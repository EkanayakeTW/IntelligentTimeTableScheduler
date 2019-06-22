/**
 * 
 */
package ITS;

/**
 * @author Ekanayaketw
 *
 */
public class Subject {
	
	private int subjectID;
	private String subjectName;
	private int numberOfExamsPerDay;
	private boolean islab;
	private String department;
	
	Subject(int id, String name, int exams, boolean lab, String dept){
		this.subjectID=id;
		this.subjectName=name;
		this.numberOfExamsPerDay=exams;
		this.islab=lab;
		this.department=dept;
	}
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public int getnumberOfExamsPerDay() {
		return numberOfExamsPerDay;
	}
	public void setnumberOfExamsPerDay(int numberOfExamsPerDay) {
		this.numberOfExamsPerDay = numberOfExamsPerDay;
	}
	public int getSubjectID() {
		return subjectID;
	}
	public void setSubjectID(int subjectID) {
		this.subjectID = subjectID;
	}

	public boolean isIslab() {
		return islab;
	}

	public void setIslab(boolean islab) {
		this.islab = islab;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	

}
