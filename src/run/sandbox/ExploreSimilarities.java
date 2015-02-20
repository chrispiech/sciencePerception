package run.sandbox;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import minions.StudentLoader;
import minions.StudentLoaderSpelling;
import models.Student;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import util.Histogram;

public class ExploreSimilarities {

	private void run() {
		Map<String, Student> students = StudentLoaderSpelling.load();
		System.out.println("num students: " + students.size());
		
		String[] tables = {
			"preCs",
			"preSelf",
			"postCs",
			"postSelf"
		};
		
		// write the header line
		String header = "\t";
		for(String b : tables) {
			header += b + "\t";
		}
		System.out.println(header);
		
		// write the table body
		for(String a : tables) {
			String line = a + "\t";
			for(String b : tables) {
				double s = getSimilarity(students, a, b);
				line += s + "\t";
			}
			System.out.println(line);
		}
		
		
	}
	
	private double getSimilarity(
			Map<String, Student> students, 
			String tableAName,
			String tableBName) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		for(String id : students.keySet()) {
			Student s = students.get(id);
			List<String> A = s.getList(tableAName);
			List<String> B = s.getList(tableBName);
			if(A == null || B == null) continue;
			double d = jaccardIndex(A, B);
			stats.addValue(d);
		}
		return stats.getMean();
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
		new ExploreSimilarities().run();
	}



}
