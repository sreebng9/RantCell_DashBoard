@verifyTestGropfunctionalities
Feature: Perform the speed Test & caprure the result
	@liveTracking
  Scenario: Perform the ping Test & caprure the result
    Given I am on RantCell pre-login page
    When I enter username "sreekanth@megrontech.co.uk" and I enter password "sreekanth@rantcell"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    And I click on Remote Test and I click on Add test group
    When I enter group name as "SreeTest11" and click on next button
    Then List of devices page should be displayed
    When I select the device and click on add button
    Then test group name should be displayed in the list of groups
    When I select the test group that we created and check for Live Tracking    
    And mapview is captured
    
    @sheduleTest
  Scenario: Perform the ping Test & caprure the result
    Given I am on RantCell pre-login page
    When I enter username "sreekanth@megrontech.co.uk" and I enter password "sreekanth@rantcell"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    And I click on Remote Test and I click on Add test group
    When I enter group name as "SreeTest11" and click on next button
    Then List of devices page should be displayed
    When I select the device and click on add button
    Then test group name should be displayed in the list of groups
    When I select the test group that we created and check for Schedule Test    
    And I fill the shedule test details     
    And I enter the B party phone number as "9655858693" and call duration "5" seconds and proceed to start test
    Then I can see the staus in table view format. 
    
    @deleteAGroup
    Scenario: Perform the ping Test & caprure the result
    Given I am on RantCell pre-login page
    When I enter username "sreekanth@megrontech.co.uk" and I enter password "sreekanth@rantcell"
    And I click on login button
    Then I should be logged onto the RantCell Home page
    And I click on Remote Test and I click on Add test group
    When I enter group name as "TestGroup" and click on next button
    Then List of devices page should be displayed
    When I select the device and click on add button
    Then test group name should be displayed in the list of groups
    #When I select the test group that we created and check for Schedule Test  
    And I select the test group that we created and I delete the particular group
    
      