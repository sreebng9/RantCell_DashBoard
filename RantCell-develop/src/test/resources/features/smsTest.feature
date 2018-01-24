@smsTest
Feature: Perform the sms Test & caprure the result

  Scenario: Perform the ping Test & caprure the result
    Given I am on RantCell pre-login page
    When I enter username "sreekanth@megrontech.co.uk" and I enter password "sreekanth@rantcell"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    And I click on Remote Test and I click on Add test group
    When I enter group name as "SreeTest2" and click on next button
    Then List of devices page should be displayed
    When I select the device and click on add button
    Then test group name should be displayed in the list of groups
    When I select the test group that we created and check for devices
    Then I can see the list of devices and their status
    And I can see detailed details by clicking on show details link
    When I enter mandetory run test details and check on sms test
    And I enter the B party phone number as "9655858693" and wait duration "30" seconds and proceed to start test
    Then I can see the staus in table view format.
    And I select the test group that we created and I delete the particular group
    
     
    
    