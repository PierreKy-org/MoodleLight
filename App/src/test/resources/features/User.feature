Feature: Knowing the Modules i'm in

  Scenario : As a User a can know the modules i'm in
    Given A User
    Given Multiples modules
    When I am in theses modules
    Then I can know that i am registered in theses modules
