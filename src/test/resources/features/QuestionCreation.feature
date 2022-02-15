Feature: Creating and Deleting a new Question

  Scenario: Successful open question creation
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to create the question "testOpenQuestion1"
    Then last request status is 200
    And the response is '^\{"message":"Open question successfully created!"\}$'

  Scenario: Successful mqc question creation
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to create the mqc "testMQCQuestion1"
    Then last request status is 200
    And the response is '^\{"message":"MQC question successfully created!"\}$'

  Scenario: Successful code runner question creation
    Given TODO

  Scenario: Successful open question delete
    Given TODO

  Scenario: Successful mqc question delete
    Given TODO

  Scenario: Successful code runner question delete
    Given TODO

  Scenario: Trying to create a question when the name is already used by another
    Given TODO

  Scenario: Trying to delete a question when the question does not exists
    Given TODO