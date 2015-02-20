package run.figures;

import java.io.File;
import java.util.*;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import util.FileSystem;
import util.Histogram;
import util.Warnings;
import minions.Sentimenter;
import minions.Stereotyper;
import minions.StudentLoader;
import models.Student;

public class SentimentPValueSelf {

	private static final int ITERATIONS = 100000;

	private void run() {
		Map<String, Student> students = StudentLoader.load();

		List<Double> allValues = new ArrayList<Double>();
		for(String id : students.keySet()) {
			Student s = students.get(id);
			
			addScore(allValues, s.getPreSelf());
			addScore(allValues, s.getPostSelf());
		}
		
		double countLarger = 0;
		for(int i = 0; i < ITERATIONS; i++) {
			List<Double> sampledPre = sample(allValues, 137);
			List<Double> sampledPost = sample(allValues, 137);
			double preMean = getMean(sampledPre);
			double postMean = getMean(sampledPost);
			// two tailed test
			double diff = Math.abs(postMean - preMean);
			if(diff > 0.09) {
				countLarger++;
			}
		}
		double p = countLarger / ITERATIONS;
		System.out.println("p-value: " + p);
	}

	private List<Double> sample(List<Double> allValues, int num) {
		Collections.shuffle(allValues);
		List<Double> values = new ArrayList<Double>();
		for(int i = 0; i < num; i++) {
			values.add(allValues.get(i));
		}
		return values;
	}

	private double getMean(List<Double> values) {
		SummaryStatistics stats = new SummaryStatistics();
		for(Double d : values) {
			stats.addValue(d);
		}
		return stats.getMean();
	}

	private void addScore(List<Double> scores, List<String> terms) {
		if(terms == null) return;
		Double score = Sentimenter.getSentiment(terms);
		if(score != null) {
			scores.add(score);
		} 
	}
	
	public static void main(String[] args) {
		new SentimentPValueSelf().run();
	}


}
