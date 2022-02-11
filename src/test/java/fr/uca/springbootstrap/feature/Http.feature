Feature: Testing our Rest API

  Scenario: Successful GET request
    Given the user "Quentin" with the password "abc"
    Then a get request is made to "api/test/user"
    Then last request status is 200
    And the response is "User Content."

  Scenario: Unsuccessful GET request
    Given the user "Quentin" with the password "wrong"
    Then a get request is made to "api/test/user"
    Then last request status is 401

  Scenario: Successful POST request
    Given the user "Quentin" with the password "abc"
    Then a post request is made to "api/auth/signup" with payload "{\"username\":\"admin\",\"password\":\"abcdef1234\",\"email\":\"admin@gmail.com\"}"
    Then last request status is 400
    And the response is "{\"message\":\"Error: Username is already taken!\"}"

  Scenario: Unsuccessful POST request
    Given the user "Quentin" with the password "abc"
    Then a post request is made to "api/test/plane"
    Then last request status is 404

  Scenario: Successful PUT request
    Given the user "Quentin" with the password "abc"
    Then a put request is made to "api/test/put"
    Then last request status is 200
    And the response is "Put Successful"

  Scenario: Successful DELETE request
    Given the user "Quentin" with the password "abc"
    Then a delete request is made to "api/test/delete"
    Then last request status is 200
    And the response is "Delete Successful"