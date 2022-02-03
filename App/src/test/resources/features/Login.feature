Feature: Logging as a User

  Scenario : Logging to the authentication service with a valid token
    Given An authentication Service
    Given I am an User
    When I have a valid Token
    Then I can Connect to the Service

  Scenario : Logging to the authentication service without a valid token
    Given An authentication Service
    Given I am an User
    When I don't have a valid Token
    Then I can't Connect to the Service