package by.htp.XML.runner.food;

import java.util.List;

import by.htp.XML.model.food.Food;
import by.htp.XML.parser.food.StAXMenuParser;

public class StAXRunner {
	public static final String PATH = "resources/menu.xml";

	public static void main(String[] args) {
		StAXMenuParser staxParser = new StAXMenuParser();
		staxParser.buildMenu(PATH);

		List<Food> list = staxParser.getMenu();
		for (Food f : list) {
			System.out.println(f);
		}

	}

}
