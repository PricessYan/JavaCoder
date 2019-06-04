package WebTest_Work1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import ope.FileOpe;
import ope.OperationAdv;

public class Work1 {

	public WebDriver driver;

	@BeforeMethod
	public void Open() {
		driver = new FirefoxDriver();

	}

	@AfterMethod
	public void Close() {
		driver.close();
	}

	//直接从数据库
	@DataProvider(name = "dp1")
	public Object[][] data() {
		try {
			String dbURL = "jdbc:mysql://192.168.198.128:3306/bugs";
			String uname = "root";
			String pwd = "123456";
			Connection conn = DriverManager.getConnection(dbURL, uname, pwd);
			PreparedStatement ps = conn.prepareStatement("select * from profiles");
			ResultSet rs = ps.executeQuery();
			rs.last();
			int count = rs.getRow();
			Object[][] o = new Object[count][];
			rs.beforeFirst();
			int index = 0;
			while (rs.next()) {
				o[index] = new Object[] { rs.getString(2), "123456" };
				index++;
			}
			return o;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("sql cann't connect");
			return new Object[][] { { "scbigboss@163.com", "123456" }, { "yansumei@qq.com", "123456" },
					{ "bbb@ranther.cn", "123456" }, { "aaa@qq.com", "12345" } };
		}

	}

	//JSON获取数据
	@DataProvider(name = "dp2")
	public Object[][] data2() {
		try {
			FileReader fr = new FileReader("C:\\JavaFile\\JSON\\myson.json");
			BufferedReader br = new BufferedReader(fr);
			String strjson = "";

			while (true) {
				String str = br.readLine();
				if (str == null) {
					break;
				}
				strjson += str;
			}
			br.close();
			JSONObject json = new JSONObject(strjson);

			JSONArray jsonarray = json.getJSONArray("users");
			Object[][] o = new Object[jsonarray.length()][];
			for (int i = 0; i < jsonarray.length(); i++) {
				json = (JSONObject) jsonarray.get(i);
				System.out.println(json.get("uname") + ":" + json.get("pwd"));
				o[i] = new Object[] { json.get("uname"), json.get("pwd") };
			}
			return o;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Object[][] { { "scbigboss@163.com", "123456" }, { "yansumei@qq.com", "123456" },
				{ "bbb@ranther.cn", "123456" }, { "aaa@qq.com", "12345" } };
		}
	}
	
	//XML获取数据
	@DataProvider(name = "dp3")
	public Object[][] data3() {
		SAXReader sax = new SAXReader();
		try {
			Document doc = sax.read("C:\\JavaFile\\XML\\myxml.xml");
			
			Element root = doc.getRootElement();
			List<Element> list = root.elements();
			Object[][] o=new Object[list.size()][];
			int index=0;
			for (Element element : list) {
				o[index]=new Object[] {element.element("uname").getText(),element.element("pwd").getText()};
				index++;
			}
			return o;
		} catch (DocumentException e) {
			e.printStackTrace();
			return new Object[][] { { "scbigboss@163.com", "123456" }, { "yansumei@qq.com", "123456" },
				{ "bbb@ranther.cn", "123456" }, { "aaa@qq.com", "12345" } };
		}
	}
	
	
	
	

	@Test(dataProvider = "dp3")
	public void Test01(String name, String pwd) {

		driver.get("http://192.168.198.128/bugzilla/");
		driver.findElement(By.id("login_link_top")).click();
		driver.findElement(By.id("Bugzilla_login_top")).sendKeys(name);
		driver.findElement(By.id("Bugzilla_password_top")).sendKeys(pwd);
		driver.findElement(By.id("log_in_top")).click();
		String msg = driver.findElement(By.cssSelector("#title > p")).getText();

		boolean flag = true;
		int logtype = 0;

		switch (msg) {
		case "Bugzilla – Main Page":
			msg += "-----普通用户：";
			break;
		case "Bugzilla – Welcome to Bugzilla":
			msg += "-----管理员：";
			break;
		case "Bugzilla – Account Disabled":
			msg += "-----禁用用户：";
			flag = false;
			logtype = 1;
			break;
		case "Bugzilla – Invalid Username Or Password":
			msg += "-----错误用户：";
			flag = false;
			logtype = 2;
			break;

		default:
			logtype = 3;
			msg += "-----其他用户：";
			break;
		}

		FileOpe fo = new OperationAdv("c:\\JavaFile\\2019-05-31", "log.txt");
		fo.logWriter(logtype, msg + name);
		Assertion ass = new Assertion();
		ass.assertTrue(flag);

	}

}
