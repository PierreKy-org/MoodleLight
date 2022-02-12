Feature: Authentication

  Scenario : SignUp as a new User
    Then last request status is 0

  Scenario : SignUp when the userName already exists
    Then last request status is 0

  Scenario : SignUp when the email already exists
    Then last request status is 0

  Scenario : SignIn as a valid User
    Then last request status is 0

  Scenario : SignIn with a wrong login
    Then last request status is 0

  Scenario : SignIn with a wrong password
    Then last request status is 0

  Scenario : Request with a good authentication token
    Then last request status is 0

  Scenario : Request with a false authentication token
    Then last request status is 0

  Scenario : Request without the permission
    Then last request status is 0