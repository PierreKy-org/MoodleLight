package com.example.user;

import Models.Module;
import Models.Users.Student;
import Models.Users.Teacher;
import Models.Users.User;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModuleTest {

    Module module;
    Teacher teacher;
    Student student;

    @BeforeEach
    void setup(){
        module = new Module("Math");
        teacher = new Teacher(1,"test","test","testLogin","testPw");
        student = new Student(2,"test","test","testLogin","testPw");
    }

    @Test
    void addAUser() {
        assertEquals(0,module.getUsersList().size());
        teacher.addAUser(student,module);
        assertArrayEquals(new User[]{student},module.getUsersList().toArray());
    }
}