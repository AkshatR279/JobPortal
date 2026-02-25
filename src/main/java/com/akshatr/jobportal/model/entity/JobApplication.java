package com.akshatr.jobportal.model.entity;

import com.akshatr.jobportal.model.enums.JobApplicationStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "tbl_JobApplications")
@Getter
@Setter
public class JobApplication extends BaseEntity{
    @ManyToOne
    private Job job;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "referralFor")
    private Referral referral;

    private JobApplicationStatus status;
    private Date submittedOn;
}
