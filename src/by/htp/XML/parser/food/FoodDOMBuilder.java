package by.htp.XML.parser.food;

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

import by.htp.XML.model.food.Food;

public class FoodDOMBuilder {
	private List<Food> menu;
	private DocumentBuilder docBuilder;

	public FoodDOMBuilder() {
		menu = new ArrayList<Food>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			docBuilder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println("Configuration error: " + e);
		}
	}

	public List<Food> getFood() {
		return menu;
	}

	public void buildListFood(String fileName) {
		Document doc = null;
		try {
			doc = docBuilder.parse(fileName);
			Element root = doc.getDocumentElement();
			NodeList foodList = root.getElementsByTagName("food");
			for (int i = 0; i < foodList.getLength(); i++) {
				Element foodElement = (Element) foodList.item(i);
				Food food = buildFood(foodElement);
				menu.add(food);
			}
		} catch (IOException e) {
			System.err.println("File error or I/O error: " + e);
		} catch (SAXException e) {
			System.err.println("Parsing failure: " + e);
		}
	}

	private Food buildFood(Element foodElement) {
		Food food = new Food();
		food.setId(Integer.parseInt(foodElement.getAttribute("id")));
		food.setName(getElementTextContent(foodElement, "name"));
		food.setPrice(getElementTextContent(foodElement, "price"));
		food.setDescription(getElementTextContent(foodElement, "description"));
		food.setCalories(Integer.parseInt(getElementTextContent(foodElement, "calories")));
		return food;
	}

	private String getElementTextContent(Element foodElement, String name) {
		NodeList nList = foodElement.getElementsByTagName(name);
		Node node = nList.item(0);
		String text = node.getTextContent();
		return text;
	}

}
