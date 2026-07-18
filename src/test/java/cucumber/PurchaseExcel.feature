@excel
Feature: Purchase the order using data from an Excel sheet

  Background:
    Given I landed on Ecommerce Page

  @excelOrder
  Scenario: Purchase order using Excel data set order1
    When I purchase using data set "order1"
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage
