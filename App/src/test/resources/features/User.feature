Feature: Knowing the Modules i'm in

  Scenario : As a User a can know the modules i'm in
    Given A User named "Matthieu"
    Given Multiples modules named "Maths", "Computer Science"
    When Matthieu is in modules Maths and Computer Science
    Then Matthieu can knows that he is registered in Maths and Computer Science modules
