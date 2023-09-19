package com.contributetocommunity.service;

import com.contributetocommunity.ContributeToCommunityApplicationTests;
import com.contributetocommunity.bom.Volunteer;
import com.contributetocommunity.service.VolunteerService;
import com.contributetocommunity.translator.VolunteerTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InitialDataServiceTest extends ContributeToCommunityApplicationTests {

    @Autowired
    private VolunteerService volunteerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(volunteerService, "volunteerTranslator", new VolunteerTranslator());
    }


    @Test
    public void loadInitialDataTest() {
        List<Volunteer> result = volunteerService.getVolunteersByJobId(1);
        assertFalse(CollectionUtils.isEmpty(result));
        assertTrue(result.size() == 9);
        result.stream().forEach(v -> assertTrue(
                !CollectionUtils.isEmpty(
                        v.getJobs().stream()
                                .filter(job -> job.getId() == 1)
                                .collect(Collectors.toList())))
        );
    }
}
