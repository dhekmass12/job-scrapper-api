package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class FilterJob {
    List<String> jobFields;
    List<String> publishedDates;
    List<String> locations;
    List<String> companies;

    public FilterJob(){

    }

    public FilterJob(String jobFields, String publishedDates, String locations, String companies) throws Exception{
        List<String> jobFieldList = new ArrayList<>(Arrays.asList(jobFields.split(",")));
        jobFieldList.replaceAll(String::toLowerCase);
        List<String> publishedDateList = new ArrayList<>(Arrays.asList(publishedDates.split(",")));
        for (int i = 0; i < publishedDateList.size(); i++){
            if (!publishedDateList.get(i).isEmpty() && publishedDateList.get(i).length() != 10){
                throw new Exception("ERROR: TANGGAL HARUS TERDIRI DARI 10 KARAKTER");
            }
            publishedDateList.set(i, publishedDateList.get(i).toLowerCase());
        }
        List<String> locationList = new ArrayList<>(Arrays.asList(locations.split(",")));
        locationList.replaceAll(String::toLowerCase);
        List<String> companyList = new ArrayList<>(Arrays.asList(companies.split(",")));
        locationList.replaceAll(String::toLowerCase);

        this.jobFields = jobFieldList;
        this.publishedDates = publishedDateList;
        this.locations = locationList;
        this.companies = companyList;
    }
}