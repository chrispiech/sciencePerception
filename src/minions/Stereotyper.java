package minions;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.FileSystem;

public class Stereotyper {
	
	private static Map<String, Double> stereotypes = null;

	public static Double getStereotype(List<String> terms) {
		if(stereotypes == null) {
			loadStereotypes();
		}
		double sum = 0.0;
		int count = 0;
		for(String term : terms) {
			if(stereotypes.containsKey(term)) {
				sum += stereotypes.get(term);
				count++;
			}
		}
		if(count == 0) {
			return null;
		}
		return sum / count;
	}

	private static void loadStereotypes() {
		File f = new File(FileSystem.getDataDir(), "wordStereotypes.csv");
		stereotypes = new HashMap<String, Double>();
		for(String line : FileSystem.getFileLines(f)) {
			String[]cols = line.split(",");
			String word = cols[0].toLowerCase();
			String stereoStr = cols[1];
			double score = Double.parseDouble(stereoStr);
			stereotypes.put(word, score);
		}
	}
	
}
