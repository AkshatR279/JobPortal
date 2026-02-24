package com.akshatr.jobportal.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_Referrals")
@Getter
@Setter
public class Referral extends BaseEntity {
    private String message;

    @OneToMany
    private User user;

    @OneToOne
    private JobApplication referralFor;
}
