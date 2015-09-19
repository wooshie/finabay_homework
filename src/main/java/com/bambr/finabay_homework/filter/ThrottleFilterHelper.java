package com.bambr.finabay_homework.filter;

import com.bambr.finabay_homework.loan.application.util.GeoLocation;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Daniil on 14.09.2015.
 */
public class ThrottleFilterHelper {
    public static final String TOO_MANY_REQUEST_PER_COUNTRY = "Too many requests from your country, try again later!";

    public static String prepareRejectionReasonIfAny(GeoLocation geoLocation, Long limit, ConcurrentHashMap<String, Long> requestPerTimeUnitPerCountry, HttpServletRequest request, StringBuffer rejectionReason) {
        String country = geoLocation.getCountry(request);
        String checkupKey = country + "_" + (new Date().getTime() / 1000);
        requestPerTimeUnitPerCountry.computeIfAbsent(checkupKey, key -> {
            requestPerTimeUnitPerCountry.clear();
            return 1L;
        });
        requestPerTimeUnitPerCountry.computeIfPresent(checkupKey, (key, value) -> ++value);
        if (requestPerTimeUnitPerCountry.get(checkupKey) >= limit) {
            rejectionReason.append(TOO_MANY_REQUEST_PER_COUNTRY);
        }
        return country;
    }
}
