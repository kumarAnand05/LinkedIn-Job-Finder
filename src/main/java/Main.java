import io.ExcelUtility;
import io.UserInput;
import scraper.Scraper;


public class Main {

    static StringBuilder jobData;

    public static void main(String[] args){

        // Takes user input
        UserInput userInput = new UserInput();
        userInput.takeUserInput();

        Scraper scraper = new Scraper();
        jobData = scraper.startScraping(userInput);

        // Creating csv from the data
        new ExcelUtility().compileJobData(jobData);
        System.out.println("All possible job data scraped and stored locally.");
    }
}
