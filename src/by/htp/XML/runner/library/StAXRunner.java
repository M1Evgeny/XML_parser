package by.htp.XML.runner.library;

import java.util.List;
import by.htp.XML.model.library.PrintedEdition;
import by.htp.XML.parser.library.StAXLibraryParser;

public class StAXRunner {

	public static final String PATH = "resources/Library.xml";

	public static void main(String[] args) {
		StAXLibraryParser staxParser = new StAXLibraryParser();
		staxParser.buildCatalog(PATH);

		List<PrintedEdition> list = staxParser.getCatalog();
		for (PrintedEdition f : list) {
			System.out.println(f);
		}

	}

}
