package by.htp.XML.runner.library;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import by.htp.XML.model.library.PrintedEdition;
import by.htp.XML.parser.library.LibrarySaxHandler;

public class SaxLibrary {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		XMLReader reader = XMLReaderFactory.createXMLReader();
		LibrarySaxHandler handler = new LibrarySaxHandler();
		reader.setContentHandler(handler);
		reader.parse(new InputSource("resources/Library.xml"));

		List<PrintedEdition> catalog = handler.getPrintedEdition();
		for (PrintedEdition pE : catalog) {
			System.out.println(pE);
		}
	}

}
