Feature: Delete to Module

  Background:
    Given a teacher named "Cinzia"
    And a Module named "Math"
    And a teacher named "Phillipe" registered on "Math"
    And a Student named "Pierre" registered on "Math"

  Scenario: "Cinzia" delete "Phillipe" from "Math"
    When "Cinzia" delete "Phillipe" from "Math"
    Then "Phillipe" is delete from "Math"

  Scenario: "Phillipe" who doesn't have module try to access to "Math"
    When "Philippe" try to access to "Math"
    And "Math" is unavailable for "Phillipe"

  Scenario: "Cinzia" delete "Pierre" from "Math"
    When "Cinzia" delete "Pierre" from "Math"
    Then "Pierre" is delete from "Math"
    And "Math" is unavailable for "Pierre"