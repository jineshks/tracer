package in.espirit.tracer.model;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Bineesh
 *
 */

public class Emoticons {

	private static HashMap<String, String> iconCodes = null;

	public HashMap<String, String> getIconsMap() {
		if (iconCodes == null) {
			replaceCodeByIcons();
		}
		return iconCodes;

	}

	private void replaceCodeByIcons() {

		try {

			if (iconCodes == null) {	
				//read xml
				InputStream is = getClass().getResourceAsStream("/Emoticons.xml");

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder;

				iconCodes = new HashMap<String, String>();
				builder = factory.newDocumentBuilder();
				Document doc = builder.parse(is);

				// Get Root Node and its child
				Node root = doc.getDocumentElement();
				NodeList childNodes = root.getChildNodes();

				for (int i = 0; i < childNodes.getLength(); i++) {
					replaceCodeByIcons(childNodes.item(i));
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void replaceCodeByIcons(Node node) {

		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element e = (Element) node;
			String[] keyVal = e.getTextContent().trim().split("#");
			iconCodes.put(keyVal[0].trim(), keyVal[1].trim());
		}
	}

}
