Feature: Managing Questions

  Scenario: Adding a correct answer to a question
    Given a Teacher with the login "testTeacher1"
    And a open named "testQuestion1"
    When "testTeacher1" add the valid answer ":)" to the question "testQuestion1"
    And the response is '^\{"message":"The answer has been added to the question!"\}$'

  Scenario: Removing a correct answer from a question
    Given TODO

  Scenario: Removing a correct answer from a question
    Given TODO