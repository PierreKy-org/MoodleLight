Feature: Testing the Module Controller

  Scenario: Trying to register to a module when the user does not exists
    Given a Teacher with the login "testTeacher1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to register the user "abcdef12345" to the module "testModule1"
    Then last request status is 400
    And the response is "{\"message\":\"The user does not exists\"}"

  Scenario: Trying to register to a module when the module does not exists
    Given a Teacher with the login "testTeacher1"
    And a Student with the login "testStudent1"
    When The user "testTeacher1" try to register the user "testStudent1" to the module "abcdef12345"
    Then last request status is 400
    And the response is "{\"message\":\"The module does not exists\"}"

  Scenario: Trying to register to a module as a teacher when a teacher is already registered
    Given a Teacher with the login "testTeacher1"
    And a Teacher with the login "testTeacher2"
    And a Module named "testModule1"
    When The user "testTeacher1" try to register the user "testTeacher1" to the module "testModule1"
    And The user "testTeacher1" try to register the user "testTeacher2" to the module "testModule1"
    Then last request status is 400
    And the response is "{\"message\":\"There is already a teacher registered to the course\"}"

  Scenario: Trying to register to a module as a student
    Given a Student with the login "testStudent1"
    And a Module named "testModule1"
    When The user "testStudent1" try to register the user "testStudent1" to the module "testModule1"
    Then last request status is 403

  Scenario: Trying to register to a module as a user already registered
    Given a Teacher with the login "testTeacher1"
    And a Student with the login "testStudent1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to register the user "testStudent1" to the module "testModule1"
    And  The user "testTeacher1" try to register the user "testStudent1" to the module "testModule1"
    Then last request status is 400
    And the response is "{\"message\":\"The user is already registered\"}"

  Scenario: Successful registration
    Given a Teacher with the login "testTeacher1"
    And a Student with the login "testStudent1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to register the user "testStudent1" to the module "testModule1"
    Then last request status is 200
    And the response is "{\"message\":\"User successfully registered to module!\"}"