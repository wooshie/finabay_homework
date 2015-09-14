package com.bambr.finabay_homework.loan.application.controller;

import com.bambr.finabay_homework.loan.application.data.LoanApplicationData;
import com.bambr.finabay_homework.loan.application.data.LoanApplicationResponse;
import com.bambr.finabay_homework.loan.application.repository.LoanApplicationDataRepository;
import com.bambr.finabay_homework.loan.application.util.GeoLocation;
import com.bambr.finabay_homework.loan.user.data.UserData;
import com.bambr.finabay_homework.loan.user.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniil on 10.09.2015.
 * Provides controllable way to functionality exposed via REST(like) way
 */
@Controller
public class LoanApllicationDataController {
    private static final Logger logger = LoggerFactory.getLogger(LoanApllicationDataController.class);

    @Autowired
    Validator loanApplicationDataValidator;

    @Autowired
    LoanApplicationDataRepository loanApplicationDataRepository;

    @Autowired
    UserDataRepository userDataRepository;

    @Autowired
    GeoLocation geoLocation;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(loanApplicationDataValidator);
    }

    @RequestMapping(value = "/approved", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LoanApplicationData> getApprovedLoanApplications() {
        return loanApplicationDataRepository.findByApprovedTrue();
    }

    @RequestMapping(value = "/approved/{personalId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<LoanApplicationData> getApprovedLoanApplicationsByUser(@PathVariable String personalId) {
        return loanApplicationDataRepository.findByApprovedTrueAndPersonalId(personalId);
    }


    @RequestMapping(value = "/apply", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public LoanApplicationResponse apply(@Valid LoanApplicationData data, BindingResult errors, HttpServletRequest request) {
        String country = geoLocation.getCountry(request);
        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setPassed(!errors.hasErrors());
        List<String> errorsList = new ArrayList<>();
        if (errors.hasErrors()) {
            //Put error information into response
            errors.getAllErrors().forEach(error -> {errorsList.add(error.getDefaultMessage());logger.error(error.getDefaultMessage());});
            response.setErrors(errorsList);
        } else {
            data.setApproved(false); //Just in case: "trust no-one, even yourself.. ..I only wanted to fart" :)
            loanApplicationDataRepository.save(data);
            UserData userData = userDataRepository.findByPersonalId(data.getPersonalId());
            if(userData == null) {
                //Let's create user
                userData = new UserData();
                userData.setName(data.getName());
                userData.setSurname(data.getSurname());
                userData.setPersonalId(data.getPersonalId());
                userDataRepository.save(userData);
            }
        }
        return response;
    }

}
