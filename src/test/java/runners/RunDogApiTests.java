package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:Features"},
        glue = {"steps"},
        plugin = {"pretty",
                "json:target/cucumber-reports/cucumber.json",
                "html:target/cucumber-reports/cucumber-basic.html"},
        tags = "@DogAPI",
        monochrome = true,
        snippets = SnippetType.CAMELCASE,
        dryRun = false
		)
public class RunDogApiTests {

}
