package run;

import java.io.File;
import java.util.*;

import minions.StudentLoader;
import models.Student;
import util.FileSystem;
import util.Histogram;
import util.Warnings;

public class IdentityTanimoto {

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		System.out.println("num students: " + students.size());
		
		Histogram h = new Histogram(0, 100, 5);
		int countPositive = 0;
		for(String id : students.keySet()) {
			Student s = students.get(id);
			double preOverlap = getPreOverlap(s);
			h.addPoint(preOverlap);
			//System.out.println(id + "\t" + preOverlap);
			if(preOverlap > 0) {
				countPositive++;
			}
		}
		System.out.println(h);
		System.out.println("positive:" + countPositive);
	}

	private double getPreOverlap(Student s) {
		List<String> cs = s.getPreCs();
		List<String> self = s.getPreSelf();
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
		new IdentityTanimoto().run();
	}



}
