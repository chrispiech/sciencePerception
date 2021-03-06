package minions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import models.Student;
import run.figures.IdentityTanimotoPrePost;
import util.FileSystem;

public class StudentLoaderSpelling {
	private static final int CS_START = 37;
	private static final int SELF_END = 33;
	private static final int SELF_START = 3;
	private Map<String, Student> students = new HashMap<String, Student>();

	public static Map<String, Student> load() {
		StudentLoaderSpelling l = new StudentLoaderSpelling();
		l.run();
		return l.students;
	}
	
	private void run() {
		File projDir = new File(FileSystem.getDataDir(), "Spellcheck");
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
				List<String> self = getWords(line, SELF_START, SELF_END);
				/*for(String str : self) {
					if(str.contains("computer scientist")) {
						getWords(line, SELF_START, SELF_END);
					}
					
				}*/
				s.setPostSelf(self);
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
		String curr = "";
		List<String> cols = new ArrayList<String>();
		boolean inQuotes = false;
		for(int i = 0; i < line.length(); i++) {
			if(line.charAt(i) == ',' && !inQuotes) {
				cols.add(curr);
				curr = "";
			} else {
				if(line.charAt(i) == '"') {
					inQuotes = !inQuotes;
				} else {
					curr += line.charAt(i);
				}
			}
		}
		String[] list = new String[cols.size()];
		for(int i = 0; i < list.length; i++) {
			list[i] = cols.get(i);
		}
		return list;
	}
}
