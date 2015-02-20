package run;

import java.util.List;
import java.util.Map;

import util.CountMap;
import util.MapSorter;
import minions.StudentLoader;
import models.Student;

public class WordCloud {
	public static void main(String[] args) {
		new WordCloud().run();
	}

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		for(String id : students.keySet()) {
			Student s = students.get(id);
			for(String item : s.getPreCs()) {
				if(!item.equals("creative")) {
				System.out.println(item.replaceAll("\\s+","-"));
				}
			}
			/*if(s.getPostCs() == null) continue;
			for(String item : s.getPostCs()) {
				System.out.println(item.replaceAll("\\s+","-"));
			}*/
		}
	}
}
