package com.example.recipe_sharing_platform.Service.Imp;

import com.example.recipe_sharing_platform.Entity.DietaryPreference;
import com.example.recipe_sharing_platform.Repository.DietaryPreferenceRepo;
import com.example.recipe_sharing_platform.Request.DietaryPreferenceRequest;
import com.example.recipe_sharing_platform.Service.DietaryPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DietaryPreferenceServiceImp implements DietaryPreferenceService {

    @Autowired
    private DietaryPreferenceRepo dietaryPreferenceRepo;

    @Override
    public DietaryPreference createDietary(DietaryPreferenceRequest dietaryPreferenceRequest) {
        DietaryPreference dietaryPreference = new DietaryPreference();
        dietaryPreference.setName(dietaryPreferenceRequest.getName());
        dietaryPreferenceRepo.save(dietaryPreference);
        return dietaryPreference;
    }

    @Override
    public List<DietaryPreference> getDietary() {
        List<DietaryPreference> dietaryPreferenceList = dietaryPreferenceRepo.findAll();
        return dietaryPreferenceList;
    }

    @Override
    public DietaryPreference updateDietary(Long id, DietaryPreferenceRequest dietaryPreferenceRequest) {
        DietaryPreference dietaryPreference = dietaryPreferenceRepo.findById(id).orElse(null);
        if (dietaryPreference!=null) {
            dietaryPreference.setName(dietaryPreferenceRequest.getName());
            dietaryPreferenceRepo.save(dietaryPreference);
            return dietaryPreference;
        } else {
            throw new IllegalStateException("Dietary Preference Not Found");
        }
    }

    @Override
    public void deleteDietary(Long id) {
        DietaryPreference dietaryPreference = dietaryPreferenceRepo.findById(id).orElse(null);
        if (dietaryPreference != null) {
            dietaryPreferenceRepo.deleteById(id);
        } else {
            throw new IllegalStateException("Dietary Preference Not Found");
        }
    }
}
