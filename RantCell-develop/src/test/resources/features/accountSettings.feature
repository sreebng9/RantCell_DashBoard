@accountSettings
Feature: Test the user account settings functionality.
	@TC063 @TC064
  Scenario: Testing the login functionality with valid users
    Given I am on RantCell pre-login page
    When I enter username "testu321@gmail.com" and I enter password "Testuser@123"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    When I click on account settings then account settings page shuld be displayed
    When I click on change password then cahnge password page should be displayed
    When I input current passord "Testuser@123" and new password "@rantcell" and click on update passord
    Then new password should be updated
    When I try to login to Rantcell with updated credentials, username "testu321@gmail.com" and password "Testuser@123"
    Then I should be logged in successfully
    
    @TC065
    Scenario: User able to update personal details under account settings.
    Given I am on RantCell pre-login page
    When I enter username "testu321@gmail.com" and I enter password "@rantcell"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    When I click on account settings then account settings page shuld be displayed
    When I click on address update then change address page should be displayed
    When I input Address, pincode, country that needs to be updated and click on update address button
    Then address should be updated in the customer details
    
    When I click on phone number update then change mobile number page should be displayed
    When I input new mobile number that needs to be updated and click on update mobile number button
    Then mobile number should be updated in the customer details