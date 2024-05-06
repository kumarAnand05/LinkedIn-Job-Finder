import io.ExcelUtility;
import io.UserInput;
import org.openqa.selenium.WebDriverException;
import scraper.Scraper;


public class Main {

    static StringBuilder jobData;

    public static void main(String[] args){

        // Takes user input
        UserInput userInput = new UserInput();
        userInput.takeUserInput();

        // Starting scraper
        try {
            Scraper scraper = new Scraper();
            jobData = scraper.startScraping(userInput);
        }
        catch (WebDriverException internetDisconnected){
            System.out.println("Please connect to internet and try again");
            System.exit(-1);
        }

        // Creating csv from the data
        new ExcelUtility().compileJobData(jobData);
        System.out.println("All possible job data scraped and stored locally.");
    }
}
