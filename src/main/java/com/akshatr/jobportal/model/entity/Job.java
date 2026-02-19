package com.akshatr.jobportal.model.entity;

import com.akshatr.jobportal.model.enums.JobStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    @ManyToOne
    private Company company;

    private JobStatus status;
}
