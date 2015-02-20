package run;

import java.util.List;
import java.util.Map;

import util.CountMap;
import util.MapSorter;
import minions.StudentLoader;
import models.Student;

public class EntriesHistogram {
	public static void main(String[] args) {
		new EntriesHistogram().run();
	}

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		
		CountMap<String> counts = new CountMap<String>();
		for(String id : students.keySet()) {
			Student s = students.get(id);
			for(String item : s.getPreCs()) {
				counts.add(item);
			}
			if(s.hasPost()) {
				for(String item : s.getPostCs()) {
					counts.add(item);
				}
			}
		}
		MapSorter<String> sorter = new MapSorter<String>();
		List<String> keys = sorter.sort(counts);
		for(int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			int count = counts.get(key);
			System.out.println(key + "\t" + count);
		}
		System.out.println("\n\n");
	}
}
