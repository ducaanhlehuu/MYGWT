package com.hello.client.activities.class_divide;


import java.util.ArrayList;
import java.util.List;

public class InMemoryStudentRepo implements StudentRepo {
    private List<Student> studentList;

    @Override
    public List<Student> getStudentList() {
        if (studentList == null || studentList.isEmpty()) {
            readData();
        }
        return studentList;
    }

    private void readData() {
        studentList = new ArrayList<>();
        String[] lines = StudentData.data.split("\r\n");
        
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            if (parts.length == 4) {
                String studentId = parts[0];
                int gender = Integer.parseInt(parts[1]);
                double grade = Double.parseDouble(parts[2]);
                String majorId = parts[3];
                Student student = new Student(studentId, gender, grade, majorId);
                studentList.add(student);
            }
        }
    }

    @Override
    public List<Student> getStudentsByMajorId(String majorId) {
        List<Student> studentsOfMajor = new ArrayList<>();
        for(Student student : getStudentList()) {
            if(student.getMajorId().equals(majorId)) {
                studentsOfMajor.add(student);
            }
        }
        System.out.println(majorId + " : " + studentsOfMajor.size() + " students");
        return studentsOfMajor;
    }


}