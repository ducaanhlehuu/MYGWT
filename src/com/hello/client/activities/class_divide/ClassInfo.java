package com.hello.client.activities.class_divide;


public class ClassInfo {
	private String trainingSystem;
    private String trainingField;
    private String trainingProgram;
    private Integer totalStudents;
    
    
	public ClassInfo(String trainingSystem, String trainingField, String trainingProgram, Integer totalStudents) {
		super();
		this.trainingSystem = trainingSystem;
		this.trainingField = trainingField;
		this.trainingProgram = trainingProgram;
		this.totalStudents = totalStudents;
	}
	
	
	
	public ClassInfo() {
		super();
	}



	public String getTrainingSystem() {
		return trainingSystem;
	}
	public void setTrainingSystem(String trainingSystem) {
		this.trainingSystem = trainingSystem;
	}
	public String getTrainingField() {
		return trainingField;
	}
	public void setTrainingField(String trainingField) {
		this.trainingField = trainingField;
	}
	public String getTrainingProgram() {
		return trainingProgram;
	}
	public void setTrainingProgram(String trainingProgram) {
		this.trainingProgram = trainingProgram;
	}
	public Integer getTotalStudents() {
		return totalStudents;
	}
	public void setTotalStudents(Integer totalStudents) {
		this.totalStudents = totalStudents;
	}
    

}