package run.figures;

import java.io.File;
import java.util.*;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import minions.StudentLoader;
import models.Student;
import util.FileSystem;
import util.Histogram;
import util.Warnings;

public class IdentityPositiveOverlapPValue {

	private static final int ITERATIONS = 1000000;

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		System.out.println("num students: " + students.size());
		
		List<Double> allValues = new ArrayList<Double>();
		for(String id : students.keySet()) {
			Student s = students.get(id);
			Double postOverlap = getPostOverlap(s);
			if(postOverlap == null) continue;
			allValues.add(postOverlap);
			Double preOverlap = getPreOverlap(s);
			if(preOverlap == null) continue;
			allValues.add(preOverlap);
		}
		
		double countLarger = 0;
		for(int i = 0; i < ITERATIONS; i++) {
			List<Double> sampledPre = sample(allValues, 160);
			List<Double> sampledPost = sample(allValues, 150);
			double preMean = getPercent(sampledPre);
			double postMean = getPercent(sampledPost);
			// two tailed test
			double diff = Math.abs(postMean - preMean);
			if(diff > (0.198)) {
				countLarger++;
			}
			if(i % 10000 == 0) System.out.println(i);
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

	private double getPercent(List<Double> values) {
		SummaryStatistics stats = new SummaryStatistics();
		for(Double d : values) {
			stats.addValue(d);
		}
		return stats.getMean();
	}
	
	private Double getPostOverlap(Student s) {
		List<String> cs = s.getPostCs();
		List<String> self = s.getPostSelf();
		if(cs == null || self == null) return null;
		return positiveOverlap(cs, self);
	}

	private Double getPreOverlap(Student s) {
		List<String> cs = s.getPreCs();
		List<String> self = s.getPreSelf();
		if(cs == null || self == null) return null;
		return positiveOverlap(cs, self);
	}

	private static double positiveOverlap(Collection<String> a, Collection<String> b) {
		Set<String> intersect = new HashSet<String>(a);
		intersect.retainAll(b);
		return intersect.isEmpty() ? 0 : 1;
	}

	public static void main(String[] args) {
		new IdentityPositiveOverlapPValue().run();
	}



}
