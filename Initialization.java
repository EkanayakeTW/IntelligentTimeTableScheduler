/**
 * 
 */
package ITS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * @author Ekanayaketw
 *
 */
public class Initialization {
	
	//this class takes all inputs from a file. courseID, courseName, roomID's, subjects and professors associated with course
		//currently hardcoded by taking one course with 6 subjects and 6 teachers
		
		private ArrayList<Subject> subjects=new ArrayList();
		private ArrayList<Invigilator> invigilators=new ArrayList();
		private ArrayList<TimeTable> timetables=new ArrayList();
		private ArrayList<Exam> exams=new ArrayList<>();
		private ArrayList<Combination> combinations=new ArrayList<>();
				
		//reads input from a file.
		
		public void readInput() throws IOException{
					
			ArrayList<Hall> classroom=new ArrayList<>();
			Hall room1 = new Hall("D101", 20, false, "Common");
			classroom.add(room1);
			Hall room2 = new Hall("E101", 20, false, "ComputerScience");
			classroom.add(room2);
			Hall room3 = new Hall("LAB1", 20, true, "ComputerScience");
			classroom.add(room3);
			Hall room4 = new Hall("LAB2", 20, true,"Software Engineering");
			classroom.add(room4);
			Hall room5 = new Hall("G101", 20, false, "Data Sciece");
			classroom.add(room5);
			Hall room6 = new Hall("H101", 20, false, "Cyber Security");
			classroom.add(room6);
			Hall room7 = new Hall("I101", 60, false, "CSN");
			classroom.add(room6);
			
			
			invigilators.add(new Invigilator(1, "Shruti", "IR/IRlab/DM"));
			invigilators.add(new Invigilator(2, "Snehal", "P&S"));
			invigilators.add(new Invigilator(3, "Ramrao", "DS"));
			invigilators.add(new Invigilator(4, "Ranjit", "WR"));
			invigilators.add(new Invigilator(5, "Shekhar", "TOC"));
			invigilators.add(new Invigilator(6, "Monica", "SS"));
			invigilators.add(new Invigilator(7, "Ravi", "R"));
			invigilators.add(new Invigilator(8, "Amit", "ML/MLlab"));
			invigilators.add(new Invigilator(9, "Rama", "DAA/UML"));
			
			createLExams(invigilators);
			
			TimeTable timetb1=new TimeTable(classroom,exams, invigilators);
			//timetb1.initialization(classroom, classes);
			//TimeTable timetb2=new TimeTable(classroom, classes);
			//TimeTable timetb3=new TimeTable(classroom, classes);
					
			int courseid = 1;
			String courseName="MSc.I.T. Part I";
			System.out.println("reading input.......");
			subjects.add(new Subject(1,"IR",4,false, "ComputerScience"));
			subjects.add(new Subject(2,"P&S",4,false,"ComputerScience"));
			subjects.add(new Subject(3,"DS",4,false,"ComputerScience"));
			subjects.add(new Subject(4,"WR",1,false,"Common"));
			subjects.add(new Subject(5,"TOC",4,false,"ComputerScience"));
			subjects.add(new Subject(6,"IRlab",3,true,"ComputerScience"));
			subjects.add(new Subject(7,"JAVA",3,true,"ComputerScience"));

				
			System.out.println("new course creation.......");
			Course course1 = new Course(courseid, courseName, subjects);
			course1.createCombination("IR/P&S/DS/WR/TOC/IRlab/JAVA/", 20);		
			course1.createStudentGroups();		
			ArrayList<StudentGroups> studentGroups = course1.getStudentGroups();
			timetb1.addStudentGroups(studentGroups);
			//combinations.addAll(course1.getCombinations());
			
			//timetb2.addStudentGroups(studentGroups);
			///timetb3.addStudentGroups(studentGroups);
			subjects.clear();
			
			subjects.add(new Subject(8,"DM",4,false,"ComputerScience"));
			subjects.add(new Subject(9,"DAA",4,false,"ComputerScience"));
			subjects.add(new Subject(10,"SS",1,false,"ComputerScience"));
			subjects.add(new Subject(11,"ML",4,false,"Common"));
			subjects.add(new Subject(12,"UML",4,false,"ComputerScience"));
			subjects.add(new Subject(13,"MLlab",3,true,"ComputerScience"));
			subjects.add(new Subject(14,"R",3,true,"ComputerScience"));
			
			Course course2 = new Course(2, "MSc.I.T. Part II", subjects);
			course2.createCombination("DM/DAA/SS/ML/UML/MLlab/R/", 20);
			course2.createStudentGroups();
			studentGroups = course2.getStudentGroups();
			timetb1.addStudentGroups(studentGroups);
			//combinations.addAll(course2.getCombinations());
			//timetb2.addStudentGroups(studentGroups);
			//timetb3.addStudentGroups(studentGroups);
			
			System.out.println("Setting tt.......");
			
			System.out.println("adding tt.......");
			timetb1.initializeTimeTable();
			//timetb2.initializeTimeTable();
			//timetb3.initializeTimeTable();
			timetables.add(timetb1);
			//timetable.add(timetb2);
			//timetable.add(timetb3);
			
					
			System.out.println("populating.......");
			
			
			
			//display();
			
			populateTimeTable(timetb1);
			GeneticAlgorithm ge=new GeneticAlgorithm();
			
			//ge.fitness(timetb1);
//			timetb1.createTimeTableGroups(combinations);
			ge.populationAccepter(timetables);
//			//ge.fitness(timetb2);
			
			//ge.fitness(timetb3);
			
			//populateTimeTable();
		}
		
		public void populateTimeTable(TimeTable timetb1){
			int i=0;
			System.out.println("populating started.......");
			while(i<3){
				TimeTable tempTimetable = timetb1;
				ArrayList<Hall> allrooms = tempTimetable.getRoom();
				Iterator<Hall> allroomsIterator = allrooms.iterator();
				while(allroomsIterator.hasNext()){
					Hall room = allroomsIterator.next();
					ArrayList<Day> weekdays = room.getWeek().getWeekDays();
					Collections.shuffle(weekdays);
					if(!room.isLaboratory()){
						Iterator<Day> daysIterator=weekdays.iterator();
						while(daysIterator.hasNext()){
							Day day = daysIterator.next();
							Collections.shuffle(day.getTimeSlot());
						}
					}				
				}
				timetables.add(tempTimetable);
				i++;
			}
			System.out.println("populating done.......");
			System.out.println("display called.......");
			display();
		}
		
		private void createLExams(ArrayList<Invigilator> invigilators) {
			// TODO Auto-generated method stub
			
			java.util.Iterator<Invigilator> invigilatorIterator=invigilators.iterator();
			while(invigilatorIterator.hasNext()){
				Invigilator invigilator=invigilatorIterator.next();
				ArrayList<String> subjectsAssigned = invigilator.getSubjectAssigned();
				Iterator<String> subjectIterator = subjectsAssigned.iterator();
				while(subjectIterator.hasNext()){
					String subject = subjectIterator.next();
					exams.add(new Exam(invigilator, subject));
				}
			}
		}
		
		//creates another 3 timetable objects for population by taking first timetable and shuffling it.
		
	/*
	 * public void populateTimeTable(){ int i=0;
	 * System.out.println("populating started......."); while(i<6){ TimeTable
	 * tempTimetable = timetables; ArrayList<Hall> allrooms =
	 * tempTimetable.getRoom(); Iterator<Hall> allroomsIterator =
	 * allrooms.iterator(); while(allroomsIterator.hasNext()){ Hall room =
	 * allroomsIterator.next(); ArrayList<Day> weekdays =
	 * room.getWeek().getWeekDays(); Iterator<Day> daysIterator=weekdays.iterator();
	 * while(daysIterator.hasNext()){ Day day = daysIterator.next();
	 * Collections.shuffle(day.getTimeSlot()); } } timetable.add(tempTimetable);
	 * i++; } System.out.println("populating done.......");
	 * System.out.println("display called......."); display();
	 * 
	 * GeneticAlgorithm.populationAccepter(timetable); }
	 */
		
		//displays all timetables
		
		private void display() {
			// TODO Auto-generated method stub
			int i=1;
			System.out.println("displaying all tt's.......");
			Iterator<TimeTable> timetableIterator = timetables.iterator();
			while(timetableIterator.hasNext()){
				System.out.println("+++++++++++++++++++++++++++++++++++++++++\nTime Table no. "+i);
				TimeTable currentTimetable = timetableIterator.next();
				System.out.println("Score : "+currentTimetable.getFittness());
				ArrayList<Hall> allrooms = currentTimetable.getRoom();
				Iterator<Hall> allroomsIterator = allrooms.iterator();
				while(allroomsIterator.hasNext()){
					Hall room = allroomsIterator.next();
					System.out.println("Hall: "+room.getHallId());
					ArrayList<Day> weekdays = room.getWeek().getWeekDays();
					Iterator<Day> daysIterator=weekdays.iterator();
					while(daysIterator.hasNext()){
						Day day = daysIterator.next();
						ArrayList<TimeSlot> timeslots = day.getTimeSlot();
						Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
						//System.out.print(""+day.getName()+": ");
						while(timeslotIterator.hasNext()){
							TimeSlot exams = (TimeSlot) timeslotIterator.next();
							if(exams.getExam()!=null){
						
								System.out.print("("+exams.getExam().getSubject()+"#"+exams.getExam().getInvigilator().getInvigilatorName()+"#"+exams.getExam().getStudentGroup().getName().split("/")[0]+")");
							}
							else{
								System.out.print("   free   ");
							}
						}
						System.out.print("\n");
					}
					System.out.print("\n\n");
				}
				i++;
			}
		}

}
