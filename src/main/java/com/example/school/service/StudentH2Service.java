package com.example.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import javax.validation.OverridesAttribute;

import com.example.school.repository.StudentRepository;
import com.example.school.model.*;




@Service
public class StudentH2Service implements StudentRepository{
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Student> getStudents(){
        List<Student> studentsList = db.query("SELECT * FROM STUDENT", new StudentRowMapper());
        ArrayList<Student> students = new ArrayList<>(studentsList);
        return students;

    }

    @Override
    public Student getStudentById(int studentId){
        try{
            Student student = db.queryForObject("SELECT * FROM STUDENT WHERE studentId=?",new StudentRowMapper(),studentId);
            return student;
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(Student student){
        db.update("INSERT INTO STUDENT VALUES (?,?,?)", student.getStudentName(),student.getGender(),student.getStandard());
        Student savedStudent = db.queryForObject("SELECT * FROM STUDENT WHERE studentName=? and gender=? and standard=?",new StudentRowMapper(),student.getStudentName(),student.getGender(),student.getStandard());
        return savedStudent;
    }

    @Override
    public Student updateStudent(int studentId,Student student){
        if (student.getStudentName() != null){
            db.update("UPDATE STUDENT SET studentName=? WHERE studentId=?",student.getStudentName(),studentId);
        }
        if(student.getGender() != null){
            db.update("UPDATE STUDENT SET gender=? WHERE studentId=?",student.getGender(),studentId);
        }
        if(student.getStandard() != -1){
            db.update("UPDATE STUDENT SET standard=? WHERE studentId=?",student.getStandard(),studentId);
        }

        return getStudentById(studentId);
    }

    @Override
    public void deleteStudent(int studentId){
        db.update("DELETE FROM STUDENT WHERE studentId=?",studentId);
    }

    @Override
    public String getTotalStudents(ArrayList<Student> studentList){
        int count = 0;
        for (Student stud: studentList){
          db.update("INSERT INTO STUDENT VALUES(?,?,?)", stud.getStudentName(),stud.getGender(),stud.getStandard());  
            count+=1;
        }
        String msg = String.format("Successfully added %d students",count);
        return msg;
    };
}

