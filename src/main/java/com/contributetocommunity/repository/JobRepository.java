package com.contributetocommunity.repository;

import com.contributetocommunity.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<JobEntity, Integer> {

}
