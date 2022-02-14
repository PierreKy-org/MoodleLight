Feature: Creating and Deleting a new Module

  Scenario: Trying to create a new resource
    Given a Teacher with the login "testTeacher1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to create the course "testCourse1"
    And the response is '^\{"message":"A module with this name already exists"\}$'