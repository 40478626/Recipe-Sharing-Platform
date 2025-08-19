package com.example.recipe_sharing_platform.Controller;

import com.example.recipe_sharing_platform.Entity.DietaryPreference;
import com.example.recipe_sharing_platform.Request.DietaryPreferenceRequest;
import com.example.recipe_sharing_platform.Response.Basic;
import com.example.recipe_sharing_platform.Response.ResponseFactory;
import com.example.recipe_sharing_platform.Service.DietaryPreferenceService;
import com.example.recipe_sharing_platform.Utils.Translator;
import com.example.recipe_sharing_platform.constant.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DietaryPreferenceController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private DietaryPreferenceService dietaryPreferenceService;

    @PostMapping(value = "create-dietary")
    ResponseEntity<Basic> createDietary(@RequestBody DietaryPreferenceRequest dietaryPreferenceRequest) {
        try {
            // Implement logic to create a new dietary preference
            DietaryPreference dietaryPreference = dietaryPreferenceService.createDietary(dietaryPreferenceRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, dietaryPreference, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("Error creating dietary preference", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "get-dietary")
    ResponseEntity<Basic> getDietary() {
        try {
            // Implement logic to get a dietary preference
            List<DietaryPreference> dietaryPreferenceList = dietaryPreferenceService.getDietary();
            return responseFactory.buildSuccess(HttpStatus.OK, dietaryPreferenceList, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
        } catch (Exception e) {
            log.error("Error getting dietary preference", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "update-dietary/{id}")
    ResponseEntity<Basic> updateDietary(@PathVariable("id") Long id,
                                        @RequestBody DietaryPreferenceRequest dietaryPreferenceRequest) {
        try {
            // Implement logic to update a dietary preference
            DietaryPreference dietaryPreference = dietaryPreferenceService.updateDietary(id, dietaryPreferenceRequest);
            return responseFactory.buildSuccess(HttpStatus.OK, dietaryPreference, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("Error updating dietary preference", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }

    @PostMapping(value = "delete-dietary/{id}")
    ResponseEntity<Basic> deleteDietary(@PathVariable("id") Long id) {
        try {
            // Implement logic to delete a dietary preference
            dietaryPreferenceService.deleteDietary(id);
            return responseFactory.buildSuccess(HttpStatus.OK, "Deleted!", ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
        } catch (Exception e) {
            log.error("Error deleting dietary preference", e);
            return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        }
    }
}
