Feature: Register Teacher

  Background:
    Given a Teacher with login "steve"
    And a Teacher with login "sarah"
    And a Module named "Gestion de projet"

  Scenario: Register Teacher
    When "steve" is added to the Module "Gestion de projet"
    Then last request status is 200
    And "steve" is registered to module "Gestion de projet"

  Scenario: Register Second Teacher
    When "sarah" is added to the Module "Gestion de projet"
    And "steve" is added to the Module "Gestion de projet"
    Then last request status is 400
    And "sarah" is registered to module "Gestion de projet"
    And "steve" is not registered to module "Gestion de projet"

