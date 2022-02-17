Feature: Managing resources

  Scenario: Verifying that a resource is restricted to certain roles
    Given a Student with the login "testStudent3"
    And a course named "testResource1"
    When "testStudent3" request the visibility of the resource "testResource1"
    Then the response is '^[{"id":[0-9]*, "name":ROLE_TEACHER}]$'

  Scenario: Adding a role to the resourceVisibility of a resource
    Given TODO

  Scenario: Removing a role to the resourceVisibility of a resource
    Given TODO

  Scenario: Trying to add a role if the role is already in the resourceVisibility of the resource
    Given TODO

  Scenario: Trying to remove a role if the role not in the resourceVisibility of the resource
    Given TODO

  Scenario: Changing the name of a resource
    Given TODO

  Scenario: Changing the description of a resource
    Given TODO

  Scenario: Adding the resource to a module
    Given TODO

  Scenario: Removing the resource from a module
    Given TODO

  Scenario: Trying to add to a module if the resource is already in a module
    Given TODO

  Scenario: Trying to remove from a module if the resource is not in the module
    Given TODO

  Scenario: Validating a questioner
    Given TODO