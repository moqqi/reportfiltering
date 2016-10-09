package main.java.Converter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public interface Converter {
	public String getFromJson(String fileName) throws FileNotFoundException, IOException, ParseException;
	public String getFromXml(String fileName) throws TransformerException, ParserConfigurationException, 
	SAXException, IOException;
	public String getFromCsv(String fileName) throws IOException;
	public void writeToCsv(String filename, List<ArrayList<String>> csvLines) throws IOException;
}
