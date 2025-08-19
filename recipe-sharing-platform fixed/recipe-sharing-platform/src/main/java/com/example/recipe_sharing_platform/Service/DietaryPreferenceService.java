package com.example.recipe_sharing_platform.Service;

import com.example.recipe_sharing_platform.Entity.DietaryPreference;
import com.example.recipe_sharing_platform.Request.DietaryPreferenceRequest;

import java.util.List;

public interface DietaryPreferenceService {
    DietaryPreference createDietary(DietaryPreferenceRequest dietaryPreferenceRequest);
    List<DietaryPreference> getDietary();
    DietaryPreference updateDietary(Long id, DietaryPreferenceRequest dietaryPreferenceRequest);
    void deleteDietary(Long id);
}
