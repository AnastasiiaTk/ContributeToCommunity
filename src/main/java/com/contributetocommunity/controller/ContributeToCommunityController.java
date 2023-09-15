package com.contributetocommunity.controller;

import com.contributetocommunity.repository.VolunteerRepository;
import com.contributetocommunity.service.VolunteerService;
import com.contributetocommunity.bom.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContributeToCommunityController {

    @Autowired
    private VolunteerService volunteerService;

    @GetMapping("/api/volunteers/{jobId}")
    public @ResponseBody
    List<Volunteer> getVolunteers(@PathVariable("jobId") Integer jobId) {
        return volunteerService.getVolunteersByJobId(jobId);
    }

    @GetMapping("/api/volunteersPage")
    public @ResponseBody
    Page<Volunteer> getVolunteersPage(@RequestParam("pageIndex") Integer pageIndex, @RequestParam("pageSize") Integer pageSize) {
        return volunteerService.findVolunteers(pageIndex, pageSize);
    }
}
