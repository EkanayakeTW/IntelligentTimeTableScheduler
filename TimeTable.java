/**
 * 
 */
package ITS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

/**
 * @author Ekanayaketw
 *
 */
public class TimeTable {
	
	private ArrayList<Hall> rooms = new ArrayList<Hall>();
	private int fittness;
	private ArrayList<Exam> exams=new ArrayList<>();
	private ArrayList<StudentGroups> studentGroups=new ArrayList<>(); 
	private ArrayList<Hall> practicalRooms = new ArrayList<Hall>();
	private ArrayList<Hall> theoryRooms = new ArrayList<Hall>();
	private ArrayList<StudentGroups> theoryStudentGroups=new ArrayList<>(); 
	private ArrayList<StudentGroups> practicalStudentGroups=new ArrayList<>(); 
	private HashMap<Combination, Week> personalTimeTable= new HashMap<Combination, Week>();
	private ArrayList<Invigilator> invigilators=new ArrayList<>();
	
	//adds more rooms to timetable

	public TimeTable(ArrayList<Hall> examhall, ArrayList<Exam> exams,ArrayList<Invigilator> invigilators) {


		this.rooms=examhall;
		this.exams=exams;
		this.fittness=999;
        this.invigilators=invigilators;
	}
	
    public void initialization(ArrayList<Hall> examhall, ArrayList<Exam> exams){
	this.rooms=examhall;
	this.exams=exams;
	this.fittness=999;
   }
	
	public int getFittness() {
		return fittness;
	}
	
	public void setFittness(int fittness) {
		this.fittness = fittness;
	}

	public void addStudentGroups(ArrayList<StudentGroups> studentgrps) {
		// TODO Auto-generated method stub
		studentGroups.addAll(studentgrps);
	}
	
	public void initializeTimeTable(){
		for (Iterator<Hall> roomsIterator = rooms.iterator(); roomsIterator.hasNext();) {
			Hall room = roomsIterator.next();
			if(room.isLaboratory()){
				practicalRooms.add(room);
			}
			else{
				theoryRooms.add(room);
			}
		}
		for (Iterator<StudentGroups> studentGroupIterator = studentGroups.iterator(); studentGroupIterator.hasNext();) {
			StudentGroups studentGroup = studentGroupIterator.next();
			if(studentGroup.isPractical()){
				practicalStudentGroups.add(studentGroup);
			}
			else{
				theoryStudentGroups.add(studentGroup);
			}
		}
		rooms.clear();
		studentGroups.clear();
		setTimeTable(practicalStudentGroups, practicalRooms, "practical");
		setTimeTable(theoryStudentGroups, theoryRooms, "theory");
		rooms.addAll(practicalRooms);
		rooms.addAll(theoryRooms);
		studentGroups.addAll(practicalStudentGroups);
		studentGroups.addAll(theoryStudentGroups);
	}
	
	public void setTimeTable(ArrayList<StudentGroups> studentGroups2, ArrayList<Hall> rooms2, String string) {
		// TODO Auto-generated method stub
		Collections.shuffle(studentGroups2);
		Stack<Exam> examsStack=new Stack<Exam>();
		for (Iterator<StudentGroups> sdtGrpIterator = studentGroups2.iterator(); sdtGrpIterator.hasNext();) {			
			StudentGroups studentGrp = sdtGrpIterator.next();
			String subject = studentGrp.getSubjectName();
			int noOfLectures = studentGrp.getnoOfExamsPerDay();
			for(int i=0; i<noOfLectures; i++){
				Collections.shuffle(exams);
				Iterator<Exam> classIterator = exams.iterator();
				while(classIterator.hasNext()){
					Exam exam = classIterator.next();
					if(exam.getSubject().equalsIgnoreCase(subject)){
						Exam mainLecture=new Exam(exam.getInvigilator(), exam.getSubject());
						mainLecture.setStudentGroup(studentGrp);
						examsStack.push(mainLecture);
						break;
					}
				}
			}
		}
		while(!(examsStack.empty())){
				Collections.shuffle(examsStack);
				Exam exam2 = examsStack.pop();
				if(string.equalsIgnoreCase("theory")){
					placeTheoryExam(exam2, rooms2);
				}
				if(string.equalsIgnoreCase("practical")){
					placePracticalExam(exam2, rooms2);
				}
		}	
	}	
	
		
	
	private void placePracticalExam(Exam exam2, ArrayList<Hall> rooms2) {
		// TODO Auto-generated method stub
		int size = exam2.getStudentGroup().getSize();
		String dept=exam2.getStudentGroup().getDepartment();
		int i=0;
		boolean invalid=true;
		Hall room = null;
		Collections.shuffle(rooms2);
		while(invalid){
		room=getBestRoom(size, rooms2);
		if(room.getDepartment().equalsIgnoreCase(dept)){
			invalid=false;
			Collections.shuffle(rooms2);
			}
		else{
			Collections.shuffle(rooms2);
			}
		}
		ArrayList<Day> weekdays = room.getWeek().getWeekDays();
		Iterator<Day> daysIterator=weekdays.iterator();
		while(daysIterator.hasNext() && i<3){
			Day day = daysIterator.next();
			ArrayList<TimeSlot> timeslots = day.getTimeSlot();
			Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
			while(timeslotIterator.hasNext() && i<3){
				TimeSlot lecture3 = timeslotIterator.next();
				if(lecture3.getExam()==null){
				lecture3.setExam(exam2);
				i++;
				}
			}
		}		
	}



	private void placeTheoryExam(Exam exams, ArrayList<Hall> rooms2) {
		// TODO Auto-generated method stub
		int size = exams.getStudentGroup().getSize();
		String dept=exams.getStudentGroup().getDepartment();
		boolean invalid=true;
		Hall room = null;
		Collections.shuffle(rooms2);
		while(invalid){
			room=getBestRoom(size, rooms2);
			if(room.getDepartment().equalsIgnoreCase(dept)){
				invalid=false;
				Collections.shuffle(rooms2);
				}
			else{
				Collections.shuffle(rooms2);
				}
			}
		ArrayList<Day> weekdays = room.getWeek().getWeekDays();
		Iterator<Day> daysIterator=weekdays.iterator();
		while(daysIterator.hasNext()){
			Day day = daysIterator.next();
			ArrayList<TimeSlot> timeslots = day.getTimeSlot();
			Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
			while(timeslotIterator.hasNext()){
				TimeSlot exam2 = timeslotIterator.next();
				if(exam2.getExam()==null){
					exam2.setExam(exams);
				return;				
				}
			}
		}		
	}



	private boolean checkOccupiedHall(Hall tempRoom, ArrayList<Hall> rooms2) {
		// TODO Auto-generated method stub
		for (Iterator<Hall> roomsIterator = rooms2.iterator(); roomsIterator.hasNext();){
			Hall room = roomsIterator.next();
			if(room.equals(tempRoom)){
			ArrayList<Day> weekdays = room.getWeek().getWeekDays();
			Iterator<Day> daysIterator=weekdays.iterator();
			while(daysIterator.hasNext()){
				Day day = daysIterator.next();
				ArrayList<TimeSlot> timeslots = day.getTimeSlot();
				Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
				while(timeslotIterator.hasNext()){
					TimeSlot lecture = timeslotIterator.next();
					if(lecture.getExam()==null){
						return false;
					}
				}
			}
			return true;
			}		
		}
		return false;
	}



	private Hall getBestRoom(int size, ArrayList<Hall> rooms2) {
		// TODO Auto-generated method stub
		int delta = 1000;
		Hall room = null;
		for (Iterator<Hall> roomsIterator = rooms2.iterator(); roomsIterator.hasNext();){
			Hall tempRoom = roomsIterator.next();
			if(!checkOccupiedHall(tempRoom, rooms2)){
		        int tmp = Math.abs(size - tempRoom.getSize());
		        if(tmp < delta){
		            delta = tmp;
		            room = tempRoom;
		    }
			}
		}
		return room;
	}

public void createTimeTableGroups(ArrayList<Combination> combinations2){
		ArrayList<Combination> combinations=new ArrayList<>();
			for(Iterator<Combination> combItr = combinations2.iterator(); combItr.hasNext();){
				 Combination comb = combItr.next();
				if(!combinations.contains(comb)){
					combinations.add(comb);
				}
			}

		for(Iterator<Combination> combIterator = combinations2.iterator(); combIterator.hasNext();){
			Combination combtn = combIterator.next();
			personalTimeTable.put(combtn, new Week());
		}
		
		for(Iterator<Combination> combIterator = combinations2.iterator(); combIterator.hasNext();){
			Combination combtn = combIterator.next();
			for (Iterator<Hall> roomsIterator = theoryRooms.iterator(); roomsIterator.hasNext();){
				Hall room=roomsIterator.next();
				Iterator<Day> daysIterator = room.getWeek().getWeekDays().iterator();
				while(daysIterator.hasNext()){
					Day day = daysIterator.next();
					ArrayList<TimeSlot> timeslots = day.getTimeSlot();
					Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
					while(timeslotIterator.hasNext()){
						TimeSlot exams = timeslotIterator.next();
						if(exams.getExam()==null){
							System.out.print(" free ");
					}
						else if(exams.getExam().getStudentGroup().getCombination().contains(combtn)){
							System.out.print("###Hall="+room.getHallId()+" Day="+day.getName()+" Time="+exams.getSlotTime()+" Invigilator="+exams.getExam().getInvigilator()+" Subject="+exams.getExam().getSubject());
						}
						else{
							System.out.print(" free ");
					}
				}
				System.out.print("\n");
			}
		}
		}
	}
	
	/*
	 * private void putInPersonalTimeTable(Combination combtn, String roomNo, String
	 * name, TimeSlot exam) { // TODO Auto-generated method stub Week week =
	 * personalTimeTable.get(combtn); Iterator<Day>
	 * daysIterator=week.getWeekDays().iterator(); while(daysIterator.hasNext()){
	 * Day day = daysIterator.next(); if(day.getName().equalsIgnoreCase(name)){
	 * Iterator<TimeSlot> timeslotIterator=day.getTimeSlot().iterator();
	 * while(timeslotIterator.hasNext()){ TimeSlot exam2 = (TimeSlot)
	 * timeslotIterator.next(); if(exam2); } } } }
	 */
		

// creates random assignment of exam using exam objects, subjects and number of exams per day to a room
	
private Hall randomTimetable(Hall room, ArrayList<Subject> subjectsAssigned, ArrayList<Exam> examList) {
		Iterator subIterator= subjectsAssigned.iterator();
	Stack<Exam> examsStack=new Stack();
	while(subIterator.hasNext()){
		Subject subject = (Subject) subIterator.next();
			int noOfExamsPerDay = subject.getnumberOfExamsPerDay();
			for(int i=0; i<noOfExamsPerDay; i++){
				Collections.shuffle(examList);
				Iterator<Exam> classIterator = examList.iterator();
				while(classIterator.hasNext()){
					Exam getExam = classIterator.next();
					if(getExam.getSubject().equalsIgnoreCase(subject.getSubjectName())){
						examsStack.push(getExam);
						break;
					}
				}
			}
		}
		
		Collections.shuffle(examsStack);
		ArrayList<Day> weekdays = room.getWeek().getWeekDays();
		Iterator<Day> daysIterator=weekdays.iterator();
		while(daysIterator.hasNext()){
			Day day = daysIterator.next();
			ArrayList<TimeSlot> timeslots = day.getTimeSlot();
			Iterator timeslotIterator= timeslots.iterator();
			while(timeslotIterator.hasNext() && !(examsStack.isEmpty())){
			TimeSlot lecture = (TimeSlot) timeslotIterator.next();
			lecture.setExam(examsStack.pop());
			Collections.shuffle(examsStack);
			}
		}		
	return room;
	}
	
	

	/**
	 * @return
	 */
	public ArrayList<Hall> getRoom() {
		return rooms;
	}

	public void setRoom(ArrayList<Hall> room) {
		this.rooms = room;
	}



	public ArrayList<Hall> getPracticalRooms() {
		return practicalRooms;
	}



	public void setPracticalRooms(ArrayList<Hall> practicalRooms) {
		this.practicalRooms = practicalRooms;
	}



	public ArrayList<Hall> getTheoryRooms() {
		return theoryRooms;
	}



	public void setTheoryRooms(ArrayList<Hall> theoryRooms) {
		theoryRooms = theoryRooms;
	}



	public ArrayList<StudentGroups> getTheoryStudentGroups() {
		return theoryStudentGroups;
	}



	public void setTheoryStudentGroups(ArrayList<StudentGroups> theoryStudentGroups) {
		this.theoryStudentGroups = theoryStudentGroups;
	}



	public ArrayList<StudentGroups> getPracticalStudentGroups() {
		return practicalStudentGroups;
	}



	public void setPracticalStudentGroups(ArrayList<StudentGroups> practicalStudentGroups) {
		this.practicalStudentGroups = practicalStudentGroups;
	}
}


