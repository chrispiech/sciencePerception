package run.figures;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import minions.Sentimenter;
import minions.StudentLoader;
import models.Student;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class SentimentPrePostSelfAverage {

	public static void main(String[] args) {
		new SentimentPrePostSelfAverage().run();
	}

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		System.out.println("num students: " + students.size());
		
		SummaryStatistics preList = new SummaryStatistics();
		SummaryStatistics postList = new SummaryStatistics();
		
		int done = 0;
		for(String id : students.keySet()) {
			Student s = students.get(id);
			addToList(preList, s.getPreSelf());
			addToList(postList, s.getPostSelf());
			System.out.println(++done);
		}
		System.out.println("pre");
		System.out.println(preList.getMean());
		System.out.println(preList.getStandardDeviation());
		
		System.out.println("\n\n");
		
		System.out.println("post");
		System.out.println(postList.getMean());
		System.out.println(postList.getStandardDeviation());
	}

	private void addToList(SummaryStatistics preList, List<String> wordList) {
		if(wordList == null) return;
		String wordStr = asString(wordList);
		Double sentiment = Sentimenter.getSentiment(wordStr);
		if(sentiment != null) {
			preList.addValue(sentiment);
		}
	}

	private String asString(List<String> list) {
		String str = "";
		for(String s : list) {
			str += s + " ";
		}
		return str;
	}
}