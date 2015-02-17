package minions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Student;
import run.IdentityTanimoto;
import util.FileSystem;

public class StudentLoader {
	private static final int CS_START = 37;
	private static final int SELF_END = 34;
	private static final int SELF_START = 3;
	private Map<String, Student> students = new HashMap<String, Student>();

	public static Map<String, Student> load() {
		StudentLoader l = new StudentLoader();
		l.run();
		return l.students;
	}
	
	private void run() {
		File projDir = new File(FileSystem.getDataDir(), "OpenEnded");
		File pre = new File(projDir, "pre.txt");
		File post = new File(projDir, "post.txt");

		processPre(pre);
		processPost(post);
	}

	private void processPost(File post) {
		List<String> rows = FileSystem.getFileLines(post);
		for(String line : rows.subList(2, rows.size())) {
			String id = getIdFromLine(line);
			if(id.isEmpty()) continue;
			Student s = students.get(id);
			if(!students.containsKey(id)) {
				System.out.println("count not find: " + id);
			} else {
				s.setPostSelf(getWords(line, SELF_START, SELF_END));
				s.setPostCs(getWords(line, CS_START, getCols(line).length));
			}
		}
	}

	private void processPre(File pre) {
		List<String> rows = FileSystem.getFileLines(pre);
		for(String line : rows.subList(2, rows.size())) {
			String id = getIdFromLine(line);
			if(id.isEmpty()) continue;
			if(!students.containsKey(id)) {
				String name = getNameFromLine(line);
				String school = getSchoolFromLine(line);
				students.put(id, new Student(name, school));
			}
			Student s = students.get(id);
			s.setPreSelf(getWords(line, SELF_START, SELF_END));
			s.setPreCs(getWords(line, CS_START, getCols(line).length));
		}
	}

	private List<String> getWords(String line, int start, int end) {
		String[] cols = getCols(line);
		List<String> words = new ArrayList<String>();
		for(int i = start; i < end; i++) {
			String word = cols[i];
			if(!word.isEmpty()) {
				word = word.toLowerCase();
				word = word.trim();
				word = word.replace("\"", "");
				words.add(word);
			}
		}
		return words;
	}
	
	private String getNameFromLine(String line) {
		return getCols(line)[1];
	}
	
	private String getSchoolFromLine(String line) {
		return getCols(line)[2];
	}


	private String getIdFromLine(String line) {
		String name = getCols(line)[1];
		String school = getCols(line)[2];
		String id =  name + "." + school;
		return id.toLowerCase();
	}

	private String[] getCols(String line) {
		return line.split("\t", -1);
	}
}
