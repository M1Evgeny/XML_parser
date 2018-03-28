package by.htp.XML.runner.food;

import java.util.List;

import by.htp.XML.model.food.Food;

import by.htp.XML.parser.food.StAXMenuParser2;

public class StAXRunner2 {
	public static final String PATH = "resources/menu.xml";

	public static void main(String[] args) {
		StAXMenuParser2 staxParser = new StAXMenuParser2();
		staxParser.buildMenu(PATH);

		List<Food> list = staxParser.getMenu();
		for (Food f : list) {
			System.out.println(f);
		}

	}

}
