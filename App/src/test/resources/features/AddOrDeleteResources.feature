Feature: Add/Delete Resources to a Module

  Background:
    Given An User with the login "Cinzia" and the role "teacher"
    And A Resource named "MCQ"
    And A Module named "Math"

  Scenario Add MCQ to a Math as a Cinzia
    When "Cinzia" add Resource "MCQ" to the module "Math"
    Then The Module "Math" contains the resource "MCQ"

  Scenario: Add MCQ to a Math with no Resource as a Cinzia
    When "Cinzia" add Resource "MCQ" to the module "Math"
    Then The Module "Math" contains the resource "MCQ"
    And the number of Resource of "Math" is 1

  Scenario Delete MCQ from Math as Cinzia
    When "Cinzia" delete "MCQ" from the module "Math"
    Then The Module "Math" no longer contains the resource "MCQ"

  Scenario: Delete MCQ of a Module with 1 resource as Cinzia
    When "Cinzia" delete "MCQ" from the module "Math"
    Then The Module "Math" no longer contains the resource "MCQ"
    And The Module "Math" no longer contains any Resource
