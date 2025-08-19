    package com.example.recipe_sharing_platform.Controller;

    import com.example.recipe_sharing_platform.Entity.Rate;
    import com.example.recipe_sharing_platform.Request.RateRequest;
    import com.example.recipe_sharing_platform.Response.Basic;
    import com.example.recipe_sharing_platform.Response.ResponseFactory;
    import com.example.recipe_sharing_platform.Service.RateService;
    import com.example.recipe_sharing_platform.Utils.Translator;
    import com.example.recipe_sharing_platform.constant.ErrorCode;
    import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @Slf4j
    @RestController
    @RequestMapping("/api")
    @CrossOrigin(origins = "*")
    public class RateController {
        @Autowired
        private ResponseFactory responseFactory;

        @Autowired
        private RateService rateService;

        @PostMapping(value = "create-rate")
        ResponseEntity<Basic> createRate(@RequestBody RateRequest rateRequest) {
            try {
                // Implement logic to create a new rate
                Rate rate = rateService.createRate(rateRequest);
                return responseFactory.buildSuccess(HttpStatus.OK, rate, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
            } catch (Exception e) {
                log.error("Error creating new rate", e);
                return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
            }
        }

        // @PostMapping(value = "get-rate-by-recipe/{id}")
        // ResponseEntity<Basic> getRateByRecipeId(@PathVariable Long id) {
        //     try {
        //         // Implement logic to get a rate by recipe id
        //         Rate rate = rateService.getRateByRecipeId(id);
        //         return responseFactory.buildSuccess(HttpStatus.OK, rate, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
        //     } catch (Exception e) {
        //         log.error("get rate error", e);
        //         return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
        //     }
        // }

        @PostMapping(value = "get-rate-by-recipe/{id}")
        ResponseEntity<Basic> getRateByRecipeId(@PathVariable Long id) {
            try {
                List<Rate> rates = rateService.getRatesByRecipeId(id);
                return responseFactory.buildSuccess(HttpStatus.OK, rates, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
            } catch (Exception e) {
                log.error("get rate error", e);
                return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
            }
        }

        @PostMapping(value = "get-rate-by-user/{id}")
        ResponseEntity<Basic> getRateByUserId(@PathVariable String id) {
            try {
                // Implement logic to get a rate by user id
                Rate rate = rateService.getRateByUserId(id);
                return responseFactory.buildSuccess(HttpStatus.OK, rate, ErrorCode.GET_INFO_SUCCESS, Translator.toLocale(ErrorCode.GET_INFO_SUCCESS));
            } catch (Exception e) {
                log.error("get rate error", e);
                return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
            }
        }

        @PostMapping(value = "update-rate/{id}")
        ResponseEntity<Basic> updateRate(@PathVariable Long id, @RequestBody RateRequest rateRequest) {
            try {
                // Implement logic to update a rate
                Rate rate = rateService.updateRate(id, rateRequest);
                return responseFactory.buildSuccess(HttpStatus.OK, rate, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
            } catch (Exception e) {
                log.error("update rate error", e);
                return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
            }
        }

        @PostMapping(value = "delete-rate/{id}")
        ResponseEntity<Basic> deleteRate(@PathVariable Long id) {
            try {
                // Implement logic to delete a rate
                rateService.deleteRate(id);
                return responseFactory.buildSuccess(HttpStatus.OK, null, ErrorCode.SUCCESS, Translator.toLocale(ErrorCode.SUCCESS));
            } catch (Exception e) {
                log.error("delete rate error", e);
                return responseFactory.buildError(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.HAVE_SOME_PROBLEM, Translator.toLocale(ErrorCode.HAVE_SOME_PROBLEM));
            }
        }
    }
