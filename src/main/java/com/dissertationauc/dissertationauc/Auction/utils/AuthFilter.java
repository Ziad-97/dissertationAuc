package com.dissertationauc.dissertationauc.Auction.utils;

import com.dissertationauc.dissertationauc.Auction.data.UserLifeCycleData;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthFilter implements Filter {
    private final JWT jwtUtil;
    private final JWT jwt;

    public AuthFilter(JWT jwtUtil, JWT jwt) {
        this.jwtUtil = jwtUtil;
        this.jwt = jwt;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String header = httpRequest.getHeader("Authorization");///The only problem is that my
        //token isn't fetched, how do I fetch the token?

        if(httpRequest.getRequestURI().equalsIgnoreCase("/user/login") ||
                httpRequest.getMethod().equalsIgnoreCase("OPTIONS") ||
//                httpRequest.getMethod().equalsIgnoreCase("auction/auctions") ||
                httpRequest.getRequestURI().equalsIgnoreCase("/user/register")){
            filterChain.doFilter(servletRequest,servletResponse);

        }

        else if (header != null && header.startsWith("Bearer")) {
            String token = header.substring(7);


            if (jwtUtil.validateToken(header)) {
                UserLifeCycleData data = new UserLifeCycleData();
                data.setUserName(JWT.extractUsername(header));
                ThreadContext.setThreadContextData(data);

                filterChain.doFilter(servletRequest, servletResponse);

            }


        }
        else{
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().println("Unauthorized access");
        }

        //THis method validates the request but how do I call the method inside each endpoint?

        ///below the method is also called when the token is invalid, but I am confused on how
        // filter each endpoint based of request and response?

        //psuedo code: inside the getMapping for login, call the do filter method, and if it works it runs
        // the code, is that how it works? This method should check for token validity and after that check whether
        //request is processed correctly.



    }

}
