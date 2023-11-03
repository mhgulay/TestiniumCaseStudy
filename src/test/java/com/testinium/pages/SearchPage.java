package com.testinium.pages;

import com.testinium.utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Random;

public class SearchPage {

    public SearchPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(id = "o-searchSuggestion__input")
    public WebElement searchSuggestionInput;

    @FindBys({
            @FindBy(xpath = "//div[@id='productList']/div")
    })
    public List<WebElement> products;
    public WebElement randomProduct(){
        Random random = new Random();
        return products.get(random.nextInt(products.size()));
    }

}
