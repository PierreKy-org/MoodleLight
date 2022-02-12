package fr.uca.springbootstrap.models;

import io.cucumber.java.ParameterType;

import java.util.Locale;

public enum ERole {
  ROLE_STUDENT,
  ROLE_TEACHER,
  ROLE_ADMIN;

  static public ERole convertStringToErol(String mystr){
    return switch (mystr.toLowerCase(Locale.ROOT)){
      case "teacher" -> ROLE_TEACHER;
      case "student" -> ROLE_STUDENT;
      case "admin" -> ROLE_ADMIN;
      default -> ROLE_STUDENT;
    };
  }

}

