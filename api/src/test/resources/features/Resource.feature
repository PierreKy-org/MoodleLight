Feature: Managing resources

  Scenario: Verifying that a resource is restricted to certain roles
    Given a Student with the login "testStudent3"
    And a course named "testResource1"
    When "testStudent3" request the description of the resource "testResource1"
    Then last request status is 404

  Scenario: Adding a role to the resourceVisibility of a resource
    Given a Teacher with the login "testTeacher3"
    And a course named "testResource1"
    When "testTeacher3" add "student" to the resource "testResource1"
    Then last request status is 200

  Scenario: Removing a role to the resourceVisibility of a resource
    Given a Teacher with the login "testTeacher3"
    And a course named "testResource1"
    When "testTeacher3" add "student" to the resource "testResource1"
    And "testTeacher3" remove "student" to the resource "testResource1"
    Then last request status is 200

  Scenario: Trying to add a role if the role is already in the resourceVisibility of the resource
    Given a Teacher with the login "testTeacher3"
    And a course named "testResource1"
    When "testTeacher3" add "student" to the resource "testResource1"
    And "testTeacher3" add "student" to the resource "testResource1"
    Then the response is '\{"message":"this visibility was already here"\}'

  Scenario: Trying to remove a role if the role not in the resourceVisibility of the resource
    Given a Teacher with the login "testTeacher3"
    And a course named "testResource1"
    When "testTeacher3" remove "student" to the resource "testResource1"
    And "testTeacher3" remove "student" to the resource "testResource1"
    Then the response is '\{"message":"visibility not in this resource"\}'
