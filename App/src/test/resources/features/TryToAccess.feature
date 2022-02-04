Feature: Try to Access to a Module

  Background:
    Given a Teacher named "Cinzia" with no Module
    Given a Module named "Math"
    Given a student named "Pierre" with no Module

  Scenario: A teacher with no module try to acces to "Math"
    When "Cinzia" try to access to "Math"
    Then the last request status is 400
    And "Cinzia" is not allowed to acces to "Math"

  Scenario: A student with no module try to acces to "Math"
    When "Pierre" try to access to "Math"
    Then the last request status is 400
    And "Pierre" is not allowed to acces to "Math"