package io;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

public class ExcelUtility {

    public void compileJobData(StringBuilder collectedData){

        // Filename and filepath of the csv
        String fileName = "Job Listings ("+ LocalDate.now() +").csv";
        String filePath = "CollectedJobData"+File.separator+fileName;

        StringBuilder newJobData = new StringBuilder();
        StringBuilder existingData = new StringBuilder();
        String[] collectedJobData = collectedData.toString().split("\n");

        File csvFile = new File(filePath);

        // Checking if a csv file already exist for today's date.
        if(csvFile.exists()){

            // Reading and filtering out all data that are present in the existing csv
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    existingData.append(line.strip()).append(", ");
                }

                for(String jobData : collectedJobData){
                    String jobMatcher = String.join(",", Arrays.copyOfRange(jobData.split(","), 0, 4));
                    if(existingData.indexOf(jobMatcher.strip())==-1){
                        newJobData.append(jobData).append("\n");
                    }
                }
            }
            catch (IOException e) {
                System.out.println("Some error occurred reading the existing csv file so data has been printed below\n\n\n.");
                System.out.println(newJobData);
            }
        }
        // In case no csv is present for today then all data is stored
        else{
            for(String jobData : collectedJobData){
                newJobData.append(jobData).append("\n");
            }
        }

        // Creating CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String[] lines = newJobData.toString().split("\n");     // Splitting each Job Data

            // Adding each line to csv
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
