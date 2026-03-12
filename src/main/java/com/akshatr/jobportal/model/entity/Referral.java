package com.akshatr.jobportal.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_Referrals")
@Getter
@Setter
public class Referral extends BaseEntity {
    private String message;

    private Long userId;

    @OneToOne
    private JobApplication referralFor;
}
