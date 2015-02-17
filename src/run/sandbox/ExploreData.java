package run.sandbox;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.CountMap;
import util.FileSystem;
import util.MapSorter;
import util.Warnings;

public class ExploreData {

	private static final int PRE_END = 33;
	private static final int PRE_START = 3;
	CountMap<String> preSelfWords = new CountMap<String>();
	CountMap<String> preCsWords = new CountMap<String>();
	CountMap<String> postSelfWords = new CountMap<String>();
	CountMap<String> postCsWords = new CountMap<String>();

	private void run() {
		File projDir = new File(FileSystem.getDataDir(), "OpenEnded");
		File pre = new File(projDir, "pre.csv");
		File post = new File(projDir, "post.csv");
		
		extractFile(pre, preSelfWords, preCsWords);
		extractFile(post, postSelfWords, postCsWords);
		
		outputCount(preSelfWords, "pre-self-words");
		outputCount(preCsWords, "pre-cs-words");
		outputCount(postSelfWords, "post-self-words");
		outputCount(postCsWords, "post-cs-words");

		CountMap<String> csWords = CountMap.merge(preCsWords, postCsWords);
		outputCount(csWords, "cs-words");
		
		CountMap<String> selfWords = CountMap.merge(preSelfWords, postSelfWords);
		outputCount(selfWords, "self-words");
		
		Set<String> allWords = new HashSet<String>();
		allWords.addAll(preSelfWords.keySet());
		allWords.addAll(preCsWords.keySet());
		allWords.addAll(postSelfWords.keySet());
		allWords.addAll(postCsWords.keySet());
		System.out.println("num words total: " + allWords.size());
	}

	private void extractFile(File f, CountMap<String> self, CountMap<String> cs) {
		
		List<String> rows = FileSystem.getFileLines(f);
		for(String line : rows.subList(2, rows.size())) {
			processLine(line, self, cs);
		}
	}

	private void outputCount(CountMap<String> map, String name) {
		System.out.println(name);
		System.out.println("num words: " + map.size());
		MapSorter<String> sorter = new MapSorter<String>();
		List<String> keys = sorter.sort(map);
		for(int i = 0; i < 10; i++) {
			String key = keys.get(i);
			int count = map.get(key);
			System.out.println(key + "\t" + count);
		}
		System.out.println("\n\n");
	}

	private void processLine(String line, CountMap<String> self, CountMap<String> cs) {
		String[] cols = line.split(",", -1);
		for(int i = PRE_START; i < PRE_END; i++) {
			String word = cols[i];
			if(!word.isEmpty()) {
				word = word.toLowerCase();
				self.add(word);
			}
		}
		for(int i = 37; i < cols.length; i++) {
			String word = cols[i];
			if(!word.isEmpty()) {
				word = word.toLowerCase();
				cs.add(word);
			}
		}
	}

	public static void main(String[] args) {
		new ExploreData().run();
	}

}
