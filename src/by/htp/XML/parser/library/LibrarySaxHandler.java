package by.htp.XML.parser.library;

import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import by.htp.XML.model.library.LibraryTagName;
import by.htp.XML.model.library.PrintedEdition;

import java.util.ArrayList;

public class LibrarySaxHandler extends DefaultHandler {
	private List<PrintedEdition> catalog = new ArrayList<PrintedEdition>();
	private PrintedEdition pEdition;
	private StringBuilder text;

	public List<PrintedEdition> getPrintedEdition() {
		return catalog;
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		text = new StringBuilder();
		if (qName.equals("printed_edition")) {
			pEdition = new PrintedEdition();
		}
		if (qName.equals("book")) {
			pEdition.setType("book");
			pEdition.setId(Integer.parseInt(attributes.getValue("id")));
		}
		if (qName.equals("magazin")) {
			pEdition.setType("magazin");
			pEdition.setId(Integer.parseInt(attributes.getValue("id")));
		}
	}

	public void characters(char[] buffer, int start, int length) {
		text.append(buffer, start, length);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		LibraryTagName tagname = LibraryTagName.valueOf(qName.toUpperCase().replace("-", "_"));
		switch (tagname) {
		case TITLE:
			pEdition.setTitle(text.toString());
			break;
		case AUTHOR:
			pEdition.setAuthor(text.toString());
			break;
		case PUBLISH_YEAR:
			pEdition.setYear(Integer.parseInt(text.toString()));
			break;
		case DESCRIPTION:
			pEdition.setDescription(text.toString());
			break;
		case REDABILITY:
			pEdition.setRedability(text.toString());
			break;
		case PRINTED_EDITION:
			catalog.add(pEdition);
			pEdition = null;
			break;
		}
	}

	public void warning(SAXParseException exception) {
		System.err.println("Warning: line " + exception.getLineNumber() + ": " + exception.getMessage());
	}

	public void error(SAXParseException exception) {
		System.err.println("ERROR: line " + exception.getLineNumber() + ": " + exception.getMessage());
	}

	public void fatalError(SAXParseException exception) throws SAXException {
		System.err.println("FATAL: line " + exception.getLineNumber() + ": " + exception.getMessage());
		throw (exception);
	}

}
