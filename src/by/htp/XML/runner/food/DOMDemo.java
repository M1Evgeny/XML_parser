package by.htp.XML.runner.food;

import java.util.List;

import by.htp.XML.model.food.Food;
import by.htp.XML.parser.food.FoodDOMBuilder;

public class DOMDemo {

	public static final String PATH = "resources/menu.xml";

	public static void main(String[] args) {
		FoodDOMBuilder domBuilder = new FoodDOMBuilder();
		domBuilder.buildListFood(PATH);
		
		List<Food> list = domBuilder.getFood();
		for(Food f: list){
		System.out.println(f);
		}
	}

}
