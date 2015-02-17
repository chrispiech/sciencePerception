package run;

import java.util.Map;

import minions.Sentimenter;
import minions.Stereotyper;
import minions.StudentLoader;
import models.Student;
import util.Histogram;

public class StereoVsSentiment {

	private void run() {
		Map<String, Student> students = StudentLoader.load();
		for(String id : students.keySet()) {
			Student s = students.get(id);
			//if(!s.hasPost()) continue;
			Double stereotype = Stereotyper.getStereotype(s.getPreCs());
			Double sentiment = Sentimenter.getSentiment(s.getPreCs());
			if(stereotype != null && sentiment != null) {
				String name = s.getName();
				String school = s.getSchool();
				System.out.println(name + "\t" + school + "\t" + stereotype + "\t" + sentiment);
						
			} 
			
		}
	}

	public static void main(String[] args) {
		new StereoVsSentiment().run();
	}
}
