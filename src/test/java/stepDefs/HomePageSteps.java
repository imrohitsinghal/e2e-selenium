package stepDefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import framework.core.Page;
import framework.pages.*;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import tulna.Screens.TulnaExecutor;

public class HomePageSteps extends Page {

    HomePage home = new HomePage();

    @Given("^User is on the home page")
    public void userOnHome() throws Throwable {
        home.isHomePage();
    }

    @Then("^I should see page title as \"(.*)\"$")
    public void validateLoggedInUser(String expected) throws Exception {
        Assert.assertEquals(expected, home.getPageTitle());
    }

    @Then("^I compare the \"([^\"]*)\" screen$")
    public void compareScreens(String screenName) throws Throwable {
        waitForPageLoad();
        Assert.assertTrue(new TulnaExecutor().tulnaExecutorNativeCompare(screenName, 0));

    }
}
