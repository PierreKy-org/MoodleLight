Feature : Register Teacher

  Background:
    Given A User with the login "steve" and the role "teacher"
    And A User with the login "sarah" and the role "teacher"
    And A Module named "Gestion de projet"

  Scenario: Register Teacher
    When "steve" registers to the Module "Gestion de projet"
    Then the last request status is 200
    And "steve" is registered to the Module "Gestion de projet"

  Scenario: Register Second Teacher
    When "sarah" registers to the Module "Gestion de projet"
    And "steve" registers to the Module "Gestion de projet"
    Then the last request status is 400
    And "sarah" is registered to the Module "Gestion de projet"
    And "steve" is not registered to the Module "Gestion de projet"