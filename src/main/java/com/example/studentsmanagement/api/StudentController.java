package com.example.studentsmanagement.api;

import com.example.studentsmanagement.exceptions.InvalidUniversityClassException;
import com.example.studentsmanagement.exceptions.StudentEmptyNameException;
import com.example.studentsmanagement.exceptions.StudentNotExistException;
import com.example.studentsmanagement.model.Student;
import com.example.studentsmanagement.service.StudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {
     private StudentService studentService;

     @Autowired
     public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @RequiresPermissions("student:read")
    public List<Student> getAllStudents() {
         return studentService.getAllStudents();
    }

    @GetMapping("/name")
    public List<Student> getStudents(@RequestParam String name) {
        return studentService.getStudentByName(name);
    }

    @GetMapping("/contain_name")
    public List<Student> getStudentContainName(@RequestParam String name) {
         return studentService.getStudentsContainName(name);
    }
    @GetMapping("/class")
    public List<Student> getStudentInClass(@RequestParam int year, @RequestParam int number) {
         return studentService.getStudentsInClass(year, number);
    }
    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
         try {
             Student savedStudent = studentService.addStudent(student);
             return ResponseEntity.ok("Registed student" + student.toString());
         }
         catch (StudentEmptyNameException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

         }
    }

    @PostMapping(path ="assignclass/{sid}/{cid}")
    public ResponseEntity<String> assignClass(@PathVariable("sid") Long studentId, @PathVariable("cid" ) Long classId){
         try {
             Student updateStudent = studentService.assignClass(studentId, classId);
             return ResponseEntity.ok("Assigned the class" + updateStudent.toString());
         }
         catch (StudentNotExistException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
         }
         catch (InvalidUniversityClassException e) {
             return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
         }
     }
}
