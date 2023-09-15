package com.contributetocommunity.repository;

import com.contributetocommunity.entity.VolunteerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VolunteerRepository extends JpaRepository<VolunteerEntity, Integer> {

    @Query(value = "SELECT * FROM volunteers v INNER JOIN jobs_volunteers jv ON v.id = jv.volunteer_id " +
            "WHERE jv.job_id = :jobId ORDER BY v.first_name, v.last_name", nativeQuery = true)
    List<VolunteerEntity> findAllByJobId(@Param("jobId") Integer id);
}
