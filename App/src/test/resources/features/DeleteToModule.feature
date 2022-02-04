Feature: Delete to Module
  Background:
    Given a Teacher named "Cinzia"
    Given a Module named "Math"
    Given a teacher named "Phillipe" registered on "Math"
    Given a Student with the id 123 is registered on "Math"

  Scenario: "Cinzia" delete "Phillipe" from the module
    When "Cinzia" delete "Phillipe" from the module
    Then "Phillipe" has no longer "Math" in his modules

  Scenario: "Phillipe" who doesn't have module
    When "Philippe" try to access to "Math"
    Then last request status is 400
    And "Math" is unavailable for "Phillipe"


  Scenario: "Cinzia" delete "Pierre" from "Math"
    When "Cinzia" delete the student with the id 123 from "Math"
    Then "Math" is unavailable for 123

  Scenario: "Cinzia" delete "Pierre" from "Math"
    Given "Math" is "Pierre"'s only module
    When "Cinzia" delete the student with the id 123 from "Math"
    Then "Math" is unavailable for 123
    And "Pierre" has no lounger Module
