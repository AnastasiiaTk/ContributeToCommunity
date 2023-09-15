package com.contributetocommunity.translator;

import com.contributetocommunity.bom.Job;
import com.contributetocommunity.bom.Volunteer;
import com.contributetocommunity.entity.JobEntity;
import com.contributetocommunity.entity.VolunteerEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VolunteerTranslator {

    public List<Volunteer> volunteerListToBom(List<VolunteerEntity> source) {
        return source.stream()
                .map(entity -> volunteerToBom(entity))
                .collect(Collectors.toList());
    }

    public Volunteer volunteerToBom(VolunteerEntity source) {
        return Volunteer.builder()
                .id(source.getId())
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .jobs(source.getJobEntities().stream()
                        .map(job -> jobToBom(job))
                        .collect(Collectors.toList()))
                .build();
    }

    public Job jobToBom(JobEntity source) {
        return Job.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .build();
    }
}
