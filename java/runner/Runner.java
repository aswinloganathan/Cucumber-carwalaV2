package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

public class Runner {
	
	@CucumberOptions(features = "src/main/java/feature/carwala.feature", glue = "steps", monochrome = true)

	public class RunTest extends AbstractTestNGCucumberTests{

	}

}
