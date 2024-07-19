package com.hello.client.activities.class_divide;

public class Student {
	String studentId;
	int gender;
	double grade;
	String majorId;
	
	public Student(String studentId, int gender, double grade, String majorId) {
		super();
		this.studentId = studentId;
		this.gender = gender;
		this.grade = grade;
		this.majorId = majorId;
	}
	public Student(String studentId, int gender, double grade) {
		this.studentId = studentId;
		this.gender = gender;
		this.grade = grade;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	public String getMajorId() {
		return majorId;
	}
	public void setMajorId(String majorId) {
		this.majorId = majorId;
	}
}
