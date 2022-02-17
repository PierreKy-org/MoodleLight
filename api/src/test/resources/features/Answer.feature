Feature: Managing User Answers

  Scenario: Answering a question with a good response
    Given a Student with the login "testStudent1"
    And a open named "testQuestion1"
    When "testStudent1" answer the question "testQuestion1" with ":)"
    And the response is '^\{"message":"Good"\}$'

  Scenario: Answering a question with a wrong response
    Given a Student with the login "testStudent1"
    And a open named "testQuestion1"
    When "testStudent1" answer the question "testQuestion1" with ":?"
    And the response is '^\{"message":"Wrong"\}$'

  Scenario: Answering a runner question with a good response
    Given a Student with the login "testStudent1"
    And a runner named "testQuestion2"
    When "testStudent1" answer the question "testQuestion2" with "def square(value):\\n\\treturn value*value\\nprint(square(runner_input))"
    And the response is '^\{"message":"Good"\}$'

  Scenario: Answering a runner question with a wrong response
    Given a Student with the login "testStudent1"
    And a runner named "testQuestion2"
    When "testStudent1" answer the question "testQuestion2" with "def square(value):\\n\\treturn value+value\\nprint(square(runner_input))"
    And the response is '^\{"message":"Wrong"\}$'

  Scenario: Answering a runner question with syntactically wrong python code
    Given a Student with the login "testStudent1"
    And a runner named "testQuestion2"
    When "testStudent1" answer the question "testQuestion2" with "function square(value):\\n\\treturn value*value\\nprint(square(runner_input))"
    And the response is '^\{"message":"Wrong"\}$'