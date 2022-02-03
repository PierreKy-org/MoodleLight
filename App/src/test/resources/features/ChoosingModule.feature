Feature: Choosing module.

  Scenario: Choosing an empty module as a teacher.
    Given As a Teacher
    Given A Module with no Teacher registered
    When I choose this Module
    Then I a can register to the Module

  Scenario: Choosing a module that already has a teacher registered to it as a teacher.
    Given As a Teacher
    Given A Module with a Teacher registered to it
    When I choose this Module
    Then I can't register to it

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
