Feature: Module

  Scenario: "Phillipe" is removed from the module "Math"
    Given a Teacher with the login "Phillipe"
    And a Student with the login "Quentin"
    And a Module named "Math"
    When "Phillipe" is added to the Module "Math"
    And "Phillipe" is removed from the Module "Math"
    Then "Phillipe" is not registered to the Module "Math"
    And last request status is 401
