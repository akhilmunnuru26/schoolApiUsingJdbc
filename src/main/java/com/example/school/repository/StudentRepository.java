package com.example.school.repository;

import com.example.school.model.Student;
import java.util.*;

public interface StudentRepository{
    ArrayList<Student> getStudents();
    Student getStudentById(int studentId);
    Student addStudent(Student student);
    String getTotalStudents(ArrayList<Student> studentList);
    Student updateStudent(int studentId, Student student);
    void deleteStudent(int studentId);
}