package com.bambr.finabay_homework.loan.application.util;

import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Daniil on 14.09.2015.
 */
@Component
public class GeoLocation {
    @Autowired
    WebServiceClient geoIpClient;

    Logger logger = LoggerFactory.getLogger(GeoLocation.class);

    public String getCountry(HttpServletRequest request) {
        String country = "lv";
        String remoteAddressString = request.getRemoteAddr();
        InetAddress remoteAddress = null;
        // TODO: Check header for proxy things and try to get X-forwarded-for or something like this
        try {
            if (remoteAddressString.split(":").length == 8) {
                //IPV6
                remoteAddress = Inet6Address.getByName(remoteAddressString);
            } else {
                //IPV4
                remoteAddress = InetAddress.getByName(remoteAddressString);
            }
        } catch (UnknownHostException e) {
            //oh well, nothing we should be doing about it yet
            logger.warn(e.getMessage());
        }
        if (remoteAddress != null) {
            CountryResponse countryResponse = null;
            try {
                countryResponse = geoIpClient.country(remoteAddress);
            } catch (IOException | GeoIp2Exception e) {
                //shit happens, we live on :)
                logger.warn(e.getMessage());
            }
            if (countryResponse != null) {
                country = countryResponse.getCountry().getIsoCode();
            }
        }
        return country;
    }
}
