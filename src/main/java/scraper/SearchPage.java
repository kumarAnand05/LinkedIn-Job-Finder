package scraper;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


public class SearchPage extends Driver{

    /**
     * Initializing page object elements
     */
    public SearchPage(){
        PageFactory.initElements(driver, this);
    }

    /**
     * Stores the collected job details
     */
    StringBuilder collectedJobDetails = new StringBuilder();

    /**
     * Stores the number of job details collected
     */
    static int numberOfJobsFound = 0;

    // add finding weblement for popup to login to continue to search jobs
    @FindBy(xpath = "//*[@id=\"base-contextual-sign-in-modal\"]/div/section/button")
    WebElement loginPopup;


    /**
     * Navigation bar options available on the login page
     */
    @FindBy(xpath = "//ul[contains(@class,'top-nav-menu')]/li")
    List<WebElement> loginNavigationOptions;

    /**
     * Job Role search box WebElement
     */
    @FindBy(id = "job-search-bar-keywords")
    WebElement jobRoleInput;

    /**
     * Job Location box WebElement
     */
    @FindBy(id = "job-search-bar-location")
    WebElement jobLocationInput;

    /**
     * Search button WebElement
     */
    @FindBy(xpath = "//section[@id='jobs-search-panel']//form/child::button")
    WebElement searchButton;

    /**
     * List of all the filter button WebElement
     */
    @FindBy(xpath = "//li[@class='filter']")
    List<WebElement> filters;

    /**
     *  List of all the option elements that are within the post time filter option
     */
    @FindBy(xpath = "//div[contains(@aria-label,'Date posted filter options')]/div")
    List<WebElement> postTimeOptions;

    /**
     * List of all the job postings that are available on the left pane of job search page
     */
    @FindBy(xpath = "//ul[@class='jobs-search__results-list']/li/div/a")
    List<WebElement> jobPostings;

    /**
     * See more jobs button element on the left pane of the job search page
     */
    @FindBy(xpath = "//button[contains(text(), 'See more jobs')]")
    WebElement seeMoreJobsButton;

    /**
     * Job role element on the right pane of the selected job
     */
    @FindBy(xpath = "//div[contains(@class,'top-card-layout__entity-info')]/div//h2")
    WebElement jobRoleElement;

    /**
     * Company name element on the right pane of the selected job
     */
    @FindBy(xpath = "//span[contains(@class,'topcard__flavor')]/a")
    WebElement companyNameElement;

    /**
     * Job location element on the right pane of the selected job
     */
    @FindBy(xpath = "//span[contains(@class,'topcard__flavor')][2]")
    WebElement jobLocationElement;

    /**
     * See more button element on the right pane of the selected job to expand the job description
     */
    @FindBy(xpath = "//button[contains(@class,'show-more-less')]")
    WebElement expandJobDescription;

    /**
     * List of elements of job criteria list available for any particular job in Job Description
     */
    @FindBy(xpath = "//ul[@class='description__job-criteria-list']/li/h3")
    List<WebElement> criteriaList;

    /**
     * Post time element on the right pane of the selected job
     */
    @FindBy(xpath = "//span[contains(@class,'posted-time-ago')]")
    WebElement postTimeElement;

    /**
     * Number of applicant element on the right pane of the selected job
     */
    @FindBy(xpath = "//*[contains(@class,'num-applicants__caption')]")
    WebElement numberOfApplicantElement;

    /**
     * Job description element on the right pane of the selected job
     */
    @FindBy(xpath = "//div[contains(@class,'show-more-less')]")
    WebElement jobDescriptionElement;

    /**
     * Job Link element on the right pane of the selected job
     */
    @FindBy(xpath = "//div[contains(@class,'top-card-layout__entity-info')]/div//a")
    WebElement jobLinkElement;


    /**
     * Function to pause script without having need to implement exception handling all the time.
     * @param seconds duration in seconds for which the script needs to be stopped
     */
    public void pauseFor(long seconds){
        try {
            Thread.sleep(seconds*1000);
        }
        catch (InterruptedException ignored){
        }
    }
    
    /**
     * Function to check if login popup is visible and close it if displayed
     */
    public void handleLoginPopup() {

        try {
            pauseFor(1);
            if(loginPopup.isDisplayed()) {
                System.out.println("Login popup appeared, closing it...");
                loginPopup.click();
                System.out.println("Login popup closed successfully.");
                pauseFor(2);
            }
            else {
                System.out.println("No login popup appeared.");
            }
        } catch (NoSuchElementException | TimeoutException e) {
            // Ignore if popup is not found
        }
    }


    /**
     * Opens the job page in case asked for login
     */
    public void handleLoginPage()
    {
        for(WebElement navigationOption: loginNavigationOptions){
            try {
                if(navigationOption.getText().strip().contains("Jobs")){
                    navigationOption.findElement(By.tagName("a")).click();
                }
            }
            catch(NoSuchElementException | TimeoutException navigationUnavailable){
                System.out.println("Unable to bypass login page...Try clearing history and cache data of browser and run script again");
                System.exit(-1);
            }
        }

        if(driver.getTitle().contains("Log In")){
            System.out.println("Unable to bypass login page...Try clearing history and cache data of browser and run script again");
            System.exit(-1);
        }
    }


    /**
     * To fill job role entered by the user in search box of the Job search page
     * @param jobRole String value of user input for Job role to search for.
     */
    public void fillJobRole(String jobRole) {
        pauseFor(1);
        jobRoleInput.click();
        jobRoleInput.clear();
        pauseFor(1);
        jobRoleInput.sendKeys(jobRole);
        pauseFor(2);
    }


    /**
     * To fill the job location value entered by the user in the location box of the Job search page
     * @param jobLocation String value of the user input for Job location to search for.
     */
    public void fillJobLocation(String jobLocation) {
        jobLocationInput.click();
        pauseFor(1);
        jobLocationInput.clear();
        pauseFor(2);
        jobLocationInput.sendKeys(jobLocation);
        pauseFor(2);
    }

    /**
     * Clicks on the search button to submit the search input
     */
    public void submitJobInput() {
        searchButton.click();
    }


    /**
     * To find Post time filter WebElement from the filter element list
     * @return Job post time filter WebElement
     */
    public WebElement getPostTimeFilterElement(){
        for(WebElement filter: filters){
            if(filter.getText().contains("Any time")){
                return filter;
            }
        }
        return null;
    }


    /**
     * Clicks on post time filter and select post time filter selected by user and submit the selection
     * @param postTime string value of the post time selected by the user
     */
    public void applyPostTimeFilter(String postTime){

        // Clicking on post time filter button
        try {
            pauseFor(1);
            getPostTimeFilterElement().click();
            pauseFor(2);
        }
        catch(Exception filterUnavailable){
            filterUnavailable.printStackTrace();
            System.exit(-1);
        }

        // Selecting user selected option from the available filters
        for (WebElement postOption: postTimeOptions){
            if(postOption.getText().contains(postTime)){
                postOption.findElement(By.tagName("input")).click();
                pauseFor(3);
            }
        }
        // Confirming filter selection by clicking on the done button of filter list
        postTimeOptions.getFirst().findElement(By.xpath("parent::div/parent::div/following::button")).click();
    }


    /**
     * To access the previous listed job
     * @param jobNumber index number of the job currently in access
     */
    public void accessPreviousJob(int jobNumber){
        jobPostings.get(jobNumber-1).click();
    }


    /**
     * To access the next listed job
     * @param jobNumber index number of the job currently in access
     */
    public void accessNextJob(int jobNumber){
        jobPostings.get(jobNumber+1).click();
    }


    /**
     * Reloads right pane of selected job in case pane doesn't load by clicking back and forth
     * @param jobNumber index number of the job that is currently selected
     */
    public void reloadJobDetails(int jobNumber){
        if(jobNumber==0){
            accessNextJob(jobNumber);
            pauseFor(2);
            accessPreviousJob(jobNumber+1);
            pauseFor(2);
        }
        else{
            accessPreviousJob(jobNumber);
            pauseFor(2);
            accessNextJob(jobNumber-1);
            pauseFor(2);
        }
    }


    /**
     * To click on the see more jobs button on the job search page
     * @return boolean value for whether button was clicked
     */
    public boolean accessSeeMoreJobs(){

        try {
            seeMoreJobsButton.click();
            pauseFor(2);
            jobPostings = driver.findElements(By.xpath("//ul[@class='jobs-search__results-list']/li/div/a"));
            return true;
        }
        catch(ElementNotInteractableException buttonUnavailable){
        }
        return false;
    }


    /**
     * Check whether the content of right pane is visible on the right pane
     * @return boolean value for availability of the contents of right pane
     */
    public boolean checkRightPaneAvailability(){
        try{
            return !jobRoleElement.getText().isEmpty();
        }
        catch(NoSuchElementException | TimeoutException loadingFailed){
        }
        return false;
    }


    /**
     * Collects the seniority level data of a job
     * @return String value of seniority level data for accessed job
     */
    public String getSeniorityCriteria(){

        pauseFor(1);
        for(WebElement criteria : criteriaList){
            if(criteria.getText().strip().contains("Seniority level")){
                return criteria.findElement(By.xpath("following::span")).getText();
            }
        }

        return null;
    }


    /**
     * Collect the job details on the right pane of the search page
     * @return Comma separated job details of selected job posting
     */
    public boolean collectJobData(boolean jdFilterStatus, int monthsOfExperience){

        // Filtering the job data if JD doesn't match then false will be returned and data won't be stored
        if(jdFilterStatus){
            expandJobDescription.click();
            pauseFor(1);

            String[] jobDescription = jobDescriptionElement.getText().strip().toLowerCase().split("\n");
            if(!new JDFilter().deepFilterJobDescription(jobDescription, monthsOfExperience)){
                return false;
            }
        }

        // Storing the data if JDFilter is off or JD matches user specified input values.
        StringBuilder jobData = new StringBuilder();
        jobData.append(jobRoleElement.getText().strip().replace(",","")).append(",");
        jobData.append(companyNameElement.getText().strip().replace(",", "")).append(",");
        jobData.append(getSeniorityCriteria()).append(",");
        jobData.append(jobLocationElement.getText().strip().split(",")[0]).append(",");

        // Checking if the data is already collected, if true then data is not collected again and false is returned
        if(collectedJobDetails.indexOf(jobData.toString())!=-1) {
            return false;
        }

        jobData.append(postTimeElement.getText().strip().replace(",", "")).append(",");
        jobData.append(numberOfApplicantElement.getText().strip().replace(",", "")).append(",");
        jobData.append(jobLinkElement.getAttribute("href")).append("\n");

        // Adding the job data
        collectedJobDetails.append(jobData);
        numberOfJobsFound++;

        return true;
    }


    /**
     * Iterate through the job list and stores the relevant data
     * @param numberOfJobs int value of the number of jobs that needs to be scraped
     */
    public StringBuilder findRelevantJobs(ArrayList<String> desiredExperienceLevel, int monthsOfExperience, boolean jdFilterStatus, int numberOfJobs ){

        int jobNumber = 0;
        int unexpectedError = 0;

        //Iterating through all the jobs until specified number of jobs data is scraped
        while(numberOfJobsFound<numberOfJobs){

            try {
                boolean dataCollected;
                jobPostings.get(jobNumber).click();
                pauseFor(2);

                // Matching user experience level to accessed job experience level if right pane is available
                if(checkRightPaneAvailability()) {
                    if(desiredExperienceLevel.contains(getSeniorityCriteria())){
                        dataCollected = collectJobData(jdFilterStatus, monthsOfExperience);
                        if(dataCollected){
                            System.out.println(numberOfJobsFound + " jobs collected from "+ (jobNumber+1) + " jobs.");
                        }
                        unexpectedError=0;
                    }
                }
                // Trying to load the right pane and collect the data
                else{
                    int flag = 0;

                    // Reloading right pane until available or flag limit is reached
                    while(!checkRightPaneAvailability() && flag < 5){
                        reloadJobDetails(jobNumber);
                        flag++;
                        if(desiredExperienceLevel.contains(getSeniorityCriteria())){
                            dataCollected = collectJobData(jdFilterStatus,monthsOfExperience);
                            if(dataCollected){
                                System.out.println(numberOfJobsFound + " jobs collected from "+ jobNumber+ " jobs.");
                            }
                            unexpectedError=0;
                            break;
                        }
                        if(flag==5){
                            System.out.println("Poor network connection. Unable to scan one job details. Bypassing...");
                            unexpectedError++;
                        }
                    }
                }
            }
            // By passing the job listing in case it is not accessible
            catch (NoSuchElementException | TimeoutException jobNotAccessible){
                System.out.println("Unable to scan one job details. Bypassing...");
                unexpectedError++;
            }
            catch (WebDriverException internetIssue){
                System.out.println("Unable to scan one job details due to network issue. Bypassing...");
                unexpectedError++;
            }
            catch (IndexOutOfBoundsException unknownError){
                System.out.println("Unable to access next jobs");
                unexpectedError++;
            }

            // Clicking on the see more jobs button if the job list has ended
            if(jobNumber==jobPostings.size()-1){
                if(!accessSeeMoreJobs()){
                    jobNumber--;        // To compensate the increment to avoid index out of bound error
                    unexpectedError++;
                }
                else{
                    unexpectedError=0;
                }
            }

            // To break any infinite loop
            if(unexpectedError>15){
                System.out.println("Some unexpected error caused stoppage of scraper, all possible data will be stored shortly...");
                break;
            }

            jobNumber++;
        }

        return collectedJobDetails;
    }
}
