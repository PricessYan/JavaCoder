package XMLStudy;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class DemoXML {

	public static void main(String[] args) {
		
		Document doc=DocumentHelper.createDocument();
		Element root=doc.addElement("root").addAttribute("school", "ranther").addAttribute("name", "xmldemo");
		root.addText("happy to study");
		Element sub=root.addElement("sub");
		Element sub2=sub.addElement("sub2");
		sub2.addAttribute("name", "yan");
		sub2.addText("beautiful");
		Element sub3=sub.addElement("sub3");
		sub3.addAttribute("name", "liu");
		sub3.addText("kind");
		//System.out.println(root.asXML());
		
		OutputFormat of=OutputFormat.createPrettyPrint();
		of.setEncoding("GBK");
//		of.setSuppressDeclaration(true);
//		of.setIndent("  ");
		try {
			FileWriter fw=new FileWriter("C:\\JavaFile\\XML\\first.xml");
			XMLWriter xmlwrite=new XMLWriter(fw,of);
			xmlwrite.write(doc);
			xmlwrite.close();
			System.out.println("succeed");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
