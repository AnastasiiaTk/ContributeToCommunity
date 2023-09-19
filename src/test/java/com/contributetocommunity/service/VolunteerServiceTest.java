package com.contributetocommunity.service;

import com.contributetocommunity.VolunteerTestDataBuilder;
import com.contributetocommunity.bom.Job;
import com.contributetocommunity.bom.Volunteer;
import com.contributetocommunity.entity.VolunteerEntity;
import com.contributetocommunity.repository.VolunteerPagingRepository;
import com.contributetocommunity.repository.VolunteerRepository;
import com.contributetocommunity.service.impl.VolunteerServiceImpl;
import com.contributetocommunity.translator.VolunteerTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class VolunteerServiceTest {

    @InjectMocks
    private VolunteerServiceImpl volunteerService;

    @Mock
    private VolunteerRepository volunteerRepository;

    @Mock
    private VolunteerPagingRepository volunteerPagingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(volunteerService, "volunteerTranslator", new VolunteerTranslator());
    }

    @Test
    public void getVolunteersByJobIdTest() {
        when(volunteerRepository.findAllByJobId(anyInt())).thenReturn(VolunteerTestDataBuilder.buildVolunteerEntities());

        List<Volunteer> result = volunteerService.getVolunteersByJobId(1);

        verify(volunteerRepository, times(1)).findAllByJobId(anyInt());

        assertNotNull(result);
        assertFalse(CollectionUtils.isEmpty(result));
        assertTrue(result.size() == 2);
        result.stream().forEach(v -> assertFalse(CollectionUtils.isEmpty(v.getJobs())));
        List<Job> volunteer1Jobs = result.get(0).getJobs();

        assertNotNull(volunteer1Jobs.stream().filter(v -> 1 == v.getId()).findAny().orElse(null));
        List<Job> volunteer2Jobs = result.get(1).getJobs();
        assertNotNull(volunteer2Jobs);
        assertNotNull(volunteer2Jobs.stream().filter(v -> 1 == v.getId()).findAny().orElse(null));
    }

    @Test
    public void getEmptyVolunteersByJobIdTest() {
        when(volunteerRepository.findAllByJobId(anyInt())).thenReturn(new ArrayList<>());

        List<Volunteer> result = volunteerService.getVolunteersByJobId(1);

        verify(volunteerRepository, times(1)).findAllByJobId(anyInt());

        assertNotNull(result);
        assertTrue(CollectionUtils.isEmpty(result));
    }

    @Test
    public void findVolunteersTest() {
        Page<VolunteerEntity> pageVolunteerEntities = new PageImpl<>(VolunteerTestDataBuilder.buildVolunteerEntities());
        when(volunteerPagingRepository.findAll((Pageable) any())).thenReturn(pageVolunteerEntities);

        Page<Volunteer> result = volunteerService.findVolunteers(1, 2);

        verify(volunteerPagingRepository, times(1)).findAll((Pageable) any());

        assertNotNull(result);
        assertFalse(CollectionUtils.isEmpty(result.getContent()));
        assertTrue(result.getContent().size() == 2);
        result.stream().forEach(v -> assertFalse(CollectionUtils.isEmpty(v.getJobs())));
        List<Job> volunteer1Jobs = result.getContent().get(0).getJobs();

        assertNotNull(volunteer1Jobs.stream().filter(v -> 1 == v.getId()).findAny().orElse(null));
        List<Job> volunteer2Jobs = result.getContent().get(1).getJobs();
        assertNotNull(volunteer2Jobs);
        assertNotNull(volunteer2Jobs.stream().filter(v -> 1 == v.getId()).findAny().orElse(null));
    }

    @Test
    public void findButGetEmptyVolunteersTest() {
        Page<VolunteerEntity> pageVolunteerEntities = new PageImpl<>(new ArrayList<>());
        when(volunteerPagingRepository.findAll((Pageable) any())).thenReturn(pageVolunteerEntities);

        Page<Volunteer> result = volunteerService.findVolunteers(100, 2);

        verify(volunteerPagingRepository, times(1)).findAll((Pageable) any());

        assertNotNull(result);
        assertTrue(CollectionUtils.isEmpty(result.getContent()));
    }


}
