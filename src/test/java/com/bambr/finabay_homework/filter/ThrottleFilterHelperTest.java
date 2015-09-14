package com.bambr.finabay_homework.filter;

import com.bambr.finabay_homework.loan.application.util.GeoLocation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Daniil on 14.09.2015.
 */
public class ThrottleFilterHelperTest {
    ConcurrentHashMap<String, Long> requestPerTimeUnitPerCountry;
    GeoLocation geoLocation;
    HttpServletRequest request;
    @Before
    public void setUp() throws Exception {
        requestPerTimeUnitPerCountry = new ConcurrentHashMap<>();
        geoLocation = mock(GeoLocation.class);
        request = mock(HttpServletRequest.class);
    }

    @After
    public void tearDown() throws Exception {
        requestPerTimeUnitPerCountry = null;
    }

    /**
     * Checking functionality of applications-count-throttling-by-country-per-requests-per-second :)
     * @throws Exception
     */
    @Test
    public void testPrepareRejectionReasonIfAny() throws Exception {
        when(request.getRemoteAddr()).thenReturn("0:0:0:0:0:0:0:1");
        when(geoLocation.getCountry(any(HttpServletRequest.class))).thenReturn("timbuktu");
        StringBuffer buffer = new StringBuffer();
        //Simplest case of checking if we are passing filter check under "normal" condition
        ThrottleFilterHelper.prepareRejectionReasonIfAny(geoLocation, 2L, requestPerTimeUnitPerCountry, request, buffer);
        assertEquals("", buffer.toString());

        //Making sure we are hitting the limit
        ThrottleFilterHelper.prepareRejectionReasonIfAny(geoLocation, 1L, requestPerTimeUnitPerCountry, request, buffer);
        assertEquals(ThrottleFilterHelper.TOO_MANY_REQUEST_PER_COUNTRY,buffer.toString());
    }
}