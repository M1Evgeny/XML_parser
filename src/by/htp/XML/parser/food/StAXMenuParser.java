package by.htp.XML.parser.food;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.htp.XML.model.XMLStreamConstants;
import by.htp.XML.model.food.Food;
import by.htp.XML.model.food.MenuTagName;

public class StAXMenuParser {
	private List<Food> menu = new ArrayList<Food>();
	private XMLInputFactory inputFactory;

	public StAXMenuParser() {
		inputFactory = XMLInputFactory.newInstance();
	}

	public List<Food> getMenu() {
		return menu;
	}

	public void buildMenu(String fileName) {
		FileInputStream inputStream = null;
		XMLStreamReader reader = null;
		try {
			inputStream = new FileInputStream(new File(fileName));
			reader = inputFactory.createXMLStreamReader(inputStream);
			Food food = null;
			MenuTagName elementName = null;
			while (reader.hasNext()) {
				int type = reader.next();
				switch (type) {
				case XMLStreamConstants.START_ELEMENT:
					elementName = MenuTagName.getElemenTagName(reader.getLocalName());
					switch (elementName) {
					case FOOD:
						food = new Food();
						Integer id = Integer.parseInt(reader.getAttributeValue(null, "id"));
						food.setId(id);
						break;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					String text = reader.getText().trim();
					if (text.isEmpty()) {
						break;
					}
					switch (elementName) {
					case NAME:
						food.setName(text);
						break;
					case PRICE:
						food.setPrice(text);
						break;
					case DESCRIPTION:
						food.setDescription(text);
						break;
					case CALORIES:
						Integer calories = Integer.parseInt(text);
						food.setCalories(calories);
						break;
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					elementName = MenuTagName.getElemenTagName(reader.getLocalName());
					switch (elementName) {
					case FOOD:
						menu.add(food);
					}

				}
			}
		} catch (XMLStreamException ex) {
			System.err.println("StAX parsing error! " + ex.getMessage());
		} catch (FileNotFoundException ex) {
			System.err.println("File not found! " + ex.getMessage());
		}
	}
}
