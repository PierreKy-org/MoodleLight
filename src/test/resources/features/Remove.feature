Feature: Removing user from a module

  Scenario: Trying to remove a user that doesn't exist from a module
    Given a Teacher with the login "testTeacher1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to remove the user "abcdef12345" to the module "testModule1"
    Then last request status is 400
    And the response is '^\{"message":"The user does not exists"\}$'

  Scenario: Trying to remove a user from a module that doesn't exists
    Given a Teacher with the login "testTeacher1"
    And a Student with the login "testStudent1"
    When The user "testTeacher1" try to remove the user "testStudent1" to the module "abcdef12345"
    Then last request status is 400
    And the response is '^\{"message":"The module does not exists"\}$'

  Scenario: Trying to remove a student to a module by a student
    Given a Student with the login "testStudent1"
    And a Module named "testModule1"
    When The user "testStudent1" try to remove the user "testStudent1" to the module "testModule1"
    Then last request status is 403

  Scenario: Trying to remove a student not in the module from it
    Given a Teacher with the login "testTeacher1"
    And a Student with the login "testStudent1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to remove the user "testStudent1" to the module "testModule1"
    Then last request status is 400
    And the response is '^\{"message":"The user is already not registered to the module"\}$'

  Scenario: Successful remove
    Given a Teacher with the login "testTeacher1"
    And a Student with the login "testStudent1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to register the user "testStudent1" to the module "testModule1"
    And The user "testTeacher1" try to remove the user "testStudent1" to the module "testModule1"
    Then last request status is 200
    And the response is '^\{"message":"User successfully removed from the module!"\}$'
    And The user "testStudent1" is not registered the module "testModule1"