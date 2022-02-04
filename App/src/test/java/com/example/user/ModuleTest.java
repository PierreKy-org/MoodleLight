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
        teacher = new Teacher(1L,"test","test","testLogin","testPw");
        student = new Student(2L,"test","test","testLogin","testPw");
    }

    @Test
    void addAUser() {
        assertEquals(0,module.getUsersList().size());
        teacher.addAUser(student,module);
        assertEquals(module, student.getModules().get(0));
        assertArrayEquals(new User[]{student},module.getUsersList().toArray());
    }

    @Test
    void removeUser(){
        teacher.addAUser(student,module);
        assertArrayEquals(new User[]{student},module.getUsersList().toArray());
        assertEquals(module, student.getModules().get(0));
        teacher.removeUserFromModule(student,module);
        assertEquals(0, student.getModules().size());
        assertEquals(0,module.getUsersList().size());
    }
}