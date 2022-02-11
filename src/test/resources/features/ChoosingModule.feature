#noinspection SpellCheckingInspection
Feature: Choosing module.

  Background :
    Given a teacher with login "Cinzia"
    And a teacher with login "Renevier"
    And An User with the login "Mathieu" and the role "student"
    And A Module named "Math"

  Scenario: Choosing an empty module as a teacher.
    Given A Module named "Math" with no Teacher registered
    When "Mathieu" registers to module "Math"
    Then the last request status is 200
    And "Mathieu" can register to "Math"
    When "Math" has 0 teacher registered
    And "Cinzia" registers to module "Math"
    Then the last request status is 200
    And "Cinzia" can register to "Math"

  Scenario: Choosing a module that already has a teacher registered to it as a teacher.
    When "Renevier" registers to module "Math"
    And "Cinzia" is registered to the Module "Math"
    Then  the last request status is 400
    And "Renevier" can't register to "Math"

  Scenario: Teacher add a a student to a module
    When "Cinzia" add Student "Mathieu" to "Math"
    Then the last request status is 200
    And "Mathieu" is added to the Module "Math"
    And "Math" is available for "Mathieu"