package com.contributetocommunity.service.impl;

import com.contributetocommunity.repository.VolunteerPagingRepository;
import com.contributetocommunity.repository.VolunteerRepository;
import com.contributetocommunity.service.VolunteerService;
import com.contributetocommunity.translator.VolunteerTranslator;
import com.contributetocommunity.bom.Volunteer;
import com.contributetocommunity.entity.VolunteerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("volunteerService")
public class VolunteerServiceImpl implements VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerPagingRepository volunteerPagingRepository;

    @Autowired
    private VolunteerTranslator volunteerTranslator;

    @Override
    public List<Volunteer> getVolunteersByJobId(Integer jobId) {
        List<VolunteerEntity> volunteerEntities = volunteerRepository.findAllByJobId(jobId);
        return volunteerTranslator.volunteerListToBom(volunteerEntities);
    }

    @Override
    public Page<Volunteer> findVolunteers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("firstName", "lastName"));
        Page<VolunteerEntity> volunteerEntities = volunteerPagingRepository.findAll(pageable);
        List<Volunteer> volunteers = volunteerEntities.get()
                .map(entity -> volunteerTranslator.volunteerToBom(entity))
                .collect(Collectors.toList());
        return new PageImpl<>(volunteers);
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void loadInitialData() {

    }
}
