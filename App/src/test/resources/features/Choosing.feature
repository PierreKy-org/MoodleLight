#noinspection SpellCheckingInspection
Feature: Choosing module.

  Background :
    Given An User with the login "Cinzia" and the role "teacher"
    And An User with the login "Renevier" and the role "teacher"
    And An User with the login "Mathieu" and the role "ent"
    And A Module named "Math"

  Scenario: Choosing an empty module as a teacher.
    Given A Module named "Math" with no Teacher registered
    When I choose the module "Math"
    Then the last request status is 200
    And I can register to "Math"
    When "Cinzia" chooses the module "Math"
      And "Math" has 0 teacher registered
    Then the last request status is 200
      And "Cinzia" can register to "Math"

  Scenario: Choosing a module that already has a teacher registered to it as a teacher.
    When "Renevier" chooses the module "Math"
      And "Cinzia" is registered to the Module "Math"
    Then  the last request status is 400
      And "Renevier" can't register to "Math"

  Scenario: Teacher add a a student to a module
    When "Cinzia" add Student "Mathieu" to "Math"
    Then the last request status is 200
      And "Mathieu" is added to the Module "Math"
      And "Math" is available for "Mathieu"