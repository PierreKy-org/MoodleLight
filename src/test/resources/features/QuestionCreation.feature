Feature: Creating and Deleting a new Question

  Scenario: Successful open question creation
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to create the open "testOpenQuestion1"
    Then last request status is 200
    And the response is '^\{"message":"Open question successfully created!"\}$'

  Scenario: Successful mqc question creation
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to create the mqc "testMQCQuestion1"
    Then last request status is 200
    And the response is '^\{"message":"MQC question successfully created!"\}$'

  Scenario: Successful runner question creation
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to create the runner "testRunnerQuestion1"
    Then the response is '^\{"message":"Runner question successfully created!"\}$'
    And last request status is 200

  Scenario: Trying to create a runner question with inputs and outputs size not matching
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to create the runner "testRunnerQuestionSize" with output and input size not matching
    Then the response is '^\{"message":"The inputs and outputs size does not match"}$'

  Scenario: Successful open question delete
    Given a Teacher with the login "testTeacher1"
    And a open named "testOpenQuestion1"
    When The user "testTeacher1" try to delete the open "testOpenQuestion1"
    Then last request status is 200

  Scenario: Successful mqc question delete
    Given a Teacher with the login "testTeacher1"
    And a mqc named "testMqcQuestion1"
    When The user "testTeacher1" try to delete the mqc "testMqcQuestion1"
    Then last request status is 200

  Scenario: Successful runner question delete
    Given a Teacher with the login "testTeacher1"
    And a runner named "testRunnerQuestion1"
    When The user "testTeacher1" try to delete the runner "testRunnerQuestion1"
    Then last request status is 200

  Scenario: Trying to create a question when the name is already used by another
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to create the runner "testQuestion1"
    And The user "testTeacher1" try to create the open "testQuestion1"
    Then last request status is 400

  Scenario: Trying to delete a question when the question does not exists
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to delete the runner "testRunnerQuestionFalse"
    Then last request status is 404