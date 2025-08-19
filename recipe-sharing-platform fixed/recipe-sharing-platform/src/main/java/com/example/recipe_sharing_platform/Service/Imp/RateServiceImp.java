package com.example.recipe_sharing_platform.Service.Imp;

import com.example.recipe_sharing_platform.Entity.Rate;
import com.example.recipe_sharing_platform.Repository.RateRepo;
import com.example.recipe_sharing_platform.Request.RateRequest;
import com.example.recipe_sharing_platform.Service.RateService;
import com.example.recipe_sharing_platform.config.SecurityConfig;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateServiceImp implements RateService {

    @Autowired
    private RateRepo rateRepo;
    @Autowired
    private SecurityConfig securityConfig;  // SecurityConfig provides the current user's ID.

    @Override
    public Rate createRate(RateRequest rateRequest) {
        Rate rate = new Rate();
        rate.setRecipeId(rateRequest.getRecipeId());
        rate.setUserId(securityConfig.getUserId());
        rate.setRate(rateRequest.getRate());
        rateRepo.save(rate);
        return rate;
    }

    // @Override
    // public Rate getRateByRecipeId(Long id) {
    //     Rate rate = rateRepo.findByRecipeId(id);
    //     if (rate == null) {
    //         rate = new Rate();
    //         rate.setRate(0);
    //     }
    //     return rate;
    // }
    @Override
    public List<Rate> getRatesByRecipeId(Long id) {
    List<Rate> rates = rateRepo.findAllByRecipeId(id);
    return rates.isEmpty() ? new ArrayList<>() : rates;
}


    @Override
    public Rate getRateByUserId(String id) {
        Rate rate = rateRepo.findByUserId(id);
        if (rate == null) {
            rate = new Rate();
            rate.setRate(0);
        }
        return rate;
    }

    @Override
    public Rate updateRate(Long id, RateRequest rateRequest) {
        Rate rate = rateRepo.findById(id).orElse(null);
        if (rate != null) {
            rate.setRecipeId(rateRequest.getRecipeId());
            rate.setUserId(securityConfig.getUserId());
            rate.setRate(rateRequest.getRate());
            rateRepo.save(rate);
            return rate;
        } else {
            throw new IllegalStateException("Rate Not Found");
        }
    }

    @Override
    public void deleteRate(Long id) {
        Rate rate = rateRepo.findById(id).orElse(null);
        if (rate != null) {
            rateRepo.deleteById(id);
        } else {
            throw new IllegalStateException("Rate Not Found");
        }
    }
}
