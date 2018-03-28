package by.htp.XML.parser.food;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import by.htp.XML.model.XMLStreamConstants;
import by.htp.XML.model.food.Food;
import by.htp.XML.model.food.MenuTagName;

public class StAXMenuParser2 {
	private List<Food> menu = new ArrayList<Food>();
	private XMLInputFactory inputFactory;

	public StAXMenuParser2() {
		inputFactory = XMLInputFactory.newInstance();
	}

	public List<Food> getMenu() {
		return menu;
	}

	public void buildMenu(String fileName) {
		FileInputStream inputStream = null;
		XMLStreamReader reader = null;
		String name;
		try {
			inputStream = new FileInputStream(new File(fileName));
			reader = inputFactory.createXMLStreamReader(inputStream);
			while (reader.hasNext()) {
				int type = reader.next();
				if (type == XMLStreamConstants.START_ELEMENT) {
					name = reader.getLocalName();
					if (MenuTagName.valueOf(name.toUpperCase().replace("-", "_")) == MenuTagName.FOOD) {
						Food food = buildFood(reader);
						menu.add(food);
					}
				}
			}
		} catch (XMLStreamException ex) {
			System.err.println("StAX parsing error! " + ex.getMessage());
		} catch (FileNotFoundException ex) {
			System.err.println("File not found! " + ex.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				System.err.println("imposible close " + e);
			}
		}
	}

	private Food buildFood(XMLStreamReader reader) throws XMLStreamException {
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			System.out.println(type);
			Food food = null;
			MenuTagName elementName = null;
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				 elementName = MenuTagName.getElemenTagName(reader.getLocalName());
				 food = startFood(elementName, reader);
				 break;
			case XMLStreamConstants.CHARACTERS:
				String text = reader.getText().trim();
				if (text.isEmpty()) {
					break;
				}
				try {
					setFood(elementName, food, reader, text);
				} catch (NullPointerException e) {
					System.err.println("NullPointerException at setFood" + e);
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (MenuTagName.valueOf(name.toUpperCase()) == MenuTagName.FOOD) {
					return food;
				}
				break;
			}
		}
		throw new XMLStreamException("Unkown element in tag FOOD");
	}

	private Food startFood(MenuTagName elementName, XMLStreamReader reader) {
		Food food = null;
		switch (elementName) {
		case FOOD:
			food = new Food();
			Integer id = Integer.parseInt(reader.getAttributeValue(null, "id"));
			food.setId(id);
			break;
		}
		return food;
	}

	private void setFood(MenuTagName elementName, Food food, XMLStreamReader reader, String text) {
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

	}
}