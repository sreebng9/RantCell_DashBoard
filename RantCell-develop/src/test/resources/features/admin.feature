@admin
Feature: Test the functionality of Admin.
  I want to verify the different funtionalities of Admin 
@TC001
  Scenario: Ensure admin can provision user with license pool per user via admin panel under user management, The user credentials will be shared to admin via E-Mail
    Given I am on RantCell pre-login page
    When I enter username "rantcelluk@gmail.com" and password "Test@123" into admin credentials
    And I click on login button
    Then I should be logged onto the RantCell Home page
    When I click on User Management menu located on left side
    Then User information page should be displayed
    When I click on Add user option
    And I enter all the details for required for registration and click on register
    Then user should be successfully registerd
    
    
    
  @TC004
  Scenario: Validate Admin can also login as normal user by clicking login 
    Given I am on RantCell pre-login page
    When I enter username "rantcelluk@gmail.com" and I enter password "Test@123"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    
    