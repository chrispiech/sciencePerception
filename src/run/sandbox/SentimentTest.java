package run.sandbox;

import com.alchemyapi.api.*;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

class SentimentTest {
	public static void main(String[] args) {
		// Create an AlchemyAPI object.
		AlchemyAPI alchemyObj = getAlchemyObj();

		String str = "Kind, smart and funny";
		double sentiment = getSentiment(alchemyObj, str);
		
		System.out.println(str);
		System.out.println(sentiment);
	}


	private static AlchemyAPI getAlchemyObj() {
		try {
			return AlchemyAPI.GetInstanceFromFile("api_key.txt");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	private static double getSentiment(AlchemyAPI alchemyObj, String str){
		try {
			Document doc = alchemyObj.TextGetTextSentiment(str);
			Node sentimentNode = doc.getElementsByTagName("docSentiment").item(0);
			return Double.parseDouble(sentimentNode.getTextContent());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
