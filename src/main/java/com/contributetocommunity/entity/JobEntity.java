package com.contributetocommunity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "job_name", nullable = false)
    private String name;

    @Column(name = "job_desc", columnDefinition = "TEXT")
    private String description;

}
