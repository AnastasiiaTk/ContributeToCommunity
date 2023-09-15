package com.contributetocommunity.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "volunteers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany
    @JoinTable(name="jobs_volunteers",
            joinColumns={@JoinColumn(name="volunteer_id", referencedColumnName = "id")},
            inverseJoinColumns={@JoinColumn(name="job_id", referencedColumnName = "id")})
    private List<JobEntity> jobEntities;

}
