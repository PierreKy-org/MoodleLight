Feature: Module

  Background:


  Scenario: "Phillipe" is removed from the module "Math"
    Given a Teacher with the login "Phillipe"
    And a Student with the login "Quentin"
    And a Module named "Math"
    When "Phillipe" is added to the Module "Math"
    And "Phillipe" is removed from the Module "Math"
    Then "Phillipe" is not registered to the Module "Math"

  Scenario: "Quentin" is added to the module "Math"
    Given a Student with the login "Quentin"
    And a Module named "Math"
    When "Quentin" is added to the Module "Math"
    Then "Quentin" is registered to the Module "Math"