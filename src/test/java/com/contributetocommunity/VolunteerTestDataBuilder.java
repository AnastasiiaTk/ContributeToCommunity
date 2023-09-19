package com.contributetocommunity;

import com.contributetocommunity.bom.Job;
import com.contributetocommunity.bom.Volunteer;
import com.contributetocommunity.entity.JobEntity;
import com.contributetocommunity.entity.VolunteerEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.util.Arrays;
import java.util.List;

public class VolunteerTestDataBuilder {

    public static List<VolunteerEntity> buildVolunteerEntities() {
        return Arrays.asList(VolunteerEntity.builder()
                        .id(1)
                        .firstName("firstName1")
                        .lastName("lastName1")
                        .jobEntities(Arrays.asList(
                                JobEntity.builder()
                                        .id(1)
                                        .name("job1")
                                        .description("description test 1")
                                        .build(),
                                JobEntity.builder()
                                        .id(2)
                                        .name("job2")
                                        .description("description test 2")
                                        .build(),
                                JobEntity.builder()
                                        .id(3)
                                        .name("job3")
                                        .description("description test 3")
                                        .build()
                        )).build(),
                VolunteerEntity.builder()
                        .id(2)
                        .firstName("firstName2")
                        .lastName("lastName2")
                        .jobEntities(Arrays.asList(
                                JobEntity.builder()
                                        .id(1)
                                        .name("job1")
                                        .description("description test 1")
                                        .build(),
                                JobEntity.builder()
                                        .id(4)
                                        .name("job4")
                                        .description("description test 4")
                                        .build()
                        )).build());
    }

    public static List<Volunteer> buildVolunteers() {
        return Arrays.asList(Volunteer.builder()
                .id(1)
                .firstName("firstName1")
                .lastName("lastName1")
                .jobs(Arrays.asList(
                        Job.builder()
                                .id(1)
                                .name("job1")
                                .build()
                )).build());
    }


    public static String getVolunteersAsJson() throws JsonProcessingException {
        List<Volunteer> volunteers = buildVolunteers();
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(volunteers)
                .replace("\n", "")
                .replace("\r", "")
                .replaceAll(" ", "");
    }


}
