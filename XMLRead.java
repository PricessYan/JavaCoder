package XMLStudy;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/*
 * 2019-06-04
 * 1.XML¶ÁÐ´
 * 
 * 
 */

public class XMLRead {

	public static void main(String[] args) {

		// element
		SAXReader sax = new SAXReader();
		try {
			Document doc = sax.read("C:\\JavaFile\\XML\\myxml.xml");
			// System.out.println(doc.asXML());
			Element root = doc.getRootElement();
			//Element user = root.element("user");

			// Element user2 = root.elementByID("2");

			List<Element> list = root.elements();
			for (Element element : list) {
				String e1 = element.element("uname").getTextTrim();
				String e2 = element.element("pwd").getTextTrim();
				System.out.println(e1 + ":" + e2);
			}

			// attribute
//			int x = root.attributeCount();
//			for (int i = 0; i < x; i++) {
//				Attribute a = root.attribute(i);
//				System.out.println(a.getName() + ":" + a.getValue());
//			}
//			


			// getAllNOdes();
			System.out.println();
			XMLOps xmlops=new XMLOps(root);
			List<Element> result =xmlops.getAllNodes(root) ;
			for (Element element : result) {
				if (element.getName().equals("uname")) {
					System.out.println(element.getTextTrim());
				}
				//System.out.println(element.getName());
			}
			
			
			System.out.println();
			List<Element> me =getAllNodes(root) ;
			for (Element element : me) {
				if (element.getName().equals("uname")) {
					System.out.println(element.getTextTrim());
				}
				//System.out.println(element.getName());
			}
			
			

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	//my code of get node
	// it's wrong because it's twice
	public static List<Element> getAllNodes(Element node) throws DocumentException {

		List<Element> result = new ArrayList<>();
		List<Element> list = node.elements();
		result.addAll(list);
		for (Element e:list) {
			if (e.nodeCount() == 1) {
				result.add(e);
				return result;
			}
			result.addAll(getAllNodes(e));
			//getAllNodes(e);
		}
		return result;
	}

}
