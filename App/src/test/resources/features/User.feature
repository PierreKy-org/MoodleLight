Feature: Knowing the Modules i'm in

  Background :
    Given An User with the login "Mathieu" and the role "student"
    Given A Module named "Math"
    And A Module named "Computer Science"


  Scenario : As a User a can know the modules i'm in
    When Mathieu is in Module "Math"
    And Mathieu is in Module "Computer Science"
    Then Mathieu can knows that he is registered in Maths and Computer Science modules



