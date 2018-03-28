package by.htp.XML.runner.library;

import java.util.List;
import by.htp.XML.model.library.PrintedEdition;
import by.htp.XML.parser.library.LibraryDOMParser;

public class DOMRunner {
	public static final String PATH = "resources/Library.xml";

	public static void main(String[] args) {
		LibraryDOMParser parser = new LibraryDOMParser();
		parser.buildCatalog(PATH);

		List<PrintedEdition> list = parser.getCatalog();
		for (PrintedEdition f : list) {
			System.out.println(f);
		}
	}

}
