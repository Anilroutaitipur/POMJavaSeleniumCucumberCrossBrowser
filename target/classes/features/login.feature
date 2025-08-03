@Tagrun1
Feature: To check the login functionality working fine

  Scenario: Checking login functionality should work with single user

    Given User navigate to login page
    When User enter username and password
    And User click on login button
    Then User should navigate to Home page


  Scenario Outline: Checking login functionality should work with multiple users

    Given User navigate to login page
    When User enter the "<username>" and "<password>"
    And User click on login button
    Then User should navigate to Home page

    Examples:
      | username                | password     |
      | standard_user           | secret_sauce |
      | problem_user            | secret_sauce |
      | performance_glitch_user | secret_sauce |
      | error_user              | secret_sauce |
      | visual_user             | secret_sauce |


  Scenario Outline: Checking login functionality should not work with this user

    Given User navigate to login page
    When User enter the "<username>" and "<password>"
    And User click on login button
    Then User login will fail takes screenshot

    Examples:
      | username                | password     |
      | locked_out_user         | secret_sauce |



