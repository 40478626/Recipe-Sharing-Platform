package com.example.recipe_sharing_platform.Service;

import java.util.List;

import com.example.recipe_sharing_platform.Entity.Rate;
import com.example.recipe_sharing_platform.Request.RateRequest;

public interface RateService {
    Rate createRate(RateRequest rateRequest);
    // Rate getRateByRecipeId(Long id);
    List<Rate> getRatesByRecipeId(Long id);
    Rate getRateByUserId(String id);
    Rate updateRate(Long id, RateRequest rateRequest);
    void deleteRate(Long id);
}
