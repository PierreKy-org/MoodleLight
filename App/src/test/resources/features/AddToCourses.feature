Feature: Add a un course

  Scenario : Adding a student to a course
    When «Cinzia» add «Pierre» at the course «Genie Logiciel»
    Then There is 1 in his number of course
    And There is «Genre Logiciel» in his list of courses

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