Feature: Trello Board Automation

  Scenario: Create a board, add cards, update one card, and delete them
    When I create a new board on the Trello
    Then I add a list to the board
    And I add two cards to the board
    And I randomly update one of the cards
    And I delete the cards
    And I delete the board