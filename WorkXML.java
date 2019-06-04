package XMLStudy;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class WorkXML {

	public static void main(String[] args) {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("bugzilla").addAttribute("IP", "192.168.198.128");
		String dbURL = "jdbc:mysql://192.168.198.128:3306/bugs";
		String uname = "root";
		String pwd = "123456";
		Connection conn;
		try {
			conn = DriverManager.getConnection(dbURL, uname, pwd);
			PreparedStatement ps = conn.prepareStatement("select * from profiles");
			ResultSet rs = ps.executeQuery();

			int index = 1;
			while (rs.next()) {
				Element sub = root.addElement("user").addAttribute("ID", "" + index);
				sub.addElement("uname").addText(rs.getString(2));
				sub.addElement("pwd").addText("123456");
				index++;
			}

			OutputFormat of = OutputFormat.createPrettyPrint();
			FileWriter fw = new FileWriter("C:\\JavaFile\\XML\\myxml.xml");
			XMLWriter xmlwrite = new XMLWriter(fw, of);
			xmlwrite.write(doc);
			xmlwrite.close();
			System.out.println("succeed");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
