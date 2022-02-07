#noinspection SpellCheckingInspection
Feature: Choosing module.

  Background :
    Given A Teacher "Cinzia"
    And A Teacher "Renevier"
    Given A Student named "Mathieu"
    Given A Module "Math"

  Scenario: Choosing an empty module as a teacher.
    When "Cinzia" chooses the module "Math"
      And "Math" has 0 teacher registered
    Then last request status is 200
      And "Cinzia" can register to "Math"

  Scenario: Choosing a module that already has a teacher registered to it as a teacher.
    When "Renevier" chooses the module "Math"
      And "Cinzia" is registered to the Module "Math"
    Then  last request status is 400
      And "Renevier" can't register to "Math"

  Scenario: Teacher add a a student to a module
    When "Cinzia" add "Mathieu" to "Math"
    Then last request status is 200
      And "Mathieu" is added to the Module "Math"
      And "Math" is available for "Mathieu"