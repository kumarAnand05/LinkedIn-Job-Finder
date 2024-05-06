package scraper;

import io.UserInput;
import org.openqa.selenium.WebDriver;

public class Scraper {

    WebDriver driver;


    /**
     * Scrapes the job data and returns the collected data
     * @param userInput object that has user input value
     * @return ArrayList of string containing the scraped data
     */
    public StringBuilder startScraping(UserInput userInput) {


        driver = Driver.createDriver();
        System.out.println("\n\nStarting the scraper...");
        driver.get("https://in.linkedin.com/jobs/search?keywords=&location=&geoId=&trk=public_jobs_jobs-search-bar_search-submit&position=1&pageNum=0");

        // Initializing the page object
        SearchPage jobSearchPage = new SearchPage();
        if(driver.getTitle().contains("Log In")){
            jobSearchPage.handleLoginPage();
        }

        // Filling all user details
        jobSearchPage.fillJobRole(userInput.jobRole);
        jobSearchPage.fillJobLocation(userInput.jobLocation);
        jobSearchPage.submitJobInput();

        // Applying user desired filters
        jobSearchPage.applyPostTimeFilter(userInput.postTime);

        // Iterating and collecting relevant jobs
        StringBuilder collectedData = jobSearchPage.findRelevantJobs(userInput.desiredExperienceLevel,
                                                                         userInput.monthsOfExperience,
                                                                         userInput.jdFilter,
                                                                         userInput.numberOfJobs);

        // Releasing oll resources
        driver.quit();

        return collectedData;
    }
}
