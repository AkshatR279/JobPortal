package com.akshatr.jobportal.model.entity;

import com.akshatr.jobportal.model.enums.UserRole;
import com.akshatr.jobportal.model.enums.UserStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tbl_User")
@Getter
@Setter
public class User extends BaseEntity {
    private String password;
    private UserRole role;
    private UserStatus status;

    @OneToMany(mappedBy = "user")
    private List<JobApplication> jobApplications;

    @OneToMany(mappedBy = "user")
    private List<Job> jobsPosted;

    private String token;
}
