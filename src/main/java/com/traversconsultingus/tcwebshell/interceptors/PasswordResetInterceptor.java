package com.traversconsultingus.tcwebshell.interceptors;

import com.traversconsultingus.tcwebshell.entity.User;
import com.traversconsultingus.tcwebshell.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PasswordResetInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    UserDAO userDAO;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication && !("anonymousUser").equals(authentication.getName())) {
            String userName = authentication.getName();
            User user = userDAO.findUserByEmail(userName);
            String requestURI = request.getRequestURI();
            if(user != null && user.isResetRequired() && !requestURI.equals("/changepassword") && !requestURI.equals("/api/users/changepassword")){
                //If we have the user in the authentication and they need to change their password redirect the url to the reset page
                if(request.getServerPort() != -1) {
                    response.sendRedirect(request.getScheme() + "://" + request.getServerName()+":"+request.getServerPort()+"/changepassword");
                }
                else{
                    response.sendRedirect(request.getScheme() + "://" + request.getServerName()+"/changepassword");
                }
            }
        }
        return true;
    }
}
