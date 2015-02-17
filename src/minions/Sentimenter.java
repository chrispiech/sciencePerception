package minions;

import java.util.List;

import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import util.Warnings;

import com.alchemyapi.api.AlchemyAPI;

public class Sentimenter {
	
	private static AlchemyAPI alchemy = null;

	public static Double getSentiment(List<String> list) {
		return getSentiment(asString(list));
	}
	
	public static Double getSentiment(String str) {
		if(Sentimenter.alchemy == null) {
			loadAlchemy();
		}
		return getSentiment(alchemy, str);
		//return 0.5;
	}
	
	private static void loadAlchemy() {
		try {
			String apiFile = "api_key.txt";
			Sentimenter.alchemy = AlchemyAPI.GetInstanceFromFile(apiFile);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	private static String asString(List<String> list) {
		String str = "";
		for(String s : list) {
			str += s + " ";
		}
		return str;
	}

	private static Double getSentiment(AlchemyAPI alchemyObj, String str){
		try {
			Document doc = alchemyObj.TextGetTextSentiment(str);
			Node sentimentNode = doc.getElementsByTagName("docSentiment").item(0);
			String sentimentStr = sentimentNode.getTextContent();
			return parseSentimentStr(sentimentStr);
		} catch (Exception e) {
			return null;
		} 
	}

	private static double parseSentimentStr(String sentimentStr) {
		sentimentStr = sentimentStr.trim();
		if(sentimentStr.contains("neutral")) {
			return 0;
		} else if(sentimentStr.contains("positive")) {
			sentimentStr = sentimentStr.replace("positive", "");
			sentimentStr = sentimentStr.trim();
			return Double.parseDouble(sentimentStr);
		} else if(sentimentStr.contains("negative")) {
			sentimentStr = sentimentStr.replace("negative", "");
			sentimentStr = sentimentStr.trim();
			return Double.parseDouble(sentimentStr);
		}
		throw new RuntimeException("neither pos, neg or neut");
	}
	
}
