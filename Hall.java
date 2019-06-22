/**
 * 
 */
package ITS;

import java.util.Date;

/**
 * @author Ekanayaketw
 *
 */
public class Hall {
	
	
	//represents each Hall
	
private String HallId;
private int size;
private boolean islaboratory;
private Week week;
private String department;

public Hall(String id, int i, boolean islab, String dept){
	this.HallId=id;
	this.setWeek(new Week());
	this.setSize(i);
	this.islaboratory=islab;
	this.department=dept;
}


public boolean isLaboratory() {
	return islaboratory;
}
public void setLaboratory(boolean laboratory) {
	this.islaboratory = laboratory;
}

public String getHallId() {
	return HallId;
}
public void setHallId(String HallId) {
	this.HallId = HallId;
}

public Week getWeek() {
	return week;
}

public void setWeek(Week week) {
	this.week = week;
}

public int getSize() {
	return size;
}

public void setSize(int size) {
	this.size = size;
}


public String getDepartment() {
	return department;
}


public void setDepartment(String department) {
	this.department = department;
}

}
