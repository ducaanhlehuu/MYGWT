package com.hello.client.activities.class_divide;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GetData {
private static int finalgroupQty = 0;
	
	public static int getFinalgroupQty() {
		return finalgroupQty;
	}

	public static void setFinalgroupQty(int finalgroupQty) {
		GetData.finalgroupQty = finalgroupQty;
	}

	public static DivisionResult previewDivideStudents(String code, int studentNum, int classQty, int groupQty,
			boolean isResetGroupCount) {

		List<StudentClass> classes = previewCreateClasses(code, studentNum, classQty);
		List<StudentClass> groups = previewCreateGroups(classes, groupQty, isResetGroupCount);

		return new DivisionResult(classes, groups, classQty * groupQty);
	}

	private static List<StudentClass> previewCreateClasses(String code, int studentNum, int classQty) {
		List<StudentClass> classes = new ArrayList<>();

		if (classQty <= 0 || studentNum <= 0) {
			return classes; // Trả về danh sách rỗng nếu đầu vào không hợp lệ
		}

		int baseStudentsPerClass = studentNum / classQty;
		int remainingStudents = studentNum % classQty;

		for (int i = 0; i < classQty; i++) {
			String className = code + "-" + (i + 1);
			int studentsInThisClass = baseStudentsPerClass;

			// Phân bố các sinh viên dư vào các lớp đầu tiên
			if (remainingStudents > 0) {
				studentsInThisClass++;
				remainingStudents--;
			}

			StudentClass newClass = new StudentClass(className, null, studentsInThisClass);
			classes.add(newClass);
		}

		return classes;
	}

	private static List<StudentClass> previewCreateGroups(List<StudentClass> classes, int groupQty,
			boolean isResetGroupCount) {
		List<StudentClass> groups = new ArrayList<>();

		for (StudentClass classObj : classes) {
			int studentsPerGroup = classObj.getStudentNum() / groupQty;
			int remainingStudents = classObj.getStudentNum() % groupQty;

			for (int i = 0; i < groupQty; i++) {
				String groupName;
				if (isResetGroupCount) {
					groupName = "Tổ " + (i + 1);
				} else {
					groupName = "Tổ " + ++finalgroupQty;
				}

				int studentsInThisGroup = studentsPerGroup;
				if (remainingStudents > 0) {
					studentsInThisGroup++;
					remainingStudents--;
				}

				StudentClass group = new StudentClass(groupName, classObj.getName(), studentsInThisGroup);
				groups.add(group);
			}
		}

		return groups;
	}
	
	public static DivisionResult divideStudents(DivisionResult divisionResult, List<Student> students) {
		List<StudentClass> classes = divideMajorToClasses(divisionResult.getClasses(), students);
		List<StudentClass> groups = divideClassToGroups(classes, divisionResult.getGroups());

		// Cập nhật lại số lượng sinh viên cho mỗi lớp và tổ
		for (StudentClass cls : classes) {
			cls.setStudentNum(cls.getStudents().size());
		}
		for (StudentClass group : groups) {
			group.setStudentNum(group.getStudents().size());
		}

		return new DivisionResult(classes, groups, divisionResult.getGroupQty());
	}

	private static List<StudentClass> divideMajorToClasses(List<StudentClass> classes, List<Student> students) {
	    List<StudentClass> dividedClasses = new ArrayList<>(classes);
	    List<Student> maleStudents = students.stream().filter(s -> s.getGender() == 1).collect(Collectors.toList());
	    List<Student> femaleStudents = students.stream().filter(s -> s.getGender() == 0).collect(Collectors.toList());

	    int totalStudents = students.size();
	    int classCount = classes.size();
	    int baseStudentsPerClass = totalStudents / classCount;
	    int remainingStudents = totalStudents % classCount;

	    double maleRatio = (double) maleStudents.size() / totalStudents;

	    for (int i = 0; i < classCount; i++) {
	        StudentClass currentClass = dividedClasses.get(i);
	        currentClass.setStudents(new ArrayList<>());
	        
	        int studentsInThisClass = baseStudentsPerClass + (i < remainingStudents ? 1 : 0);
	        int malesInThisClass = (int) Math.round(studentsInThisClass * maleRatio);
	        int femalesInThisClass = studentsInThisClass - malesInThisClass;

	        for (int j = 0; j < malesInThisClass && !maleStudents.isEmpty(); j++) {
	            currentClass.getStudents().add(maleStudents.remove(0));
	        }

	        for (int j = 0; j < femalesInThisClass && !femaleStudents.isEmpty(); j++) {
	            currentClass.getStudents().add(femaleStudents.remove(0));
	        }

	        // Nếu còn thiếu sinh viên, bổ sung từ danh sách còn lại
	        while (currentClass.getStudents().size() < studentsInThisClass) {
	            if (!maleStudents.isEmpty()) {
	                currentClass.getStudents().add(maleStudents.remove(0));
	            } else if (!femaleStudents.isEmpty()) {
	                currentClass.getStudents().add(femaleStudents.remove(0));
	            } else {
	                break; // Không còn sinh viên để thêm
	            }
	        }
	    }

	    return dividedClasses;
	}

	private static List<StudentClass> divideClassToGroups(List<StudentClass> classes, List<StudentClass> groups) {
	    List<StudentClass> dividedGroups = new ArrayList<>(groups);
	    
	    for (StudentClass cls : classes) {
	        List<Student> maleStudents = cls.getStudents().stream()
	            .filter(s -> s.getGender() == 1)
	            .collect(Collectors.toList());
	        List<Student> femaleStudents = cls.getStudents().stream()
	            .filter(s -> s.getGender() == 0)
	            .collect(Collectors.toList());
	        
	        List<StudentClass> classGroups = dividedGroups.stream()
	            .filter(g -> g.getParentName().equals(cls.getName()))
	            .collect(Collectors.toList());
	        
	        int totalStudents = cls.getStudents().size();
	        int groupCount = classGroups.size();
	        int baseStudentsPerGroup = totalStudents / groupCount;
	        int remainingStudents = totalStudents % groupCount;
	        
	        double maleRatio = (double) maleStudents.size() / totalStudents;
	        
	        for (int i = 0; i < groupCount; i++) {
	            StudentClass currentGroup = classGroups.get(i);
	            currentGroup.setStudents(new ArrayList<>());
	            
	            int studentsInThisGroup = baseStudentsPerGroup + (i < remainingStudents ? 1 : 0);
	            int malesInThisGroup = (int) Math.round(studentsInThisGroup * maleRatio);
	            int femalesInThisGroup = studentsInThisGroup - malesInThisGroup;
	            
	            for (int j = 0; j < malesInThisGroup && !maleStudents.isEmpty(); j++) {
	                currentGroup.getStudents().add(maleStudents.remove(0));
	            }
	            
	            for (int j = 0; j < femalesInThisGroup && !femaleStudents.isEmpty(); j++) {
	                currentGroup.getStudents().add(femaleStudents.remove(0));
	            }
	            
	            // Nếu còn thiếu sinh viên, bổ sung từ danh sách còn lại
	            while (currentGroup.getStudents().size() < studentsInThisGroup) {
	                if (!maleStudents.isEmpty()) {
	                    currentGroup.getStudents().add(maleStudents.remove(0));
	                } else if (!femaleStudents.isEmpty()) {
	                    currentGroup.getStudents().add(femaleStudents.remove(0));
	                } else {
	                    break; // Không còn sinh viên để thêm
	                }
	            }
	        }
	    }

	    return dividedGroups;}
}
