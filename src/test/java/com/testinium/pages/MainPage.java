package com.testinium.pages;

import com.testinium.utils.Driver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {

    public MainPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }
    @FindBy(id = "onetrust-accept-btn-handler")
    public WebElement allowCookies;
    @FindBy(id = "genderManButton")
    public WebElement selectGender;
    @FindBy(css = ".o-header__search--input")
    public WebElement searchInput;


}
