package org.example.service;

import org.example.model.FilterJob;
import org.example.model.Job;
import org.example.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    private List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    private boolean doesContain(List<String> substrings, String string) {
        for (String substring : substrings) {
            if (string.contains(substring)) {
                return true;
            }
        }

        return false;
    }

    public List<Job> getFilteredJobs(FilterJob filterJob) {
        List<Job> jobs = getAllJobs();
        List<Job> new_jobs = new ArrayList<>();

        List<String> companies = filterJob.getCompanies();
        List<String> locations = filterJob.getLocations();
        List<String> published_dates = filterJob.getPublishedDates();
        List<String> job_fields = filterJob.getJobFields();

        List<String> NO_FILTER = new ArrayList<>();
        NO_FILTER.add("");

        for (Job job : jobs) {
            String company = job.getCompany();
            String location = job.getLocation();
            String published_date = job.getPublished_date();
            String job_field = job.getJob_field();
            if ((companies.equals(NO_FILTER) || doesContain(companies, company)) &&
                    (locations.equals(NO_FILTER) || doesContain(locations, location)) &&
                    (published_dates.equals(NO_FILTER) || doesContain(published_dates, published_date)) &&
                    (job_fields.equals(NO_FILTER) || doesContain(job_fields, job_field))){
                new_jobs.add(job);
            }
        }

        return new_jobs;
    }
}
