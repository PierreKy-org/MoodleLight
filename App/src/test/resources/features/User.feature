Feature: Knowing the Modules i'm in

  Background :
    Given An User "Mathieu"
    Given A Module "Math"
    And A Module "Computer Science"


  Scenario : As a User a can know the modules i'm in
    When Mathieu is in Module Maths
    And Mathieu is in Module Computer Science
    Then Mathieu can knows that he is registered in Maths and Computer Science modules



