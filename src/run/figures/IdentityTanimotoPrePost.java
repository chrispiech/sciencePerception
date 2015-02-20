package run.figures;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import minions.StudentLoader;
import minions.StudentLoaderSpelling;
import models.Student;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import util.Histogram;

public class IdentityTanimotoPrePost {

	private void run() {
		Map<String, Student> students = StudentLoaderSpelling.load();
		System.out.println("num students: " + students.size());
		
		pre(students);
		post(students);
	}
	
	private void post(Map<String, Student> students) {
		Histogram h = new Histogram(0, 100, 5);
		DescriptiveStatistics stats = new DescriptiveStatistics();
		int countPositive = 0;
		for(String id : students.keySet()) {
			Student s = students.get(id);
			Double postOverlap = getPostOverlap(s);
			if(postOverlap == null) continue;
			h.addPoint(postOverlap);
			stats.addValue(postOverlap);
			//System.out.println(id + "\t" + preOverlap);
			if(postOverlap > 0) {
				countPositive++;
			}
		}
		System.out.println("post");
		System.out.println(h);
		System.out.println("positive:" + countPositive);
		System.out.println("mean: " + stats.getMean());
		double error = stats.getStandardDeviation() / Math.sqrt(stats.getN());
		System.out.println("standard dev: " + stats.getStandardDeviation());
		System.out.println("\n\n");
	}

	private void pre(Map<String, Student> students) {
		Histogram h = new Histogram(0, 100, 5);
		DescriptiveStatistics stats = new DescriptiveStatistics();
		int countPositive = 0;
		for(String id : students.keySet()) {
			Student s = students.get(id);
			double preOverlap = getPreOverlap(s);
			h.addPoint(preOverlap);
			stats.addValue(preOverlap);
			//System.out.println(id + "\t" + preOverlap);
			if(preOverlap > 0) {
				countPositive++;
			}
		}
		System.out.println("pre");
		System.out.println(h);
		System.out.println("positive:" + countPositive);
		System.out.println("mean: " + stats.getMean());
		double error = stats.getStandardDeviation() / Math.sqrt(stats.getN());
		System.out.println("standard dev: " + stats.getStandardDeviation());
		System.out.println("\n\n");
	}
	
	private Double getPostOverlap(Student s) {
		List<String> cs = s.getPostCs();
		List<String> self = s.getPostSelf();
		if(cs == null || self == null) return null;
		return jaccardIndex(cs, self);
	}

	private Double getPreOverlap(Student s) {
		List<String> cs = s.getPreCs();
		List<String> self = s.getPreSelf();
		if(cs == null || self == null) return null;
		return jaccardIndex(cs, self);
	}

	private static double jaccardIndex(Collection<String> a, Collection<String> b) {
		Set<String> union = new HashSet<String>();
		union.addAll(a);
		union.addAll(b);
		
		Set<String> intersect = new HashSet<String>(a);
		intersect.retainAll(b);
		
		return (100.0 * intersect.size()) / union.size();
		
	}

	public static void main(String[] args) {
		new IdentityTanimotoPrePost().run();
	}



}
