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

  Scenario: Teacher delete a student from a module
    Given a teacher and a student
    Given a Module with the student registered to it
    When the teacher deletethe student to the Module
    Then the student is delete on the Module
    And the Module is unavailable for the student

  Scenario: Teacher add a a student to a module
    Given a Teacher, a student and module
    When the teacher add the student to the module
    Then The student is added to this module
    And the module is available for the student