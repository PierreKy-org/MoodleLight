#noinspection SpellCheckingInspection
Feature: Choosing module.


  Scenario: Teacher add a a student to a module
    Given a Teacher with the login "Sid"
    And a Student with the login "Mathieu"
    And a Module named "Math"
    When The user "Sid" try to register the user "Mathieu" to the module "Math"
    And "Mathieu" is added to the Module "Math"
    Then "Mathieu" is registered to the Module "Math"
    And last request status is 200

  Scenario: Choosing an empty module as a teacher.
    Given A Module named "Genie-Logiciel" with no Teacher registered
    And a Teacher with the login "Cinzia"
    And a Student with the login "Mathieu"
    When "Mathieu" chooses the module "Genie-Logiciel"
    And "Genie-Logiciel" has 0 teacher registered
    And "Cinzia" chooses the module "Genie-Logiciel"
    Then "Cinzia" is registered to the Module "Genie-Logiciel"
    And last request status is 200
