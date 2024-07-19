package com.hello.client.activities.class_divide;



import java.util.List;

public interface StudentRepo {
    List<Student> getStudentList();
    List<Student> getStudentsByMajorId(String majorId);
}