package com.bambr.finabay_homework.loan.application.validation;

import com.bambr.finabay_homework.loan.application.data.LoanApplicationData;
import com.bambr.finabay_homework.loan.user.data.UserData;
import com.bambr.finabay_homework.loan.user.repository.UserDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Daniil on 10.09.2015.
 */
@Component
public class LoanApplicationDataValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(LoanApplicationDataValidator.class);

    @Autowired
    UserDataRepository userDataRepository;



    @Override
    public boolean supports(Class<?> clazz) {
        logger.info("supports method called for class: "+clazz.getName());
        return clazz.equals(LoanApplicationData.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.info("validate method called for: "+target);
        LoanApplicationData data = (LoanApplicationData) target;
        UserData userData = userDataRepository.findByPersonalId(data.getPersonalId());
        if(userData != null && userData.getLocked()) {
            errors.reject("666", "This user is blocked!!!");
        }
        if(data.getLoanAmount() != null) {
            if (data.getLoanAmount() <= 0) {
                errors.reject("100", "Amount cannot be negative or zero!!!");
            }
        }
    }
}
