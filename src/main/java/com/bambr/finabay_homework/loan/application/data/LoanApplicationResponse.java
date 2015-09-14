package com.bambr.finabay_homework.loan.application.data;

import java.util.List;

/**
 * Created by Daniil on 10.09.2015.
 */
public class LoanApplicationResponse {
    private boolean passed;
    private List<String> errors;

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isPassed() {
        return passed;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
