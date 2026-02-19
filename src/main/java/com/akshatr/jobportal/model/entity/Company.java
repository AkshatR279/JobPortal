package com.akshatr.jobportal.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tbl_Company")
@Getter
@Setter
public class Company extends BaseEntity{

    @OneToMany(mappedBy = "company")
    private List<Job> jobs;
}
