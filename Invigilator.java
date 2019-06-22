/**
 * 
 */
package ITS;

import java.util.ArrayList;

/**
 * @author Ekanayaketw
 *
 */
public class Invigilator {
	
	private int InvigilatorID;
	private String InvigilatorName;
	private ArrayList <String> subjectsAssigned = new ArrayList();
	
	Invigilator(int id, String name, String subj){
		this.InvigilatorID=id;
		this.InvigilatorName=name;
		String[] subjectNames=subj.split("/");
		for(int i=0; i<subjectNames.length; i++){
			this.subjectsAssigned.add(subjectNames[i]);
		}
	}
	
	public int getInvigilatorID() {
		return InvigilatorID;
	}
	public void setInvigilatorID(int invigilatorID) {
		this.InvigilatorID = invigilatorID;
	}
	public String getInvigilatorName() {
		return InvigilatorName;
	}
	public void setInvigilatorName(String invigilatorName) {
		this.InvigilatorName = invigilatorName;
	}

	public ArrayList<String> getSubjectAssigned() {
		return subjectsAssigned;
	}

	public void setSubjectAssigned(ArrayList<String> subjectsAsssigned) {
		this.subjectsAssigned = subjectsAsssigned;
	}

}
