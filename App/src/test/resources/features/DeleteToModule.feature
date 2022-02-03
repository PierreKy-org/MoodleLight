Feature: Delete to Module

  Background:
    Given a Teacher named "Cinzia"
    And a Module named "Math"
    And a teacher named "Phillipe" registered on "Math"
    And a Student with the id 123 is registered on "Math"

  Scenario: "Cinzia" delete "Phillipe" from the module
    Given "Cinzia"
    When "Cinzia" delete "Phillipe" from the module
    Then "Phillipe" has no longer "Math" in his modules

  """Scenario: "Phillipe" who doesn't have module
    When "Philippe" try to access to "Math"
    Then "Math" is unavailable for "Phillipe"*/"""

  Scenario: "Cinzia" delete "Pierre" from "Math"
    When "Cinzia" delete the student with the id 123 from "Math"
    Then "Math" is unavailable for 123