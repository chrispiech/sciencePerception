package run;

import java.io.File;
import java.util.*;

import minions.StudentLoader;
import models.Student;
import util.FileSystem;
import util.Histogram;
import util.Warnings;

public class TotalEntries {

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		int count = 0;
		for(String id : students.keySet()) {
			Student s = students.get(id);
			
			System.out.print(id+": ");
			for(int i = 0; i < s.getPreCs().size(); i++) {
				System.out.print(s.getPreCs().get(i) +",");
			}
			System.out.println("");
			
			count += s.getPreSelf().size();
			count += s.getPreCs().size();
			if(s.hasPost()) {
				count += s.getPostSelf().size();
				count += s.getPostCs().size();
			}
		}
	}

	public static void main(String[] args) {
		new TotalEntries().run();
	}



}
