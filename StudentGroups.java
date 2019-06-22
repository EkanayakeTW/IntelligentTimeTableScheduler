/**
 * 
 */
package ITS;

import java.util.ArrayList;

/**
 * @author Ekanayaketw
 *
 */
public class StudentGroups {
	
	private String name;
	private int noOfExamsPerDay;
	private ArrayList<Combination> combinations=new ArrayList<>();
	private int size;
	private String subjectName;
	private boolean isPractical;
	private String department;

	public StudentGroups(String string, int numberOfExams, int i, ArrayList<Combination> combs, String subject, boolean lab, String dept) {
		// TODO Auto-generated constructor stub
		this.setName(string);
		this.setNoOfExamsPerDay(numberOfExams);
		this.setCombination(combs);
		this.setSize(i);
		this.subjectName=subject;
		this.isPractical=lab;
		this.setDepartment(dept);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public ArrayList getCombination() {
		return combinations;
	}

	public void setCombination(ArrayList combination) {
		this.combinations = combination;
	}

	public int getnoOfExamsPerDay() {
		return noOfExamsPerDay;
	}

	public void setNoOfExamsPerDay(int noOfExamsPerDay) {
		this.noOfExamsPerDay = noOfExamsPerDay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public boolean isPractical() {
		return isPractical;
	}

	public void setPractical(boolean isPractical) {
		this.isPractical = isPractical;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

}
