Feature: Module

  Background:
    Given An User with the login "Phillipe" and the role "Teacher"
    And An User with the login "Quentin" and the role "Student"
    And A Module named "Math"

  Scenario: "Phillipe" is removed from the module "Math"
    When "Phillipe" is added to the Module "Math"
    And "Phillipe" is removed from the Module "Math"
    Then "Phillipe" is not registered to the Module "Math"

  Scenario: "Quentin" is added to the module "Math"
    When "Quentin" is added to the Module "Math"
    Then "Quentin" is registered to the Module "Math"