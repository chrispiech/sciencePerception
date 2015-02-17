package run;

import java.util.Map;

import minions.StudentLoader;
import models.Student;

import com.alchemyapi.api.AlchemyAPI;

public class Wordle {

	public static void main(String[] args) {
		new Wordle().run();
	}

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		String csStr = "";
		for(String id : students.keySet()) {
			Student s = students.get(id);
			for(String item : s.getPreCs()) {
				csStr += item + " ";
			}
			/*if(s.hasPost()) {
				for(String item : s.getPostCs()) {
					csStr += item + " ";
				}
			}*/
		}
		System.out.println(csStr);
	}
}
