Feature: Creating and Deleting a new Module

  Scenario: Trying to create a new module if another one with the same name already exists
    Given a Teacher with the login "testTeacher1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to create the module "testModule1"
    And the response is '^\{"message":"A module with this name already exists"\}$'

  Scenario: Successful module creation
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to delete the module "testModule1"
    And The user "testTeacher1" try to create the module "testModule1"
    Then last request status is 200
    And the response is '^\{"message":"Module successfully created!"\}$'

  Scenario: Trying to delete a module not existing
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to delete the module "testModule1"
    And The user "testTeacher1" try to delete the module "testModule1"
    Then last request status is 400
    And the response is '^\{"message":"The module does not exists"\}$'


  Scenario: Successful module deletion
    Given a Teacher with the login "testTeacher1"
    And a Module named "testModule1"
    When The user "testTeacher1" try to delete the module "testModule1"
    Then last request status is 200
    And the response is '^\{"message":"Module successfully deleted!"\}$'