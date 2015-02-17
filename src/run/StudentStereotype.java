package run;

import java.io.File;
import java.util.*;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import util.FileSystem;
import util.Histogram;
import util.Warnings;
import minions.Stereotyper;
import minions.StudentLoader;
import models.Student;

public class StudentStereotype {

	private void run() {
		Map<String, Student> students = StudentLoader.load();

		Histogram h = new Histogram(-1, 1, 0.5);
		SummaryStatistics stats = new SummaryStatistics();
		int nans = 0;
		for(String id : students.keySet()) {
			Student s = students.get(id);
			List<String> terms = s.getPostCs();
			if(terms == null) continue;
			Double score = Stereotyper.getStereotype(terms);
			if(score != null) {
				h.addPoint(score);
				stats.addValue(score);
			} else {
				nans++;
			}
			String name = s.getName();
			System.out.println(name + "\t" + score);
		}
		System.out.println("\n");
		System.out.println(h);
		System.out.println("nan: " + nans);
		System.out.println("mean: " + stats.getMean());
		System.out.println("std: " + stats.getStandardDeviation());
	}

	public static void main(String[] args) {
		new StudentStereotype().run();
	}


}
