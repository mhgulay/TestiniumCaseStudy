Feature: Beymen Website Automation

  Scenario: Performing a search and adding a product to the basket
    Given the Beymen website has been opened
    When the homepage is loaded
    And the first keyword from excel file is entered in the search bar
    And the first keyword is cleared from the search bar
    And the second keyword from excel file is entered in the search bar
    And the enter key is pressed on the keyboard
    And a random product is selected from the displayed results
    And the product information and price are written in a txt file
    And the selected product is added to the basket
    And the price on the product page is compared with the price in the basket
    And the quantity of the product is verified to be 2
    And the selected product is removed from the basket and the basket is empty