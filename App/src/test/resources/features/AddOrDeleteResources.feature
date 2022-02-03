Feature: Add/Delete Resources to a Module
  Background:
    Given : a Teacher
    And : a Resource
    And : a Module
  Scenario: Add resource to a Module as a teacher
    When : a Teacher add the Resource to the Module
    Then : the Module contains the Resource

  Scenario: Add resource to a Module with no resource as a teacher
    When : a Teacher add the Resource to the Module
    Then : the Module contains the Resource
    And : the number of Resource of the module is 1

  Scenario : Delete a resource of a Module as a teacher
    When : a Teacher delete the Resource to the Module
    Then : the Module no longer contains the Resource

    Scenario: Delete a resource of a Module with 1 resource as a teacher
      When : a Teacher delete the Resource to the Module
      Then : the Module no longer contains the Resource
      And : the Module no longer contains Resource
