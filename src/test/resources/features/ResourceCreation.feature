Feature: Creating and Deleting a new Resource

  Scenario: Successful course creation
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to delete the course "testCourse1"
    And The user "testTeacher1" try to create the course "testCourse1"
    And last request status is 200
    And the response is '^\{"message":"Course successfully created!"\}$'

  Scenario: Successful questioner creation
    Given a Teacher with the login "testTeacher1"
    When The user "testTeacher1" try to delete the questioner "testQuestioner1"
    When The user "testTeacher1" try to create the questioner "testQuestioner1"
    And last request status is 200
    And the response is '^\{"message":"Questioner successfully created!"\}$'

  Scenario: Successful course deletion
    Given a Teacher with the login "testTeacher1"
    And a course named "testCourse1"
    When The user "testTeacher1" try to delete the course "testCourse1"
    And last request status is 200
    And the response is '^\{"message":"Course successfully deleted!"\}$'

  Scenario: Successful questioner deletion
    Given a Teacher with the login "testTeacher1"
    And a questioner named "testQuestioner1"
    When The user "testTeacher1" try to delete the questioner "testQuestioner1"
    And last request status is 200
    And the response is '^\{"message":"Questioner successfully deleted!"\}$'

  Scenario: Trying to create a resource when the name is already used by another
    Given a Teacher with the login "testTeacher1"
    And a course named "testResource1"
    And The user "testTeacher1" try to create the questioner "testResource1"
    And last request status is 400
    And the response is '^\{"message":"A resource with this name already exists"\}$'