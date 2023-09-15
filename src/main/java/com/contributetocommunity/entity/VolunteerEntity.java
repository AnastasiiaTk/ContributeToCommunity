package com.contributetocommunity.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "volunteers")
public class VolunteerEntity extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany
    @JoinTable(name="jobs_volunteers",
            joinColumns={@JoinColumn(name="volunteer_id", referencedColumnName = "id")},
            inverseJoinColumns={@JoinColumn(name="job_id", referencedColumnName = "id")})
    private List<JobEntity> jobEntities;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<JobEntity> getJobs() {
        return jobEntities;
    }

    public void setJobs(List<JobEntity> jobEntities) {
        this.jobEntities = jobEntities;
    }
}
