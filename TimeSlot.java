/**
 * 
 */
package ITS;

/**
 * @author Ekanayaketw
 *
 */
public class TimeSlot {
	
	    private int slotTime;
		private Exam exams;
		
		
		public TimeSlot(int t){
			this.setSlotTime(t);
		}

 	    public int getSlotTime() {
			return slotTime;
		}
	
		public void setSlotTime(int slotTime) {
			this.slotTime = slotTime;
		}

		public Exam getExam() {
			return exams;
		}

		public void setExam(Exam exams) {
			this.exams = exams;
		}

}
