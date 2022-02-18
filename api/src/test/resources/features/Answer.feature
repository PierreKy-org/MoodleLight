Feature: Managing User Answers

  Scenario: Answering a question with a good response
    Given a Student with the login "testStudent1"
    And a open named "testQuestion1"
    When "testTeacher1" add the valid answer "':)'" to the question "testQuestion1"
    And "testStudent1" answer the question "testQuestion1" with "':)'"
    And the response is '^\{"message":"Good"\}$'

  Scenario: Answering a question with a wrong response
    Given a Student with the login "testStudent1"
    And a open named "testQuestion1"
    When "testTeacher1" add the valid answer ":)" to the question "testQuestion1"
    And "testStudent1" answer the question "testQuestion1" with ":?"
    And the response is '^\{"message":"Wrong"\}$'

  Scenario: Answering a runner question with a good response
    Given a Student with the login "testStudent1"
    And a runner named "testQuestion2"
    When "testTeacher1" add the valid answer "10000" to the question "testQuestion2"
    And "testTeacher1" add an input "100" to the question "testQuestion2"
    And "testStudent1" answer the question "testQuestion2" with "def square(value):\\n\\treturn value*value\\nprint(square(runner_input))"
    Then the response is '^\{"message":"Good"\}$'

  Scenario: Answering a runner question with a wrong response
    Given a Student with the login "testStudent1"
    And a runner named "testQuestion2"
    When "testTeacher1" add the valid answer "1000" to the question "testQuestion2"
    And "testTeacher1" add an input "100" to the question "testQuestion2"
    And "testStudent1" answer the question "testQuestion2" with "def square(value):\\n\\treturn value+value\\nprint(square(runner_input))"
    Then the response is '^\{"message":"Wrong"\}$'

  Scenario: Answering a runner question with syntactically wrong python code
    Given a Student with the login "testStudent1"
    And a runner named "testQuestion2"
    When "testTeacher1" add the valid answer "1000" to the question "testQuestion2"
    And "testTeacher1" add an input "100" to the question "testQuestion2"
    And "testStudent1" answer the question "testQuestion2" with "function square(value):\\n\\treturn value*value\\nprint(square(runner_input))"
    And the response is '^\{"message":"Wrong"\}$'