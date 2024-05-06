package scraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.time.Duration;
import java.util.Scanner;


public class Driver {

    static WebDriver driver;    // Driver kept static to make it easily accessible from other classes

    /**
     * Creates WebDriver object for browser selection based on user input
     * @return WebDriver object
     */
    public static WebDriver createDriver(){

        Scanner sc = new Scanner(System.in);
        int browserSelection = 0;

        System.out.println("Choose the browser that you want to use:\n1. Chrome\n2. Edge");
        if(sc.hasNextInt()){
            browserSelection = sc.nextInt();
            sc.close();
        }
        // if invalid input is provided then the code is terminated
        else{
            System.out.println("Invalid value provided.");
            System.exit(-1);
        }

        if(browserSelection==1){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            driver = new ChromeDriver(options);
        }
        else if(browserSelection == 2){
            EdgeOptions options = new EdgeOptions();
            options.addArguments("-inprivate");
            driver = new EdgeDriver(options);
        }
        // if options other than provided are selected then code is terminated
        else{
            System.out.println("Invalid value provided.");
            System.exit(-1);
        }

        // Setting browser configurations
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().deleteAllCookies();
        return driver;
    }
}
