package com.testinium.step_defs;

import com.testinium.utils.ConfigurationReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.Random;

public class TrelloApiTask_Step_Defs {

    private static final String token = ConfigurationReader.getProperty("token");
    private static final String key = ConfigurationReader.getProperty("key");
    private static ResponseBody body;
    private static String board_id = "";
    private static String list_id = "";
    private static String[] cards_id = new String[2];

    @When("I create a new board on the Trello")
    public void iCreateANewBoardOnTheTrello() {
        RestAssured.baseURI = "https://api.trello.com/1/";
        Response response = RestAssured.given().header("Content-Type", "application/json;charset=utf-8")
                .queryParam("name", "Board")
                .queryParam("key", key)
                .queryParam("token", token)
                .post("boards/");
        body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
        board_id = response.path("id");
    }

    @Then("I add a list to the board")
    public void iAddAListToTheBoard() {
        Response response =  RestAssured.given()
                .pathParam("id",board_id).and()
                .queryParam("name","List").and()
                .queryParam("key",key).and()
                .queryParam("token",token).and()
                .header("Content-Type", "application/json;charset=utf-8")
                .when().post("boards/{id}/lists");
        body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
        list_id = response.path("id");
    }

    @And("I add two cards to the board")
    public void iAddTwoCardsToTheBoard() {
        for (int i = 0; i <= 1; i++) {
            Response response =  RestAssured.given()
                    .queryParam("idList",list_id).and()
                    .queryParam("key",key).and()
                    .queryParam("name","Card_"+i).and()
                    .queryParam("token",token).and()
                    .header("Content-Type", "application/json;charset=utf-8")
                    .when().post("cards");
            body = response.getBody();
            System.out.println(response.getStatusLine());
            System.out.println(body.asString());
            cards_id[i] = response.path("id");
        }
    }

    @And("I randomly update one of the cards")
    public void iRandomlyUpdateOneOfTheCards() {
        Random random = new Random();
        int i = random.nextInt(2);

        Response response = RestAssured.given()
                .pathParam("id", cards_id[i]).and()
                .queryParam("name", "updated_name")
                .queryParam("key", key).and()
                .queryParam("token", token).and()
                .header("Content-Type", "application/json;charset=utf-8")
                .when().put("cards/{id}");
        body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }

    @And("I delete the cards")
    public void iDeleteTheCards() {
        for (String each : cards_id) {
            Response response = RestAssured.given()
                    .pathParam("id", each).and()
                    .queryParam("key", key).and()
                    .queryParam("token", token).and()
                    .header("Content-Type", "application/json;charset=utf-8")
                    .when().delete("cards/{id}");
            body = response.getBody();
            System.out.println(response.getStatusLine());
            System.out.println(body.asString());
        }
    }

    @And("I delete the board")
    public void iDeleteTheBoard() {
        Response response = RestAssured.given()
                .pathParam("id", board_id).and()
                .queryParam("key", key).and()
                .queryParam("token", token).and()
                .header("Content-Type", "application/json;charset=utf-8")
                .when().delete("boards/{id}");
        body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println(body.asString());
    }
}
