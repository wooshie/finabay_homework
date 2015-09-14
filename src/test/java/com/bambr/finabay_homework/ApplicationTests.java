package com.bambr.finabay_homework;

import com.bambr.finabay_homework.loan.application.validation.LoanApplicationDataValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {
    @Autowired
    LoanApplicationDataValidator validator;

    /**
     * Just to check if context is loading fine :)
     */
	@Test
	public void contextLoads() {
        assertNotNull(validator);
	}

}
