package framework.pages;

import framework.core.Page;
import framework.logger.LogUtils;

import java.util.HashMap;

public class HomePage extends Page {

    HashMap<String, String> locatorMap = new HashMap<>();

    public HomePage() {
        LogUtils.INFO("Navigating to " + this.getClass().getSimpleName());
    }

    private HashMap<String, String> pageHeader() {
        locatorMap.put("android", "id: ");
        locatorMap.put("ios", "id: ");
        locatorMap.put("web", "css:div.jumbotron h1");
        return locatorMap;
    }

    private HashMap<String, String> filterTeam() {
        locatorMap.put("android", "id:search_box");
        locatorMap.put("ios", "id: ");
        locatorMap.put("web", "css:input.form-control.team-filter");
        return locatorMap;
    }

//    public void selectSearchBar() throws Exception {
//        if (!DriverManager.driverType.equals("web")) {
//            click(searchBar());
//        }
//    }

    public void FilterTeamBar() throws Exception {
        click(filterTeam());
    }

    public String getPageTitle() throws Exception {
        return getText(pageHeader());
    }

    public void isHomePage() throws Exception {
        waitForPageLoad();
        isElementPresent(filterTeam());
    }
}
