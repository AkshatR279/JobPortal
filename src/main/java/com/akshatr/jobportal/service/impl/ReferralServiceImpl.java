package com.akshatr.jobportal.service.impl;

import com.akshatr.jobportal.model.dto.referral.ReferralRequestDto;
import com.akshatr.jobportal.model.entity.JobApplication;
import com.akshatr.jobportal.model.entity.Referral;
import com.akshatr.jobportal.model.entity.User;
import com.akshatr.jobportal.repository.JobApplicationRepository;
import com.akshatr.jobportal.repository.ReferralRepository;
import com.akshatr.jobportal.repository.UserRepository;
import com.akshatr.jobportal.service.ReferralService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReferralServiceImpl implements ReferralService {
    private final ReferralRepository referralRepository;
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;

    @Override
    public Referral refer(ReferralRequestDto request) {
        Optional<User> referrer = userRepository.findById(request.getUserId());
        if(referrer.isEmpty()){
            throw new RuntimeException("Invalid user.");
        }

        Optional<JobApplication> application = jobApplicationRepository.findById(request.getJobApplicationId());
        if(application.isEmpty()){
            throw new RuntimeException("Invalid job application.");
        }

        Referral referral = new Referral();
        Optional<Referral> existingReferral = referralRepository.findById(request.getId());
        if(existingReferral.isPresent()){
            referral = existingReferral.get();;
        }

        referral.setReferralFor(application.get());
        referral.setUser(referrer.get());
        referral.setMessage(request.getMessage());
        referral.setName("Employee Referral");

        return referralRepository.save(referral);
    }
}
