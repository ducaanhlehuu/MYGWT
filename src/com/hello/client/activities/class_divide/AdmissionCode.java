package com.hello.client.activities.class_divide;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.hello.shared.Config;

public class AdmissionCode {
	@Id private Long id;
	@Index private String code = Config.NULL_TXT;
	@Index private Long roodId = Config.NULL_ID;
	@Index private Long programId = Config.NULL_ID;
	private String schoolName = Config.NULL_TXT;
	private String name = Config.NULL_TXT;
	@Index private Long majorId = Config.NULL_ID;
	@Index private String majorCode = Config.NULL_TXT;
	private String major = Config.NULL_TXT;
	private int quantity = 0;
	private List<String> combination = new ArrayList<String>();
	@Index private int year = -1;
	@Index private int type = -1;
	private String contact = Config.TEXT_EMPTY;
	private String address = Config.TEXT_EMPTY;
	private String admissionAddress = Config.TEXT_EMPTY;
	
	public AdmissionCode(){
	}
	
	public AdmissionCode(String code, String name, int quantity, List<String> combines1, String schoolName) {
		this.setCode(code);
		this.setName(name);
		this.setQuantity(quantity);
		if (combines1 != null && !combines1.isEmpty()) {
			for (String tmp : combines1) {
				this.getCombination().add(tmp);
			}
		}
		this.setSchoolName(schoolName);
	}
	
	
	
	
	

	public AdmissionCode(String code, int quantity) {
		super();
		this.code = code;
		this.quantity = quantity;
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAdmissionAddress() {
		return admissionAddress;
	}
	
	public void setAdmissionAddress(String admissionAddress) {
		this.admissionAddress = admissionAddress;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Long getId() {
		return id;
	}

	public Long getRoodId() {
		return roodId;
	}

	public void setRoodId(Long roodId) {
		this.roodId = roodId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<String> getCombination() {
		return combination;
	}

	public void setCombination(List<String> combination) {
		this.combination = combination;
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

	public Long getProgramId() {
		return programId;
	}

	public void setProgramId(Long programId) {
		this.programId = programId;
	}

	public Long getMajorId() {
		return majorId;
	}

	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
	public String getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
