package com.akshatr.jobportal.model.entity;

import com.akshatr.jobportal.model.enums.JobStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_Jobs")
@Getter
@Setter
public class Job extends BaseEntity {
    private String identifier;
    private Long salaryMin;
    private Long salaryMax;
    private int experienceMin;
    private int experienceMax;
    private Date postedOn;
    private Date expiredOn;
    private JobStatus status;

    @ManyToOne
    private Company company;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "job")
    private List<JobApplication> jobApplications;
}
