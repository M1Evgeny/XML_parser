package by.htp.XML.parser.library;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import by.htp.XML.model.library.PrintedEdition;

public class LibraryDOMParser {
	private List<PrintedEdition> catalog;
	private DocumentBuilder docBuilder;

	public LibraryDOMParser() {
		catalog = new ArrayList<>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println("Configuration error: " + e);
		}
	}

	public List<PrintedEdition> getCatalog() {
		return catalog;
	}

	public void buildCatalog(String fileName) {
		Document doc = null;
		try {
			doc = docBuilder.parse(fileName);
			Element root = doc.getDocumentElement();
			NodeList peList = root.getElementsByTagName("printed_edition");
			for (int i = 0; i < peList.getLength(); i++) {
				Element peElement = (Element) peList.item(i);
				PrintedEdition pE = buildPrint(peElement);
				catalog.add(pE);
			}
		} catch (IOException e) {
			System.err.println("File error or I/O error: " + e);
		} catch (SAXException e) {
			System.err.println("Parsing failure: " + e);
		}
	}

	private PrintedEdition buildPrint(Element peElement) {
		PrintedEdition pE = new PrintedEdition();
		//pE.setType( );
		// pE.setId(Integer.parseInt(peElement.getAttribute("id")));
		pE.setTitle(getElementTextContent(peElement, "title"));
		pE.setAuthor(getElementTextContent(peElement, "author"));
		pE.setYear(Integer.parseInt(getElementTextContent(peElement, "publish_year")));
		pE.setDescription(getElementTextContent(peElement, "description"));
		pE.setRedability(getElementTextContent(peElement, "redability"));
		return pE;
	}

	private String getElementTextContent(Element peElement, String name) {
		NodeList nList = peElement.getElementsByTagName(name);
		Node node = nList.item(0);
		String text = node.getTextContent();
		return text;
	}

	private String getPrintEditionType(Element element) {
		NodeList nList = element.getElementsByTagName("book");
		Element peElement = (Element) nList.item(0);
		String text = peElement.getLocalName();
		return "b";
	}

}
