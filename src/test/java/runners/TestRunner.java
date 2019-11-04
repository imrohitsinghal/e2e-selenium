package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import stepDefs.Hooks;

@CucumberOptions(features = {"src/test/resources/features"},
        glue = {"stepDefs"},
        plugin = {"pretty", "html:target/cucumber-report.json", "json:target/report.json"})
@RunWith(Cucumber.class)
public class TestRunner extends Hooks {

}