/**
 * 
 */
package ITS;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import ITS.Hall;

/**
 * @author Ekanayaketw
 *
 */
public class GeneticAlgorithm {
	
	private static TimeTable GlobalBestTimetable;
	private static int min=1000;
	private static ArrayList<String>weekDayNames=new ArrayList<>();
	private static ArrayList<String>lectureTimings=new ArrayList<>();
	
	public static void populationAccepter(ArrayList<TimeTable> timeTableCollection) throws IOException{
		// randomly got population from the initialization class
		Iterator<TimeTable> timetableIterator=timeTableCollection.iterator();		
		for (Iterator<TimeTable> iterator = timeTableCollection.iterator(); iterator.hasNext();) {
			TimeTable tt = iterator.next();
				fitness(tt);			
		}		
		createWeek();
		createLectureTime();
		selection(timeTableCollection);		
	}
	
	private static void createWeek(){
		String[] weekDaysName=new DateFormatSymbols().getWeekdays();
		for (int i = 1; i < weekDaysName.length; i++) {
	        System.out.println("weekday = " + weekDaysName[i]);
	    	//if(!(weekDaysName[i].equalsIgnoreCase("Sunday"))){
	    	if (!(i == Calendar.SUNDAY))
	    	weekDayNames.add(weekDaysName[i]);
	    		}
	    	}
	
	
	private static void createLectureTime(){
		for(int i=9; i<16; i++){
			//if(i!=12){
				lectureTimings.add(i+":00"+" TO "+(i+1)+":00");
			//}			
		}
	}

	public static void selection(ArrayList<TimeTable> timetables) throws IOException{
		int iterations=50;
		int i=1;
		ArrayList<TimeTable> mutants=new ArrayList<>();
		Iterator<TimeTable> ttItr=timetables.iterator();
		while(ttItr.hasNext()){
			fitness(ttItr.next());
		}
		while(iterations!=0){
		//Iterator<Integer> scoreIterator=ttscore.keySet().iterator();
			Iterator<TimeTable> timetableIterator=timetables.iterator();
			//Iterator<TimeTable> tempIterator=timetableIterator;		
		//min= timetableIterator.next().getFittness();
		
		while (timetableIterator.hasNext()) {
			TimeTable tt = timetableIterator.next();
			int score=tt.getFittness();		
			if(score<min){
				min=score;
				GlobalBestTimetable=tt;
				display();
				writeToExcelFile();
			}			
			}
		
		if(min==0){
			//ArrayList<TimeTable> timeTable=new ArrayList();
			//timeTable.add(GlobalBestTimetable);
			display();
			System.exit(0);
		}
		else{
			System.out.println("Iteration :"+i);
			i++;
			iterations--;			
		//timetables.remove(GlobalBestTimetable);			
			for (Iterator<TimeTable> iterator = timetables.iterator(); iterator.hasNext();) {
				TimeTable timetable1 = iterator.next();
				//TimeTable timetable2 = (TimeTable) iterator.next();				
//				SingleTimeTable timetable1=ttscore.get(key1);
//				SingleTimeTable timetable2=ttscore.get(key2);				
				TimeTable childTimetable=crossOver(timetable1);	
//				if(childTimetable.getFittness()< GlobalBestTimetable.getFittness()){
//					GlobalBestTimetable=childTimetable;
//				}	
//				for (int j = 0; j < arr.length; j++) {
//					TimeTable singleTimeTable = arr[j];					
					TimeTable mutant=Mutation(childTimetable);
//					if(childTimetable.getFittness()< GlobalBestTimetable.getFittness()){
//						GlobalBestTimetable=childTimetable;
//					}
					mutants.add(mutant);
				//}		
			}			
			
			timetables.clear();			
			for (int j = 0; j < mutants.size(); j++) {
				fitness(mutants.get(j));
				timetables.add(mutants.get(j));
			}
			mutants.clear();
		}	
		}		
		display();
	}
	
	public static void fitness(TimeTable timetable){		
		ArrayList<Hall> rooms=timetable.getRoom();
		Iterator<Hall> roomIterator1 = rooms.iterator();		
		while(roomIterator1.hasNext()){			
			int score=0;
			Hall room1 = roomIterator1.next(); 
			Iterator<Hall> roomIterator2 = rooms.iterator();
			while(roomIterator2.hasNext()){		
				Hall room2 = roomIterator2.next();
				if(room2!=room1){
					ArrayList<Day> weekdays1 = room1.getWeek().getWeekDays();
					ArrayList<Day> weekdays2 = room2.getWeek().getWeekDays();
					Iterator<Day> daysIterator1=weekdays1.iterator();
					Iterator<Day> daysIterator2=weekdays2.iterator();
					while(daysIterator1.hasNext() && daysIterator2.hasNext()){
						Day day1 = daysIterator1.next();
						Day day2 = daysIterator2.next();
						ArrayList<TimeSlot> timeslots1 = day1.getTimeSlot();
						ArrayList<TimeSlot> timeslots2 = day2.getTimeSlot();
						Iterator<TimeSlot> timeslotIterator1= timeslots1.iterator();
						Iterator<TimeSlot> timeslotIterator2= timeslots2.iterator();
						while(timeslotIterator1.hasNext() && timeslotIterator2.hasNext()){
							TimeSlot lecture1=timeslotIterator1.next();
							TimeSlot lecture2=timeslotIterator2.next();							
							if(lecture1.getExam()!=null  &&  lecture2.getExam()!=null){
//							String subject1=exam1.getExam().getSubject();
//							String subject2=exam2.getExam().getSubject();							
							String professorName1=lecture1.getExam().getInvigilator().getInvigilatorName();
							String professorName2=lecture2.getExam().getInvigilator().getInvigilatorName();							
							String stgrp1=lecture1.getExam().getStudentGroup().getName();
							String stgrp2=lecture2.getExam().getStudentGroup().getName();							
							if(stgrp1.equals(stgrp2) || professorName1.equals(professorName2)){
								score=score+1;
							}
								ArrayList<Combination> stcomb1 = lecture1.getExam().getStudentGroup().getCombination();
								Iterator<Combination> stcombItr = stcomb1.iterator();
								while(stcombItr.hasNext()){
									if(lecture2.getExam().getStudentGroup().getCombination().contains(stcombItr.next())){
										score = score+1;
										break;
									}
								}
							
							}
						}
					}
				}
			}
			timetable.setFittness(score);			
			//ttscore.put(score,timetable);
			//System.out.println("\nScore : "+score);
			}
		System.out.println("Score..................................."+timetable.getFittness());
//		Iterator iterator = ttscore.keySet().iterator(); 
//		while (iterator.hasNext()) {  
//			   ClassRoom key = (ClassRoom) iterator.next();  
//			   int value = (int) ttscore.get(key);  
//			   
//			   System.out.println("\nScore : "+value);  
//			}  
	}

	private static TimeTable Mutation(TimeTable parentTimetable) {		
		TimeTable mutantTimeTable=parentTimetable;
		int rnd1,rnd2;
		Random randomGenerator = new Random();
		ArrayList<Hall> presentClassroom=mutantTimeTable.getRoom();
		for (Iterator<Hall> iterator = presentClassroom.iterator(); iterator.hasNext();) {
			Hall classRoom = iterator.next();			
			//for (Iterator <Day> iterator2 = classRoom.getWeek().getWeekDays().iterator(); iterator2.hasNext();) {
				
				
				//want to shuffle is not the days but the schedule for the day!				
				rnd1=randomGenerator.nextInt(5);
				rnd2=-1;
				while(rnd1!=rnd2){
					rnd2=randomGenerator.nextInt(5);
				}
				ArrayList<Day> weekDays = classRoom.getWeek().getWeekDays();
				Day day1=weekDays.get(rnd1);
				Day day2=weekDays.get(rnd2);
				
				
				ArrayList<TimeSlot> timeSlotsOfday1=day1.getTimeSlot();
				ArrayList<TimeSlot> timeSlotsOfday2=day2.getTimeSlot();
				
				day1.setTimeSlot(timeSlotsOfday2);
				day2.setTimeSlot(timeSlotsOfday1);
				
				// if i am limiting this to two days i am breaking out... 
				//or else all the days will get exchanged in a sorted order
				//like monday-tue,wed thu,fri sat in pairs!
				break;
			//}			
		}		
		// apply repairstrategy here! check whether mutant 
		//better than parent and vice versa and choose the best		
		return mutantTimeTable;	
	}

	private static TimeTable crossOver(TimeTable fatherTimeTable){
		// let us say that we give father the priority to stay as the checker!
		// in the outer loop		
		Random randomGenerator = new Random();
		Iterator<Hall> parentTimeTableClassRooms=fatherTimeTable.getRoom().iterator();		
		while(parentTimeTableClassRooms.hasNext()) {
			Hall room = parentTimeTableClassRooms.next();
			if(!room.isLaboratory()){
				ArrayList<Day> days = room.getWeek().getWeekDays();
				int i=0;
				while(i<3){
					int rnd=randomGenerator.nextInt(5);
					Day day = days.get(rnd);
					Collections.shuffle(day.getTimeSlot());
					i++;
				}			
			}
			
		}
		return fatherTimeTable;
	}
	
	private static void writeToExcelFile() throws IOException{
		FileWriter writer = new FileWriter("timetable.csv");
		PrintWriter pw = new PrintWriter(writer);
		int i=0;
		writer.append("\n\nMinimum : "+min);
		writer.append("\n\nScore : "+GlobalBestTimetable.getFittness());
		writer.append("\n\n (Subject#		Invigilator#			Student Group)");
			ArrayList<Hall> allrooms = GlobalBestTimetable.getRoom();
			Iterator<Hall> allroomsIterator = allrooms.iterator();
			while(allroomsIterator.hasNext()){
				Hall room = allroomsIterator.next();
				writer.append("\n\nHall Number: "+room.getHallId());
				ArrayList<Day> weekdays = room.getWeek().getWeekDays();
				Iterator<Day> daysIterator=weekdays.iterator();
				Iterator<String> lectTimeItr = lectureTimings.iterator();
				writer.append("\n\nTimings: ,");
				while(lectTimeItr.hasNext()){
					writer.append(lectTimeItr.next()+",");
				}
				i=0;
				writer.append("\nDays\n");
				while(daysIterator.hasNext()){
					Day day = daysIterator.next();
					writer.append(/*Day: */""+weekDayNames.get(i)+",");
					ArrayList<TimeSlot> timeslots = day.getTimeSlot();
					i++;
					for(int k=0; k<timeslots.size();k++){
						if(k==3){
							writer.append("BREAK,");
						}
							TimeSlot exams = timeslots.get(k);
							if(exams.getExam()!=null){
								writer.append("("+exams.getExam().getSubject()+"#"+exams.getExam().getInvigilator().getInvigilatorName()+"#"+exams.getExam().getStudentGroup().getName().split("/")[0]+")"+",");
							}
						
							else{
								writer.append("FREE EXAM,");
							}
						}
					writer.append("\n");
				}
				writer.append("\n");
			}
//			i++;			
		writer.append("This is Tiny Ekanayake copyright @");
			writer.flush();
		    writer.close();
	}
	

	
	private static void display() {
		// TODO Auto-generated method stub
		int i=0,j=0;
		System.out.println("Minimum : "+min);
			System.out.println("\nScore : "+GlobalBestTimetable.getFittness());
			ArrayList<Hall> allrooms = GlobalBestTimetable.getRoom();
			Iterator<Hall> allroomsIterator = allrooms.iterator();
			while(allroomsIterator.hasNext()){
				Hall room = allroomsIterator.next();
				System.out.println("\nRoom: "+room.getHallId());
				ArrayList<Day> weekdays = room.getWeek().getWeekDays();
				Iterator<Day> daysIterator=weekdays.iterator();
				Iterator<String> lectTimeItr = lectureTimings.iterator();
				System.out.print("\nTimings:    ");
				while(lectTimeItr.hasNext()){
					System.out.print(" "+lectTimeItr.next()+" ");
				}
				i=0;
				System.out.print("\n");
				while(daysIterator.hasNext()){
					Day day = daysIterator.next();
					System.out.print("Day: "+weekDayNames.get(i));
					ArrayList<TimeSlot> timeslots = day.getTimeSlot();
					//Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
					i++;
					//System.out.print(""+day.getName()+": ");
					for(int k=0; k<timeslots.size();k++){
						if(k==3){
							System.out.print("       BREAK       ");
						}
							TimeSlot exams = timeslots.get(k);
							if(exams.getExam()!=null){
							
								System.out.print("  ("+exams.getExam().getSubject()+"#"+exams.getExam().getInvigilator().getInvigilatorName()+"#"+exams.getExam().getStudentGroup().getName().split("/")[0]+")");
							}
						
							else{
								System.out.print(" FREE EXAM ");
							}
						}
					System.out.print("\n");
				}
				System.out.print("\n");
			}
			System.out.println("This is Tiny Ekanayake copyright @");
	}

}
