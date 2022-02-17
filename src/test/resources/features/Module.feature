Feature: Managing a module

  Scenario: Renaming a module
    Given a Teacher with the login "testTeacher3"
    Given a Module named "Sport"
    When "testTeacher3" renamed the module "Sport" in "AI"
    Then the response is '^\{"message":"Module successfully rename!"\}$'

  Scenario: Adding a resource to a module
    Given a Teacher with the login "testTeacher1"
    Given a Module named "Sport"
    Given a course named "saut"
    When "testTeacher1" add the course "saut" of the Module "Sport"
    Then the response is '^\{"message":"Resource successfully added"\}$'

  Scenario: Removing a resource from a module
    Given a Teacher with the login "testTeacher1"
    Given a Module named "Sport"
    Given a course named "saut"
    When "testTeacher1" delete the course "saut" of the Module "Sport"
    Then the response is '^\{"message":"Resource successfully remove"\}$'