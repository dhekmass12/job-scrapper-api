package org.example.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterJobDTO {
    String jobFields;
    String publishedDates;
    String locations;
    String companies;

    public FilterJobDTO(){

    }

    public FilterJobDTO(String jobFields, String publishedDates, String locations, String companies){
        this.jobFields = jobFields;
        this.publishedDates = publishedDates;
        this.locations = locations;
        this.companies = companies;
    }
}