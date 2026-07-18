
@tag
Feature: Purchase the order from Ecommerce Website
          
   

      
          
          
          
@tag2
Scenario Outline: Positive Test of Submitting the order
      Given I landed on Ecommerce Page
      When Logged in with username <email> and password <password>
      Then  "Incorrect email or password." message is displayed
      
  Examples:
    |email                     | password  |
    |dharshinideva24@gmail.com | Deva@241  |
 