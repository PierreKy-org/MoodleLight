#noinspection SpellCheckingInspection
Feature: Choosing module.


  Scenario: Teacher add a a student to a module
    Given a Teacher with the login "Cinzia"
    And a Student with the login "Mathieu"
    And A Module named "Math"
    When "Cinzia" add Student "Mathieu" to "Math"
    And "Mathieu" is added to the Module "Math"
    Then "Math" is available for "Mathieu"

  Scenario: Choosing an empty module as a teacher.
    Given A Module named "Genie-Logiciel" with no Teacher registered
    And a Teacher with the login "Cinzia"
    And a Student with the login "Mathieu"
    When the student "Mathieu" choose the module "Genie-Logiciel"
    And "Genie-Logiciel" has 0 teacher registered
    And "Cinzia" chooses the module "Genie-Logiciel"
    Then "Genie-Logiciel" is available for "Cinzia"

  Scenario: Choosing a module that already has a teacher registered to it as a teacher.
    Given a Teacher with the login "Renevier"
    And a Teacher with the login "Sid"
    And A Module named "Francais"
    When "Sid" chooses the module "Francais"
    When "Renevier" chooses the module "Francais"
    And "Francais" is available for "Sid"
    And "Renevier" can't register to "Francais"