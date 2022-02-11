Feature: Testing the User Controller

  Background :
    Given the user "Quentin" with the password "abc"

  Scenario: GET request to api/user/1/username
    Given the user "Quentin" with the password "abc"
    Then a get request is made to "api/user/1/username"
    Then last request status is 200
    And the response is "{\"username\":Quentin}"

  Scenario: GET request to api/user/1/email
    Given the user "Quentin" with the password "abc"
    Then a get request is made to "api/user/1/email"
    Then last request status is 200
    And the response is "{\"email\":quentin.beauchet@unice.fr}"

  Scenario: GET request to api/user/1/roles
    Given the user "Quentin" with the password "abc"
    Then a get request is made to "api/user/1/roles"
    Then last request status is 200

  Scenario: GET request to api/user/1/modules
    Given the user "Quentin" with the password "abc"
    Then a get request is made to "api/user/1/modules"
    Then last request status is 200