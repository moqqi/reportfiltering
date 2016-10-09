package main.java;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import main.java.Converter.Converter;
import main.java.Converter.ConverterImpl.ConverterImpl;

public class DataSorting {
	public static void main(String args[]) throws TransformerException, ParserConfigurationException, 
	SAXException, IOException, ParseException{
		Converter converter = new ConverterImpl();
		String fromJson = converter.getFromJson("reports.json");
		String fromXml = converter.getFromXml("reports.xml");
		String fromCsv = converter.getFromCsv("reports.csv");
		
		String allData = fromCsv+fromXml+fromJson;
		List<ArrayList<String>> csvLines = new ArrayList<ArrayList<String>>();
		
		Scanner scanner = new Scanner(allData);
		scanner.nextLine();//skipping first data line.
		while(scanner.hasNext()){
			String line = scanner.nextLine();
			String[] columns = line.split(",");
				if(!columns[6].matches("0")){//check for "0" packets-serviced
				ArrayList<String> values = new ArrayList<String>();
				for(String value: columns){
					values.add(value);
				}
				csvLines.add(values);
			}
		}
		sort(csvLines);
		
		System.out.println("Summary Of Reports:");
		int count = 0;
		for(ArrayList<String> line: csvLines){
			System.out.println("Report #"+count+"\tService-Guid:"+line.get(3));//output of count and serv-guid.
			count++;
		}
		scanner.close();
		converter.writeToCsv("output.csv", csvLines);
	}
	
	static void sort(List<ArrayList<String>> csvLines){
		Comparator<ArrayList<String>> comp = new Comparator<ArrayList<String>>() {
		    public int compare(ArrayList<String> csvLine1, ArrayList<String> csvLine2) {
		        return csvLine2.get(2).compareTo(csvLine1.get(2));//comparing dates, what's up with JSON dates?
		    }
		};
		Collections.sort(csvLines, comp);
	}
}
