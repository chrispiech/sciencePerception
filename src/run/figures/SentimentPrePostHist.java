package run.figures;

import java.util.List;
import java.util.Map;

import util.Histogram;
import minions.Sentimenter;
import minions.StudentLoader;
import models.Student;

public class SentimentPrePostHist {

	public static void main(String[] args) {
		new SentimentPrePostHist().run();
	}

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		System.out.println("num students: " + students.size());

		Histogram preHist = new Histogram(-1.0, 1.0, 0.1);
		Histogram postHist = new Histogram(-1.0, 1.0, 0.1);
		
		
		for(String id : students.keySet()) {
			Student s = students.get(id);
			addToHist(preHist, s.getPreCs());
			addToHist(postHist, s.getPostCs());
			System.out.println(preHist.size() + postHist.size());
		}
		System.out.println("pre");
		System.out.println(preHist);
		
		System.out.println("\n\n");
		
		System.out.println("post");
		System.out.println(postHist);
	}

	private void addToHist(Histogram hist, List<String> wordList) {
		if(wordList == null) return;
		String wordStr = asString(wordList);
		Double preSentiment = Sentimenter.getSentiment(wordStr);
		if(preSentiment != null) {
			hist.addPoint(preSentiment);
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