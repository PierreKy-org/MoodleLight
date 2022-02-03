Feature: Logging as a User
  Background :
    Given Authentication Service
    And User

  Scenario : Logging to the authentication service with a valid token
    When User has a valid Token
    Then Authentication Service compare token
    And He can connect to the Service

  Scenario : Logging to the authentication service without a valid token
    When User doesn't have a valid Token
    Then Authentication Service compare token
    And User can't Connect to the Service
    And Show error 401 to User
    And Authentication Service asks User to gives his password