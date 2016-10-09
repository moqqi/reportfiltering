package main.java.Converter.ConverterImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.opencsv.CSVWriter;

import main.java.Converter.Converter;

public class ConverterImpl implements Converter{
	private static String DEFAULT_PATH="src/main/resources/";

	@Override
	public String getFromJson(String fileName) throws FileNotFoundException, IOException, ParseException {
		JSONParser parser = new JSONParser();
        JSONArray obj = (JSONArray) parser.parse(new FileReader(DEFAULT_PATH+fileName));//get array of JSON objects
       	String result="";
       	for(Object o : obj){ 	
           JSONObject jsonObject =  (JSONObject) o;//get each object from array

	   //assign information from objects
           Long maxHoleSize = (Long) jsonObject.get("max-hole-size");           
           Long packetsServiced = (Long) jsonObject.get("packets-serviced");
           Long packetsRequested = (Long) jsonObject.get("packets-requested");
           String clientGuid = (String) jsonObject.get("client-guid");
           String clientAddress = (String) jsonObject.get("client-address");
           Long requestTime = (Long) jsonObject.get("request-time");
           String formatted = getDateTime(requestTime);
           String serviceGuid = (String) jsonObject.get("service-guid");
           Long retriesRequest = (Long) jsonObject.get("retries-request");
           
           result += clientAddress+","+clientGuid+","+formatted+","+serviceGuid+","+retriesRequest+","
           +packetsRequested+","+packetsServiced+","+maxHoleSize+"\n";
        }
        return result;
	}

	@Override
	public String getFromXml(String fileName) throws TransformerException, ParserConfigurationException,
	SAXException, IOException {
		File stylesheet = new File(DEFAULT_PATH+"style.xsl");//style sheet used from converting xml to csv.
        File xmlSource = new File(DEFAULT_PATH+fileName);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlSource);

        StreamSource stylesource = new StreamSource(stylesheet);
        Transformer transformer = TransformerFactory.newInstance()
                .newTransformer(stylesource);
        Source source = new DOMSource(document);
        StreamResult outputTarget = new StreamResult(new StringWriter());
        transformer.transform(source, outputTarget);
        
        String csvString = outputTarget.getWriter().toString();
        
        return csvString;
	}

	@Override
	public String getFromCsv(String fileName) throws IOException {
		return readFile(DEFAULT_PATH+"reports.csv", StandardCharsets.UTF_8);
	}
	private String readFile(String path, Charset encoding) throws IOException{//readFile made into own method for possible future use.
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

	@Override
	public void writeToCsv(String filename, List<ArrayList<String>> csvLines) throws IOException {
		String csv = DEFAULT_PATH+filename;
		CSVWriter writer = null;
		
		writer = new CSVWriter(new FileWriter(csv));
		String[] summary = "client-address,client-guid,request-time,service-guid,retries-request,packets-requested,packets-serviced,max-hole-size".split(",");
		writer.writeNext(summary);
		for(ArrayList<String> each: csvLines){
			String[] array = each.toArray(new String[]{});
		    writer.writeNext(array);
		}
		
		writer.close();
	}
	
	private String getDateTime(Long epochTime){
		Date date = new Date(epochTime);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");//Expected Output: 2016-06-28 19:06:50 ADT
        format.setTimeZone(TimeZone.getTimeZone("Canada/Atlantic"));
        String formatted = format.format(date);
        return formatted;
	}

}
