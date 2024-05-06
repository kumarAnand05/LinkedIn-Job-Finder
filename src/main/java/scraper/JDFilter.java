package scraper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JDFilter {

    public boolean deepFilterJobDescription(String[] jobDescription, int maxMonthOfExperience){
        int[] yearOfExperience = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        ArrayList<Integer> collectedYearValues = new ArrayList<>();

        for(String line: jobDescription){
            if(line.contains("exp") || line.contains("experience")){
                for(int years :yearOfExperience){
                    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(String.valueOf(years)) + "\\b");
                    Matcher matcher = pattern.matcher(line);
                    if(matcher.find()){
                        if(line.contains("month")){
                            collectedYearValues.add(Integer.parseInt(matcher.group()));
                        }
                        else{
                            collectedYearValues.add(Integer.parseInt(matcher.group())*12);
                        }
                    }
                }
            }
            else if(maxMonthOfExperience<=12 && line.contains("fresher") && !line.contains("not")){
                return true;
            }
        }

        if(collectedYearValues.isEmpty() || Collections.min(collectedYearValues)<=maxMonthOfExperience){
            return true;
        }
        return false;
    }
}
