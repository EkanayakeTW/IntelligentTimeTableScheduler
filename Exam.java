/**
 * 
 */
package ITS;

/**
 * @author Ekanayaketw
 *
 */
public class Exam {
	
	private Invigilator invigilator;
	private String subject;
	private StudentGroups studentGroup;
	
	//represents one lecture having invigilator and subject
	
	Exam(Invigilator inv, String sub){
		this.invigilator=inv;
		this.subject=sub;
	}

	public Invigilator getInvigilator() {
		return invigilator;
	}

	public void setInvigilator(Invigilator invigilator) {
		this.invigilator = invigilator;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public StudentGroups getStudentGroup() {
		return studentGroup;
	}

	public void setStudentGroup(StudentGroups studentGroup) {
		this.studentGroup = studentGroup;
	}

}
