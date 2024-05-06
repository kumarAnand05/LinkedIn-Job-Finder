package io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserInput {

    public String jobRole;
    public String jobLocation;
    public String postTime;
    public ArrayList<String> desiredExperienceLevel = new ArrayList<>();
    public boolean jdFilter;
    public int numberOfJobs;
    public int monthsOfExperience;


    /**
     * Sets Job title that user want to search for.
     * @param jobRoleInput String value of job title
     */
    public void setJobRole(String jobRoleInput){
        if(!jobRoleInput.strip().isEmpty()) {
            this.jobRole = jobRoleInput.strip();
        }
        else{
            System.out.println("No job role entered. Please run the script again.");
            System.exit(-1);
        }
    }


    /**
     * Sets job location that user is searching for.
     * @param jobLocationInput String value of the job location
     */
    public void setJobLocation(String jobLocationInput){
        if(!jobLocationInput.strip().isEmpty()) {
            this.jobLocation = jobLocationInput.strip();
        }
        else{
            System.out.println("No job location provided. Please run the script again.");
            System.exit(-1);
        }
    }


    /**
     * Sets time of job posting that user want to extract
     * @param postTimeInput String value of expected job post time
     */
    public void setPostTime(String postTimeInput){
        if(postTimeInput==null){
            System.out.println("No valid input provided so default post time 'Any time' is selected.");
            this.postTime = "Any time";
        }
        else {
            this.postTime = postTimeInput;
        }
    }

    /**
     * Sets selected desired experience levels as per user input.
     * @param allExperienceLevels Map object of key value pairs for all the available experience levels.
     * @param experienceLevelInput String array of all the user input for selection of experience level from list
     */
    public void setDesiredExperienceLevel(Map<Integer,String> allExperienceLevels, String[] experienceLevelInput){
        for(String selection : experienceLevelInput){
            try{
                String selectedExperience = allExperienceLevels.get(Integer.parseInt(selection));
                if(selectedExperience!=null){
                    desiredExperienceLevel.add(selectedExperience);
                }
            }
            catch (Exception ignored){
            }
        }

        // Checking if the experience level input is empty and filling all the level values by default
        if(desiredExperienceLevel.isEmpty()){
            System.out.println("No filter applied as no valid input provided");
            for (Map.Entry<Integer, String> experienceLevel : allExperienceLevels.entrySet()) {
                desiredExperienceLevel.add(experienceLevel.getValue());
            }
        }
    }


    /**
     * Sets the number of job that needs to be scraped
     * @param numberOfJobsInput integer value for the number of jobs that needs to be scraped
     */
    public void setNumberOfJobs(int numberOfJobsInput){
        this.numberOfJobs = numberOfJobsInput;
    }


    /**
     * Sets whether the advanced filter is to be turned on or not.
     * @param filterValueInput boolean value for advanced filter option
     */
    public void setJDFilter(String filterValueInput){
        jdFilter = filterValueInput.strip().equalsIgnoreCase("Yes") || filterValueInput.strip().equalsIgnoreCase("Y");
        if(!jdFilter){
            System.out.println("Keeping advanced filter off as no value provided");
        }
    }


    /**
     * Sets months of experience that user have
     * @param jobExperienceInput int value of months of experience that user have
     */
    public void setMonthsOfExperience(int jobExperienceInput){
        this.monthsOfExperience = jobExperienceInput;
    }


    /**
     * Take all the required user details before starting the scraper.
     */
    public void takeUserInput(){

        Scanner sc = new Scanner(System.in);

        // Taking job role input
        System.out.print("Enter the Job Role (eg. DevOps Engineer): ");
        setJobRole(sc.nextLine());

        // Taking job location input
        System.out.print("Enter the Job Location: ");
        setJobLocation(sc.nextLine());


        // List of all job post time available for selection
        Map<Integer, String> availableJobPostTime = new HashMap<>();
        availableJobPostTime.put(1, "Past 24 hours");
        availableJobPostTime.put(2, "Past week");
        availableJobPostTime.put(3, "Past month");
        availableJobPostTime.put(4, "Any time");

        // Taking the user input for time of job posting filter selection
        System.out.println("\nChoose the time of Job Posting from options below");
        for (Map.Entry<Integer, String> jobPostTime : availableJobPostTime.entrySet()) {
            System.out.println(jobPostTime.getKey() + ". " + jobPostTime.getValue());
        }
        System.out.print("\nEnter option number to select (eg. 1): ");
        String postTimeSelection = sc.nextLine().strip();
        try {
            setPostTime(availableJobPostTime.get(Integer.parseInt(postTimeSelection)));
        }
        catch (Exception invalidInput){
            setPostTime(null);
        }


        //List of all the Experience levels available for selection
        Map<Integer, String> allExperienceLevels = new HashMap<>();
        allExperienceLevels.put(1, "Internship");
        allExperienceLevels.put(2, "Entry level");
        allExperienceLevels.put(3, "Associate");
        allExperienceLevels.put(4, "Mid-Senior level");
        allExperienceLevels.put(5, "Director");
        allExperienceLevels.put(6, "Executive");
        allExperienceLevels.put(7, "Not Applicable");

        // Taking user input for experience level
        System.out.println("\nChoose experience level from options below");
        for (Map.Entry<Integer, String> experienceLevel : allExperienceLevels.entrySet()) {
            System.out.println(experienceLevel.getKey() + ". " + experienceLevel.getValue());
        }
        System.out.print("\nEnter space separated number to choose from the above list: ");
        String[] experienceInput = sc.nextLine().split(" ");
        setDesiredExperienceLevel(allExperienceLevels, experienceInput);

        // Taking user input to set the number of jobs to scrape
        System.out.print("Enter the maximum number of jobs listings that you want: ");

        try{
            int jobNumberInput = Integer.parseInt(sc.nextLine());
            if(jobNumberInput>0) {
                setNumberOfJobs(jobNumberInput);
            }
            else{
                System.out.println("Invalid Input provided for number of jobs. Run the script again and provide valid input.");
                System.exit(-1);
            }
        }
        catch (Exception invalidInput){
            System.out.println("Invalid Input provided for number of jobs. Run the script again and provide valid input.");
            System.exit(-1);
        }


        // Taking input to turn on the job description filter option
        System.out.print("Turn on Job Description filtering (Y/N)? This will make the process slower and output more relevant: ");
        setJDFilter(sc.nextLine().strip());

        // Taking input for months of experience in case opted for advanced filter
        if(jdFilter){
            System.out.print("Enter the months of experience that you have: ");
            if(sc.hasNextInt()) {
                setMonthsOfExperience(sc.nextInt());
            }
            else{
                System.out.println("Wrong experience details provided. Run the script again.");
                System.exit(-1);
            }
        }
    }


    /**
     * To print all the user input for debugging purpose
     */
    public void printUserInput(){
        System.out.println("Entered Job role: "+ this.jobRole);
        System.out.println("Entered Job location: "+ this.jobLocation);
        System.out.println("Entered Post time filter: "+ this.postTime);
        System.out.println("Selected experience levels : "+ this.desiredExperienceLevel);
        System.out.println("Entered number of jobs: "+ this.numberOfJobs);
        System.out.println("Entered filter input: "+ this.jdFilter);
        System.out.println("Entered months of experience: "+ this.monthsOfExperience);
    }
}
