Feature: Add/Delete Resources to a Module
  Background:
    Given a Teacher named "Cinzia"
    And a Resource named "MCQ"
    And a Module named "Math"

  Scenario Add MCQ to a Math as a Cinzia
    When "Cinzia" add the "MCQ" to the "Math"
    Then "Math" contains "MCQ"

  Scenario: Add MCQ to a Math with no Resource as a Cinzia
    When "Cinzia" add "MCQ" to "Math"
    Then "Math" contains "MCQ"
    And the number of Resource of "Math" is 1

  Scenario Delete MCQ from Math as Cinzia
    When "Cinzia" delete "MCQ" from "Math"
    Then "Math" no longer contains "MCQ"

    Scenario: Delete MCQ of a Module with 1 resource as Cinzia
      When "Cinzia" delete "MCQ" to "Math"
      Then "Math" no longer contains "MCQ"
      And "Math" no longer contains Resource
