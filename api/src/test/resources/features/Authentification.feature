Feature: authentification

  Scenario: signUp as a new User
    Given "testTeacher" try to SignUp with the email "testTeacher@test.fr" and the password "abracadabra"
    Then the response is '^\{"message":"User registered successfully!"\}$'


  Scenario: SignUp when the userName already exists
    Given "testTeacher" try to SignUp with the email "testTeacherA@test.fr" and the password "abracadabra"
    Given "testTeacher" try to SignUp with the email "testTeacher@test.fr" and the password "abracadabra"
    Then the response is '^\{"message":"Error: Username is already taken!"\}$'

  Scenario: SignUp when the email already exists
    Given "testTeacherA" try to SignUp with the email "testTeacherA@test.fr" and the password "abracadabra"
    Given "testTeacherB" try to SignUp with the email "testTeacherA@test.fr" and the password "abracadabra"
    Then the response is '^\{"message":"Error: Email is already in use!"\}$'

  Scenario: SignIn as a valid User
    Given "testTeacher" try to SignIn with the password "abracadabra"
    Then last request status is 200


  Scenario: SignIn with a wrong login
    Given "testTeacherFalse" try to SignIn with the password "abracadabra"
    Then last request status is 401

  Scenario: SignIn with a wrong password
    Given "testTeacher" try to SignIn with the password "abracadabr"
    Then last request status is 401

  Scenario: Request with a good authentication token
    Given a Teacher with the login "testTeacher3"
    When the user "testStudent1" request his id
    Then last request status is 200

  Scenario: Request with a false authentication token
    Given a Teacher with the login "testTeacher3"
    When the user "testStudent1" request his id with the token "abc"
    Then last request status is 401

  Scenario: Request without the permission of path
    Given a Student with the login "testStudent3"
    Given a Module named "Sport"
    When "testStudent3" renamed the module "Sport" in "AI"
    Then last request status is 403

  Scenario: Request without the permission of visibility of module
    Given TODO