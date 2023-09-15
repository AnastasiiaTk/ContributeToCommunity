package com.contributetocommunity.service;

import com.contributetocommunity.bom.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface VolunteerService {

    List<Volunteer> getVolunteersByJobId(Integer jobId);

    Page<Volunteer> findVolunteers(int page, int size);

}
