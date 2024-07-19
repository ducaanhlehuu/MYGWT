package com.hello.client.activities.class_divide;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.hello.shared.Config;
import com.hello.shared.IBasic;

/**
 * Lớp Sinh viên (lớp hành chính dành cho Quản lý lớp)
 * 
 * @author thnguyen
 *
 */
public class StudentClass implements IBasic {
	@Id
	private Long id;
	@Index
	private String name = Config.NULL_TXT;
	@Index
	private String parentName = Config.NULL_TXT;
	@Index
	private String parentCode = Config.NULL_TXT;
	@Index
	private Long parentId = Config.NULL_ID;
	@Index
	private String studentYear = Config.NULL_TXT;
	@Index
	private int year = 0;
	@Index
	private Long rootId = Config.NULL_ID;
	@Index
	private Long teacherId = Config.NULL_ID;
	@Index
	private String monitorId = Config.NULL_TXT;
	@Index
	private String secretaryId = Config.NULL_TXT;
	@Index
	private Long majorId = Config.NULL_ID;
	@Index
	private Long programId = Config.NULL_ID;
	@Index
	private String classId = Config.NULL_TXT;
	@Index private List<String> classIds = new ArrayList<String>();
	private String schoolName = Config.NULL_TXT;
	private String majorName = Config.NULL_TXT;
	private String admissionCode = Config.NULL_TXT;
	private String programName = Config.NULL_TXT;
	private int status = 0;
	private int studentNum = 0;
	private String notes = Config.NULL_TXT;
	private List<Student> students;
	
	
	public StudentClass() {
	}

	public StudentClass(int year, String name, Long rootId, String schoolName, Long programId, String programName,
			Long majorId, String majorName, Long parentId, String parentName) {
		this.setYear(year);
		this.setName(name);
		this.setRootId(rootId);
		this.setSchoolName(schoolName);
		this.setProgramId(programId);
		this.setProgramName(programName);
		this.setMajorId(majorId);
		this.setMajorName(majorName);
		this.setParentId(parentId);
		this.setParentName(parentName);
	}

	public StudentClass(String name, int studentNum) {
		super();
		this.name = name;
		this.studentNum = studentNum;
	}

	

	public StudentClass(String name, String parentName, List<Student> students) {
		super();
		this.name = name;
		this.parentName = parentName;
		this.students = students;
	}

	public StudentClass(String name, String parentName, int studentNum) {
		super();
		this.name = name;
		this.parentName = parentName;
		this.studentNum = studentNum;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentYear() {
		return studentYear;
	}

	public void setStudentYear(String studentYear) {
		this.studentYear = studentYear;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public void setId(Object id) {
	}

	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}

	public int getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(int studentNum) {
		this.studentNum = studentNum;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}

	public String getMonitorId() {
		return monitorId;
	}

	public void setMonitorId(String monitorId) {
		this.monitorId = monitorId;
	}

	public String getSecretaryId() {
		return secretaryId;
	}

	public void setSecretaryId(String secretaryId) {
		this.secretaryId = secretaryId;
	}

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Long getMajorId() {
		return majorId;
	}

	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	public String getAdmissionCode() {
		return admissionCode;
	}

	public void setAdmissionCode(String admissionCode) {
		this.admissionCode = admissionCode;
	}

	public List<String> getClassIds() {
		return classIds;
	}

	public void setClassIds(List<String> classIds) {
		this.classIds = classIds;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "StudentClass [name=" + name + ", parentName=" + parentName +"]";
	}
	

	
	
}
