package run;

import java.io.File;
import java.util.*;

import minions.Sentimenter;
import minions.StudentLoader;
import models.Student;
import util.FileSystem;
import util.Histogram;
import util.Warnings;

public class StudentSentiment {

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		getPreSentiment(students);
	}

	private void getPreSentiment(Map<String, Student> students) {
		Histogram h = new Histogram(-1.0, 1.0, 0.1);
		for(String id : students.keySet()) {
			Student s = students.get(id);
			String csPreStr = asString(s.getPreCs());
			Double sentiment = Sentimenter.getSentiment(csPreStr);
			if(sentiment != null) {
				h.addPoint(sentiment);
			}
			String name = s.getName();
			String school = s.getSchool();
			System.out.println(name + "\t" + school + "\t "+ sentiment + "\t" + csPreStr);
		}
		System.out.println("\n\n");
		System.out.println(h);
	}

	private void getPostSentiment(Map<String, Student> students) {
		for(String id : students.keySet()) {
			Student s = students.get(id);
			String csPostStr = asString(s.getPostCs());
			double sentiment = Sentimenter.getSentiment(csPostStr);
			String name = s.getName();
			String school = s.getSchool();
			System.out.println(name + "\t" + school + "\t "+ sentiment);
		}
	}

	private String asString(List<String> list) {
		String str = "";
		for(String s : list) {
			str += s + " ";
		}
		return str;
	}

	public static void main(String[] args) {
		new StudentSentiment().run();
	}



}
