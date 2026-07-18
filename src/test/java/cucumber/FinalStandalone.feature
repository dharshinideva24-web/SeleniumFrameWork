
@tag
Feature: Purchase the order from Ecommerce Website
          
    Background:
    Given I landed on Ecommerce Page
      
          
          
          
@tag2
Scenario Outline: Positive Test of Submitting the order
      Given Logged in with username <email> and password <password>
      When  I add product <productName> to cart
      And   Checkout <productName> and submit the order
      Then  "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage
      
  Examples:
    |email                     | password  | productName|
    |dharshinideva24@gmail.com | Deva@2410 | ZARA COAT 3|
 
          

