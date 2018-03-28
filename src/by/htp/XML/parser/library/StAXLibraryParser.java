package by.htp.XML.parser.library;

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
import by.htp.XML.model.food.MenuTagName;
import by.htp.XML.model.library.LibraryTagName;
import by.htp.XML.model.library.PrintedEdition;

public class StAXLibraryParser {
	private List<PrintedEdition> catalog;
	private XMLInputFactory inputFactory;

	public StAXLibraryParser() {
		catalog = new ArrayList<>();
		inputFactory = XMLInputFactory.newInstance();
	}

	public List<PrintedEdition> getCatalog() {
		return catalog;
	}

	public void buildCatalog(String fileName) {
		FileInputStream inputStream = null;
		XMLStreamReader reader = null;
		try {
			inputStream = new FileInputStream(new File(fileName));
			reader = inputFactory.createXMLStreamReader(inputStream);
			PrintedEdition pE = null;
			LibraryTagName elementName = null;
			while (reader.hasNext()) {
				int type = reader.next();
				switch (type) {
				case XMLStreamConstants.START_ELEMENT:
					elementName = LibraryTagName.getElementTagName(reader.getLocalName());
					switch (elementName) {
					case PRINTED_EDITION:
						pE = new PrintedEdition();
						break;
					case BOOK:
						pE.setType("Book");
						pE.setId(Integer.parseInt(reader.getAttributeValue(null, "id")));
						break;
					case MAGAZIN:
						pE.setType("Magazin");
						pE.setId(Integer.parseInt(reader.getAttributeValue(null, "id")));
						break;
					}
					break;
				case XMLStreamConstants.CHARACTERS:
					String text = reader.getText().trim();
					if (text.isEmpty()) {
						break;
					}
					switch (elementName) {
					case TITLE:
						pE.setTitle(text);
						break;
					case AUTHOR:
						pE.setAuthor(text);
						break;
					case PUBLISH_YEAR:
						pE.setYear(Integer.parseInt(text));
						break;
					case DESCRIPTION:
						pE.setDescription(text);
						break;
					case REDABILITY:
						pE.setRedability(text);
						break;
					}
					break;
				case XMLStreamConstants.END_ELEMENT:
					elementName = LibraryTagName.getElementTagName(reader.getLocalName());
					if (elementName.equals(LibraryTagName.PRINTED_EDITION)) {
						catalog.add(pE);
					}
					break;
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

	private PrintedEdition buildPrintedEdition(XMLStreamReader reader) throws XMLStreamException {
		PrintedEdition pE = null;
		String name;
		while (reader.hasNext()) {
			int type = reader.next();
			switch (type) {
			case XMLStreamConstants.START_ELEMENT:
				name = reader.getLocalName();
				pE = startPrintedEdition(name, reader);
				break;
			case XMLStreamConstants.CHARACTERS:
				String text = reader.getText().trim();
				name = reader.getLocalName();
				if (text.isEmpty()) {
					break;
				}
				try {
					setPrintedEdition(name, pE, reader, text);
				} catch (NullPointerException e) {
					System.err.println("NullPointerException");
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				name = reader.getLocalName();
				if (LibraryTagName.valueOf(name.toUpperCase()) == LibraryTagName.PRINTED_EDITION) {
					return pE;
				}
				break;
			}

		}
		throw new XMLStreamException("Unkown element in tag PrintedEdition");
	}

	private PrintedEdition startPrintedEdition(String name, XMLStreamReader reader) {
		PrintedEdition pE = new PrintedEdition();
		if (LibraryTagName.valueOf(name.toUpperCase()) == LibraryTagName.BOOK) {
			pE.setType("Book");
			pE.setId(Integer.parseInt(reader.getAttributeValue(null, "id")));
		}
		if (LibraryTagName.valueOf(name.toUpperCase()) == LibraryTagName.MAGAZIN) {
			pE.setType("Magazin");
			pE.setId(Integer.parseInt(reader.getAttributeValue(null, "id")));
		}
		return pE;
	}

	private void setPrintedEdition(String name, PrintedEdition pE, XMLStreamReader reader, String text) {
		switch (LibraryTagName.valueOf(name.toUpperCase())) {
		case TITLE:
			pE.setTitle(text);
			break;
		case AUTHOR:
			pE.setAuthor(text);
			break;
		case PUBLISH_YEAR:
			pE.setYear(Integer.parseInt(text));
			break;
		case DESCRIPTION:
			pE.setDescription(text);
			break;
		case REDABILITY:
			pE.setRedability(text);
			break;
		}

	}

}
