package XMLStudy;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class XMLOps {

	private Element node;
	
	
	public XMLOps(Element node) {
		this.node = node;
	}


	public List<Element> getAllNodes() {

		return getAllNodes(node);
	}



	public List<Element> getAllNodes(Element node) {
		List<Element> result = new ArrayList<>();
		List<Element> list = node.elements();
		result.addAll(list);
		for (Element e:list) {
			
			result.addAll(getAllNodes(e));
		}
		return result;
	}
	

}
