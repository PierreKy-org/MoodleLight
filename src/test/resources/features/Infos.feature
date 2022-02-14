Feature: Getting information

  Scenario: Getting the userId of an user
    Given a Student with the login "testStudent1"
    When the user "testStudent1" request his id
    Then the response is '^\{"id":[0-9]*\}$'

  Scenario: Getting the userName from an userId
    Given a Student with the login "testStudent1"
    When the user "testStudent1" request his username
    Then the response is '^\{"username":testStudent1\}$'

  Scenario: Getting the email from an userId
    Given a Student with the login "testStudent1"
    When the user "testStudent1" request his email
    Then the response is '^\{"email":testStudent1@test.fr\}$'

  Scenario: Getting the roles from an userId
    Given a Student with the login "testStudent1"
    When the user "testStudent1" request his roles
    Then the response is '^\[\{"id":1, "name":ROLE_STUDENT\},\]|\[\]$'

  Scenario: Getting the modules from an userId
    Given a Student with the login "testStudent1"
    When the user "testStudent1" request his modules
    Then the response is '^\[\]$'

  Scenario: Getting the moduleId from a moduleName
    Given a Student with the login "testStudent1"
    And a Module named "testModule1"
    When "testStudent1" request the id of the module "testModule1"
    Then the response is '^\{"id":[0-9]*\}$'

  Scenario: Getting the moduleName from an moduleId
    Given a Student with the login "testStudent1"
    And a Module named "testModule1"
    When "testStudent1" request the name of the module "testModule1"
    Then the response is '^\{"name":testModule1\}$'

  Scenario: Getting the users from an moduleId
    Given a Student with the login "testStudent1"
    And a Module named "testModule1"
    When "testStudent1" request the users of the module "testModule1"
    Then the response is '^\[\]$'

  Scenario: Getting the resourceId from a resourceName
    Given a Student with the login "testStudent1"
    And a course named "testResource1"
    When "testStudent1" request the id of the resource "testResource1"
    Then the response is '^\{"id":[0-9]*\}$'
    Then last request status is 200

  Scenario: Getting the resourceName from an resourceId
    Given a Student with the login "testStudent1"
    And a course named "testResource1"
    When "testStudent1" request the name of the resource "testResource1"
    Then the response is '^\{"name":testResource1\}$'
    Then last request status is 200

  Scenario: Getting the resourceDescription from an resourceId
    Given a Student with the login "testStudent1"
    And a course named "testResource1"
    When "testStudent1" request the description of the resource "testResource1"
    Then the response is '^\{"description":test description\}$'
    Then last request status is 200

  Scenario: Getting the resourceVisibility from an resourceId
    Given a Student with the login "testStudent1"
    And a course named "testResource1"
    When "testStudent1" request the module of the resource "testResource1"
    Then the response is '^\{"module":testResource1\}|\{\}$'
    Then last request status is 200