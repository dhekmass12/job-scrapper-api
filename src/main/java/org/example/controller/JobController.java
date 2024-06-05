package org.example.controller;

import org.example.model.FilterJob;
import org.example.model.FilterJobDTO;
import org.example.model.Job;
import org.example.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("")
public class JobController {
    @Autowired
    private JobService jobService;

    //    @GetMapping("")
//    public String loginPage(Model model) {
//        LoginDTO loginDTO = new LoginDTO();
//        model.addAttribute("loginDTO", loginDTO);
//        return "login page";
//    }
    @GetMapping("jobs")
    public String landingPage(Model model) {
        FilterJobDTO filterJobDTO = new FilterJobDTO();
        model.addAttribute("filterJobDTO", filterJobDTO);
        return "filter page";
    }

    @GetMapping("jobs/filter")
    public String jobFilter(Model model, @RequestParam("job_fields") String jobFields, @RequestParam("published_dates") String publishedDates, @RequestParam("locations") String locations, @RequestParam("companies") String companies) throws Exception{
        FilterJob filterJob = new FilterJob(jobFields, publishedDates, locations, companies);
        List<Job> jobs = jobService.getFilteredJobs(filterJob);
        model.addAttribute("jobs", jobs);
        return "all jobs page";
    }

    @PostMapping("jobs")
    public String jobFilterForm(@ModelAttribute FilterJobDTO filterJobDTO, Model model){

        return String.format("redirect:jobs/filter?job_fields=%s&published_dates=%s&locations=%s&companies=%s",
                filterJobDTO.getJobFields(),
                filterJobDTO.getPublishedDates(),
                filterJobDTO.getLocations(),
                filterJobDTO.getCompanies());
    }

    @GetMapping("/api/jobs/filter")
    @ResponseBody
    public ResponseEntity<List<Job>> getAllJobs(@RequestParam("job_fields") String jobFields, @RequestParam("published_dates") String publishedDates, @RequestParam("locations") String locations, @RequestParam("companies") String companies) throws Exception {
        FilterJob filterJob = new FilterJob(jobFields, publishedDates, locations, companies);
        return new ResponseEntity<>(jobService.getFilteredJobs(filterJob), HttpStatus.OK);
    }
}
