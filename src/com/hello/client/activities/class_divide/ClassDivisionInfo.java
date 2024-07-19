package com.hello.client.activities.class_divide;

public class ClassDivisionInfo {
	
	private Long order;
    private String trainingSystem;
    private String trainingField;
    private String trainingProgram;
    private Integer totalStudents;
    private ClassificationCriteria classificationCriteria;
    private Integer numberOfClasses;
    private Integer minStudents;
    private Integer maxStudents;
    private String classNamePrefix;
    private ClassificationCriteria groupClassificationCriteria;
    private Integer numberOfGroups;
    private Integer minStudentsPerGroup;
    private Integer maxStudentsPerGroup;
    private boolean resetGroupOrder;  
   

	public ClassDivisionInfo(Long order, String trainingSystem, String trainingField, String trainingProgram,
			Integer totalStudents, ClassificationCriteria classificationCriteria, Integer numberOfClasses,
			Integer minStudents, Integer maxStudents, String classNamePrefix,
			ClassificationCriteria groupClassificationCriteria, Integer numberOfGroups, Integer minStudentsPerGroup,
			Integer maxStudentsPerGroup, boolean resetGroupOrder) {
		super();
		this.order = order;
		this.trainingSystem = trainingSystem;
		this.trainingField = trainingField;
		this.trainingProgram = trainingProgram;
		this.totalStudents = totalStudents;
		this.classificationCriteria = classificationCriteria;
		this.numberOfClasses = numberOfClasses;
		this.minStudents = minStudents;
		this.maxStudents = maxStudents;
		this.classNamePrefix = classNamePrefix;
		this.groupClassificationCriteria = groupClassificationCriteria;
		this.numberOfGroups = numberOfGroups;
		this.minStudentsPerGroup = minStudentsPerGroup;
		this.maxStudentsPerGroup = maxStudentsPerGroup;
		this.resetGroupOrder = resetGroupOrder;
	}


	public ClassDivisionInfo(long order,String trainingSystem, String trainingField, String trainingProgram, Integer totalStudents,
			ClassificationCriteria classificationCriteria,
			ClassificationCriteria groupClassificationCriteria, boolean resetGroupOrder) {
		super();
		this.order = order;
		this.trainingSystem = trainingSystem;
		this.trainingField = trainingField;
		this.trainingProgram = trainingProgram;
		this.totalStudents = totalStudents;
		this.classificationCriteria = classificationCriteria;
		this.groupClassificationCriteria = groupClassificationCriteria;
		this.resetGroupOrder = resetGroupOrder;
	}





	public String getTrainingSystem() {
		return trainingSystem;
	}




	public String getTrainingField() {
		return trainingField;
	}




	public String getTrainingProgram() {
		return trainingProgram;
	}




	public ClassificationCriteria getClassificationCriteria() {
		return classificationCriteria;
	}




	public Integer getNumberOfClasses() {
		return numberOfClasses;
	}




	public Integer getMinStudents() {
		return minStudents;
	}




	public Integer getMaxStudents() {
		return maxStudents;
	}




	public String getClassNamePrefix() {
		return classNamePrefix;
	}




	public Integer getNumberOfGroups() {
		return numberOfGroups;
	}




	public Integer getMinStudentsPerGroup() {
		return minStudentsPerGroup;
	}




	public Integer getMaxStudentsPerGroup() {
		return maxStudentsPerGroup;
	}




	public boolean isResetGroupOrder() {
		return resetGroupOrder;
	}




	public Integer getTotalStudents() {
		return totalStudents;
	}




	public void setTotalStudents(Integer totalStudents) {
		this.totalStudents = totalStudents;
	}




	public void setTrainingSystem(String trainingSystem) {
		this.trainingSystem = trainingSystem;
	}




	public void setTrainingField(String trainingField) {
		this.trainingField = trainingField;
	}




	public void setTrainingProgram(String trainingProgram) {
		this.trainingProgram = trainingProgram;
	}




	public void setClassificationCriteria(ClassificationCriteria classificationCriteria) {
		this.classificationCriteria = classificationCriteria;
	}




	public void setNumberOfClasses(Integer numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}




	public void setMinStudents(Integer minStudents) {
		this.minStudents = minStudents;
	}




	public void setMaxStudents(Integer maxStudents) {
		this.maxStudents = maxStudents;
	}




	public void setClassNamePrefix(String classNamePrefix) {
		this.classNamePrefix = classNamePrefix;
	}




	public void setNumberOfGroups(Integer numberOfGroups) {
		this.numberOfGroups = numberOfGroups;
	}




	public void setMinStudentsPerGroup(Integer minStudentsPerGroup) {
		this.minStudentsPerGroup = minStudentsPerGroup;
	}




	public void setMaxStudentsPerGroup(Integer maxStudentsPerGroup) {
		this.maxStudentsPerGroup = maxStudentsPerGroup;
	}




	public void setResetGroupOrder(boolean resetGroupOrder) {
		this.resetGroupOrder = resetGroupOrder;
	}














	public ClassificationCriteria getGroupClassificationCriteria() {
		return groupClassificationCriteria;
	}














	public void setGroupClassificationCriteria(ClassificationCriteria groupClassificationCriteria) {
		this.groupClassificationCriteria = groupClassificationCriteria;
	}




	

	@Override
	public String toString() {
		return "ClassDivisionInfo [order=" + order + ", trainingSystem=" + trainingSystem + ", trainingField="
				+ trainingField + ", trainingProgram=" + trainingProgram + ", totalStudents=" + totalStudents
				+ ", classificationCriteria=" + classificationCriteria + ", numberOfClasses=" + numberOfClasses
				+ ", minStudents=" + minStudents + ", maxStudents=" + maxStudents + ", classNamePrefix="
				+ classNamePrefix + ", groupClassificationCriteria=" + groupClassificationCriteria + ", numberOfGroups="
				+ numberOfGroups + ", minStudentsPerGroup=" + minStudentsPerGroup + ", maxStudentsPerGroup="
				+ maxStudentsPerGroup + ", resetGroupOrder=" + resetGroupOrder + "]";
	}


	public long getOrder() {
		return order;
	}


	public void setOrder(Long order) {
		this.order = order;
	}

    
    
	
}

