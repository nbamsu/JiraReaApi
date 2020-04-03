@Regression
Feature: Test Jira REST APIs end-2-end

  Scenario: Create a new board
    When user "creates" a new "board"
    Then the user status code should be 200
    And  "board" is created

  Scenario Outline: Create Sprints
    When user "creates" a new "Sprint" with  "<name>" "<startDate>" "<endDate>""<originBoardId>""<goal>"
    Then the user status code should be 200
    And  "Sprint" is created

    Examples:
      | name                       | startDate                     | endDate                       | originBoardId | goal                          |
      | First Sprint of FullStack  | 2020-04-11T10:00:00.000+10:00 | 2020-04-25T15:10:00.000+10:00 | 2             | To be Able to Create a Sprint |
      | Second Sprint of FullStack | 2020-04-11T10:00:00.000+10:00 | 2020-04-25T15:10:00.000+10:00 | 2             | To be Able to Create a Sprint |
      | Third Sprint of FullStack  | 2020-04-11T10:00:00.000+10:00 | 2020-04-25T15:10:00.000+10:00 | 2             | To be Able to Create a Sprint |

  Scenario: validate numbers of created Sprints
    When user send Get request to endpoint
    Then status code 201
    And number of sprints 3


  Scenario: Create issues for First Sprint
    When user creates "issue" with "summary" "description" "issuetype" "priority"
    Then status code should be 201
    And "issue" created under "first sprint"

  Scenario: Create issues for Second Sprint
    When user creates "issue" with "summary" "description" "issuetype" "priority"
    Then status code should be 201
    And "issue" created under "second sprint"

  Scenario: Create issues for Third Sprint
    When user creates "issue" with "summary" "description" "issuetype" "priority"
    Then status code should be 201
    And "issue" created under "third sprint"

  Scenario: Create issues for backlog
    When user creates "issue" with "summary" "description" "issuetype" "priority"
    Then status code should be 201
    And "issue" created under "third sprint"

    Scenario: Validate number of Stories



   Scenario: Validate number of BUgs


