package com.contributetocommunity.repository;

import com.contributetocommunity.entity.VolunteerEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VolunteerPagingRepository extends PagingAndSortingRepository<VolunteerEntity, Integer>, JpaSpecificationExecutor<VolunteerEntity> {

}
