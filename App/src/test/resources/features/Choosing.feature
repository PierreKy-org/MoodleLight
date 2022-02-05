Feature: Choosing module.

  Scenario: Choosing an empty module as a teacher.
    Given As a Teacher "Cinzia"
    Given A Module "Math" with no Teacher registered
    When I choose the module "Math"
    Then last request status is 200
    And I can register to "Math"

  Scenario: Choosing a module that already has a teacher registered to it as a teacher.
    Given A Module "Genie Logiciel" with "Cinzia" registered to it
    When I choose the module "Genie Logiciel"
    Then  last request status is 400
    And I can't register to it

  Scenario: Teacher add a a student to a module
    When "Cinzia" add "Pierre" to "Math"
    Then last request status is 200
    And "Pierre" is added to "Math"
    And "Math" is available for "Pierre"