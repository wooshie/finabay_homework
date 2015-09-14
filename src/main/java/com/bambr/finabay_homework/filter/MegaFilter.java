package com.bambr.finabay_homework.filter;

import com.bambr.finabay_homework.errors.ErrorMessageCreator;
import com.bambr.finabay_homework.loan.application.util.GeoLocation;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Daniil on 10.09.2015.
 * This filter is currently used for invocation of throttling check/enforce mechanism, some other request pre-processings can be added here as well
 */
@WebFilter("ThrottleFilter")
@Component
public class MegaFilter implements Filter {
    ConcurrentHashMap<String, Long> requestPerTimeUnitPerCountry = new ConcurrentHashMap<>();
    @Autowired
    GeoLocation geoLocation;
    @Value("${throttling.limit}")
    Long limit;
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
        this.context.log("ThrottleFilter created");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        StringBuffer rejectionReason = new StringBuffer();
        String country = ThrottleFilterHelper.prepareRejectionReasonIfAny(geoLocation, limit, requestPerTimeUnitPerCountry, (HttpServletRequest) request, rejectionReason);
        if ((rejectionReason.length() > 0)) {
            String errorMessageJson = ErrorMessageCreator.createErrorJson(new HashMap<String, String>() {{
                put("reason", rejectionReason.toString());
                put("remoteAddr", request.getRemoteAddr());
                put("country", country);
            }}).toString();

            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(errorMessageJson);
        } else {
            chain.doFilter(request, response);
        }
    }


    @Override
    public void destroy() {

    }
}
