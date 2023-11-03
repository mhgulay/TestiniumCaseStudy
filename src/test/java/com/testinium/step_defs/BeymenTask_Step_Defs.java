package com.testinium.step_defs;

import com.testinium.pages.BasketPage;
import com.testinium.pages.MainPage;
import com.testinium.pages.ProductPage;
import com.testinium.pages.SearchPage;
import com.testinium.utils.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import static com.testinium.utils.BrowserUtils.waitTwoSeconds;
import static com.testinium.utils.Driver.getDriver;
import static org.junit.Assert.*;


public class BeymenTask_Step_Defs {

    MainPage mainPage = new MainPage();
    SearchPage searchPage = new SearchPage();
    ProductPage productPage = new ProductPage();
    BasketPage basketPage = new BasketPage();
    String getPriceInfo;
    String getProductInfo;

    final static Logger logger = Logger.getLogger(BeymenTask_Step_Defs.class);


    @Given("the Beymen website has been opened")
    public void theBeymenWebsiteHasBeenOpened() {
        logger.info("Test started");
        getDriver().get(ConfigurationReader.getProperty("url"));
        waitTwoSeconds();
        mainPage.allowCookies.click();
        waitTwoSeconds();
        mainPage.selectGender.click();
    }

    @When("the homepage is loaded")
    public void theHomepageIsLoaded() {
        String expectedTitle = "Beymen.com – Türkiye’nin Tek Dijital Lüks Platformu";
        waitTwoSeconds();
        String actualTitle = getDriver().getTitle();
        logger.info("User is on the " + actualTitle);
        waitTwoSeconds();
        assertEquals(expectedTitle, actualTitle);
    }

    @And("the first keyword from excel file is entered in the search bar")
    public void theFirstKeywordFromExcelFileIsEnteredInTheSearchBar() {
        waitTwoSeconds();
        mainPage.searchInput.sendKeys(ExcelReader.readCell(0, 0));
        logger.info("User is searching first keyword");
    }

    @And("the first keyword is cleared from the search bar")
    public void theFirstKeywordIsClearedFromTheSearchBar() {
        logger.info("User is clearing the search box");
        waitTwoSeconds();
        searchPage.searchSuggestionInput.sendKeys(Keys.CONTROL + "a");
        waitTwoSeconds();
        searchPage.searchSuggestionInput.sendKeys(Keys.BACK_SPACE);
    }

    @And("the second keyword from excel file is entered in the search bar")
    public void theSecondKeywordFromExcelFileIsEnteredInTheSearchBar() {
        logger.info("User is searching second keyword");
        waitTwoSeconds();
        BrowserUtils.waitForClickability(mainPage.searchInput, 20);
        waitTwoSeconds();
        searchPage.searchSuggestionInput.click();
        waitTwoSeconds();
        searchPage.searchSuggestionInput.sendKeys(ExcelReader.readCell(0, 1));
    }

    @And("the enter key is pressed on the keyboard")
    public void theEnterKeyIsPressedOnTheKeyboard() {
        waitTwoSeconds();
        searchPage.searchSuggestionInput.sendKeys(Keys.ENTER);
        logger.info("User is clicking the Enter button");
    }

    @And("a random product is selected from the displayed results")
    public void aRandomProductIsSelectedFromTheDisplayedResults() {
        logger.info("User is choosing random product");
        waitTwoSeconds();
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(searchPage.randomProduct());
        waitTwoSeconds();
        searchPage.randomProduct().click();
    }

    @And("the product information and price are written in a txt file")
    public void theProductInformationAndPriceAreWrittenInATxtFile() {
        logger.info("Gets the product information and writes the file");
        waitTwoSeconds();
        getProductInfo = productPage.productInfo.getText();
        getPriceInfo = productPage.productPrice.getText();
        String str = "Product Price : " + getPriceInfo + "\n" + "Product Info : " + getProductInfo;
        WriteToTxt.writeToTxt(str);
    }

    @And("the selected product is added to the basket")
    public void theSelectedProductIsAddedToTheBasket() {
        logger.info("User is adding to basket random product");
        try {
            productPage.productSizeForStockValid.click();
        } catch (NoSuchElementException e) {
            productPage.productSizeForStockLess.click();
        }

        productPage.addToBasket.click();
    }

    @And("the price on the product page is compared with the price in the basket")
    public void thePriceOnTheProductPageIsComparedWithThePriceInTheBasket() {
        logger.info("User is checking the price value and checking basket price value");
        productPage.goToBasket.click();
        String actualPrice = basketPage.priceOnBasket.getText();
        String formattedPrice;
        if (getPriceInfo.contains(",")) {
            formattedPrice = getPriceInfo;
        } else {
            getPriceInfo = getPriceInfo.replace(" TL", "").trim();
            formattedPrice = getPriceInfo + ",00 TL";
        }
        assertEquals(formattedPrice, actualPrice);
    }

    @And("the quantity of the product is verified to be {int}")
    public void theQuantityOfTheProductIsVerifiedToBe(Integer total) {
        logger.info("User is increasing the quantity, it is confirmed that the number of products is 2.");
        waitTwoSeconds();
        try {
            Select quantity = new Select(basketPage.quantitySelect);
            quantity.selectByValue(String.valueOf(total));
            waitTwoSeconds();
            Integer actualQuantity = Integer.parseInt(basketPage.quantity().getFirstSelectedOption().getAttribute("value"));
            waitTwoSeconds();
            assertEquals(total, actualQuantity);
            waitTwoSeconds();
        } catch (NoSuchElementException e) {
            System.out.println("Only one product left");
        }
        waitTwoSeconds();
    }

    @And("the selected product is removed from the basket and the basket is empty")
    public void theSelectedProductIsRemovedFromTheBasketAndTheBasketIsEmpty() {
        logger.info("User deleted product from the basket and checks for the basket is empty");
        waitTwoSeconds();
        basketPage.deleteProduct.click();
        waitTwoSeconds();
        try {
            assertTrue(basketPage.basketIsEmpty.isDisplayed());
        } catch (NoSuchElementException e) {
            fail();
        }
    }
}
