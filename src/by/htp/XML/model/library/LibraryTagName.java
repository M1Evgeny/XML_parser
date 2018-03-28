package by.htp.XML.model.library;

import by.htp.XML.model.food.MenuTagName;

public enum LibraryTagName {
	PRINTED_EDITION, BOOK, MAGAZIN, TITLE, AUTHOR, PUBLISH_YEAR, DESCRIPTION, REDABILITY, LIBRARY;

	public static LibraryTagName getElementTagName(String element) {
		switch (element) {
		case "book":
			return BOOK;
		case "magazin":
			return MAGAZIN;
		case "title":
			return TITLE;
		case "description":
			return DESCRIPTION;
		case "author":
			return AUTHOR;
		case "publish_year":
			return PUBLISH_YEAR;
		case "redability":
			return REDABILITY;
		case "library":
			return LIBRARY;
		case "printed_edition":
			return PRINTED_EDITION;
		default:
			throw new EnumConstantNotPresentException(MenuTagName.class, element);
		}
	}
}
