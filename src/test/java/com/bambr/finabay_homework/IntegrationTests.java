package com.bambr.finabay_homework;

import com.bambr.finabay_homework.loan.user.data.UserData;
import com.bambr.finabay_homework.loan.user.repository.UserDataRepository;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import net.sf.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.with;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by Daniil on 14.09.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class IntegrationTests {
    @Value("${local.server.port}")
    int port;

    @Autowired
    UserDataRepository userDataRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    /**
     * Checking case when all the parameters are missing
     * TODO: Add test methods for different combinations of missing parameters?
     */
    @Test
    public void testEmptyApply() {
        with().parameters("", "").expect().body("passed", equalTo(false), "errors.size()", is(5)).when().post("/apply");
        //No additional user created, we have one from fixture sql
        assertEquals(1, userDataRepository.count());
    }

    /**
     * Checking if valid loan application passes through, user which didn't exist before should be added
     * TODO: Add test method for case when loan application is valid and user is already existing
     */
    @Test
    public void testValidApply() {
        with().parameters("loanAmount", "10", "term", "12 month", "name", "bob", "surname", "smith", "personalId", "wootw00t").expect().body("passed", equalTo(true)).when().post("/apply");
        //Additional user should be insterted, we have one from fixture sql
        assertEquals(2, userDataRepository.count());
        UserData insertedUser = userDataRepository.findByPersonalId("wootw00t");
        assertNotNull(insertedUser);
        //Custom clean up
        userDataRepository.delete(insertedUser);
    }

    /**
     * Check if otherwise valid loan application gets rejected because the user is blocked
     */
    @Test
    public void testValidApplyWithBlockedUser() {
        with().parameters("loanAmount", "10", "term", "12 month", "name", "kung-fu", "surname", "panda", "personalId", "kung-fu-666-panda").expect().body("passed", equalTo(false), "errors.size()", is(1)).when().post("/apply");
        //No additional user created, we have one from fixture sql
        assertEquals(1, userDataRepository.count());
    }

    /**
     * Check if approved loan applications list is of needed size (sort of basic check, which can be extended in order to check whole list content)
     */
    @Test
    public void testApproved() {
        String responseBody = requestGetAndReturnBodyAsString("/approved");
        JSONArray jsonArray = JSONArray.fromObject(responseBody);
        assertEquals(3, jsonArray.size());
    }

    private String requestGetAndReturnBodyAsString(String path) {
        Response response = get(path).andReturn();
        return new String(response.getBody().asByteArray());
    }

    /**
     * Check if approved loan applications list per user is of needed size (sort of basic check, which can be extended in order to check whole list content)
     */
    @Test
    public void testApprovedByUser() {
        String responseBody = requestGetAndReturnBodyAsString("/approved/za-200-zu");
        JSONArray jsonArray = JSONArray.fromObject(responseBody);
        assertEquals(1, jsonArray.size());
        responseBody = requestGetAndReturnBodyAsString("/approved/pam-300-sam");
        jsonArray = JSONArray.fromObject(responseBody);
        assertEquals(2, jsonArray.size());
    }
}
