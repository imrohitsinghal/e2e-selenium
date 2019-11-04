Feature: Smoke Feature

  @mweb @web @qa @home
  Scenario: Validate Page Title on Mobile Web
      Given User is on the home page
      Then I should see page title as "Fixture Finder AT 2019"


  @mweb @web @qa @visual
  Scenario: Visual Test for Home Page
    Given User is on the home page
    Then I compare the "home/landingpage" screen