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

public class SentimentPValue {

	private static final int ITERATIONS = 100000;
	
	double[] allValues = { 
			// pre
			0.9940,0.9902,0.9900,0.9846,0.9840,0.9835,0.9829,
			0.9707,0.9702,0.9643,0.9629,0.9605,0.9513,0.9508,0.9506,0.9500,
			0.9451,0.9447,0.9427,0.9426,0.9413,0.9409,0.9368,0.9333,0.9327,
			0.9310,0.9309,0.9296,0.9296,0.9281,0.9265,0.9261,0.9248,0.9185,
			0.9112,0.9062,0.9050,0.9040,0.9023,0.9021,0.9014,0.9004,0.8969,
			0.8927,0.8900,0.8888,0.8845,0.8830,0.8792,0.8787,0.8763,0.8674,
			0.8666,0.8665,0.8648,0.8646,0.8607,0.8573,0.8569,0.8520,0.8509,
			0.8503,0.8465,0.8462,0.8429,0.8367,0.8322,0.8277,0.8235,0.8231,
			0.8171,0.8149,0.8130,0.8053,0.7968,0.7964,0.7926,0.7895,0.7871,
			0.7838,0.7728,0.7692,0.7688,0.7646,0.7607,0.7581,0.7576,0.7473,
			0.7235,0.7159,0.7105,0.7015,0.7001,0.6958,0.6906,0.6888,0.6849,
			0.6809,0.6805,0.6770,0.6624,0.6466,0.6445,0.6161,0.6151,0.6067,
			0.6017,0.5935,0.5896,0.5889,0.5823,0.5798,0.5758,0.5465,0.5288,0.5196,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.4998,0.4993,0.4595,0.4567,0.4442,0.4101,0.3512,0.3298,0.3256,0.3093,0.2548,0.2417,0.2394,0.2075,0.1658,0.1429,-0.2044,-0.2380,-0.2392,-0.4938,-0.5128,-0.5530,-0.5584,-0.6591,
			-0.7847,-0.8323,-7.4000,
			
			// put post here
			0.9940,0.9902,0.9900,0.9846,0.9840,0.9835,0.9829,
			0.9707,0.9702,0.9643,0.9629,0.9605,0.9513,0.9508,0.9506,0.9500,
			0.9451,0.9447,0.9427,0.9426,0.9413,0.9409,0.9368,0.9333,0.9327,
			0.9310,0.9309,0.9296,0.9296,0.9281,0.9265,0.9261,0.9248,0.9185,
			0.9112,0.9062,0.9050,0.9040,0.9023,0.9021,0.9014,0.9004,0.8969,
			0.8927,0.8900,0.8888,0.8845,0.8830,0.8792,0.8787,0.8763,0.8674,
			0.8666,0.8665,0.8648,0.8646,0.8607,0.8573,0.8569,0.8520,0.8509,
			0.8503,0.8465,0.8462,0.8429,0.8367,0.8322,0.8277,0.8235,0.8231,
			0.8171,0.8149,0.8130,0.8053,0.7968,0.7964,0.7926,0.7895,0.7871,
			0.7838,0.7728,0.7692,0.7688,0.7646,0.7607,0.7581,0.7576,0.7473,
			0.7235,0.7159,0.7105,0.7015,0.7001,0.6958,0.6906,0.6888,0.6849,
			0.6809,0.6805,0.6770,0.6624,0.6466,0.6445,0.6161,0.6151,0.6067,
			0.6017,0.5935,0.5896,0.5889,0.5823,0.5798,0.5758,0.5465,0.5288,0.5196,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.5000,0.4998,0.4993,0.4595,0.4567,0.4442,0.4101,0.3512,0.3298,0.3256,0.3093,0.2548,0.2417,0.2394,0.2075,0.1658,0.1429,-0.2044,-0.2380,-0.2392,-0.4938,-0.5128,-0.5530,-0.5584,-0.6591,
			-0.7847,-0.8323,-7.4000
	};

	private void run() {
		//Map<String, Student> students = StudentLoader.load();

		List<Double> allValues = new ArrayList<Double>();
		for(Double d : this.allValues) {
			allValues.add(d);
		}
		
		
		double countLarger = 0;
		for(int i = 0; i < ITERATIONS; i++) {
			List<Double> sampledPre = sample(allValues, 137);
			List<Double> sampledPost = sample(allValues, 137);
			double preMean = getMean(sampledPre);
			double postMean = getMean(sampledPost);
			// two tailed test
			double diff = Math.abs(postMean - preMean);
			if(diff > 0.13) {
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
		new SentimentPValue().run();
	}


}
