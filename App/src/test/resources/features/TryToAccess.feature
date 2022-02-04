Feature: Try to Access to a Module

  Background:
    Given a User with the login "Cinzia" and the role "Teacher" with no Module
    Given a Module named "Math"
    Given a User with the in "Pierre" and the role "Student" with no Module

  Scenario: A teacher with no module try to access to "Math"
    When "Cinzia" try to access to "Math"
    Then the last request status is 400
    And "Cinzia" is not allowed to access to "Math"

  Scenario: A student with no module try to access to "Math"
    When "Pierre" try to access to "Math"
    Then the last request status is 400
    And "Pierre" is not allowed to access to "Math"

  Scenario: "Cinzia" want to knows her number of module
    When "Cinzia" want to know her number of the modules her follows
    Then "Cinzia" has 0 modules

  Scenario: "Pierre" want to knows his number of module
    When "Pierre" want to know his number of the modules his follows
    Then "Pierre" has 0 modules

  Scenario: "Pierre" want to add a Module with a wrong name "wrongName"
    When "Pierre" try to add the Module with the wrong name "wrongName"
    Then the last request status is 400
    And "Pierre" doesn't have access to the Module "wrongName"
