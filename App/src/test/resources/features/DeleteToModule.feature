Feature: Delete to Module

  Scenario: Teacher delete a teacher
    Given a teacher "Cinzia" and a teacher "Phillipe"
    Given a Module with "Phillipe" registered to it
    When "Cinzia" delete "Phillipe" to the Module
    Then "Phillipe" is delete on the Module
    And the Module is unavailable for "Phillipe"

  Scenario: Teacher delete a student
    Given a teacher and a student
    Given a Module with the student registered to it
    When the teacher deletethe student to the Module
    Then the student is delete on the Module
    And the Module is unavailable for the student