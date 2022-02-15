Feature: Modification for the User

  Scenario: The user change his userName
    Given a Student with the login "testStudent3"
    When "testStudent3" change his "userName" to "testStudentBis"
    Then the response is '^\{"name":"testStudentBis"\}$'
    And "testStudent3" new username is "testStudentBis"

  Scenario: The user change his email
    Given a Student with the login "testStudent3"
    When "testStudent3" change his "email" to "testStudent3Bis@test.fr"
    Then the response is '^\{"email":"testStudent3Bis@test.fr"\}$'
    And "testStudent3" new email is "testStudent3Bis@test.fr"

  Scenario: The user change his password
    Given a Student with the login "testStudent3"
    When "testStudent3" change his "password" to "azertyuiop"
    Then the response is '^\{"password":"azertyuiop"\}$'
    And "testStudent3" new password is "azertyuiop"

  Scenario: A User try to change the userName of another User
    Given a Student with the login "testStudent3"
    And a Student with the login "testStudent4"
    When "testStudent3" try to change the "password" of "testStudent4" to "azertyuiop"
    Then the response is '^\{"message":"Error : Permission denied"\}$'

  Scenario: A User try to change the email of another User
    Given a Student with the login "testStudent3"
    And a Student with the login "testStudent4"
    When "testStudent3" try to change the "userId" of "testStudent4" to "azertyuiop"
    Then the response is '^\{"message":"Error : Permission denied"\}$'

  Scenario: A User try to change the password of another User
    Given a Student with the login "testStudent3"
    And a Student with the login "testStudent4"
    When "testStudent3" try to change the "email" of "testStudent4" to "azertyuiop"
    Then the response is '^\{"message":"Error : Permission denied"\}$'