/*
 * Travers Consulting
 *
 * [2014] - [2019] Travers Consulting Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Travers Consulting Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Travers Consulting Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Travers Consulting Incorporated.
 */

package com.traversconsultingus.tcwebshell.request;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.traversconsultingus.tcwebshell.entity.User;
import com.traversconsultingus.tcwebshell.dao.RoleDAO;
import com.traversconsultingus.tcwebshell.dao.UserDAO;
import lombok.Data;
import lombok.extern.java.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.Exception;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Log
@Controller
public class MainRequestController {
    @Autowired
    UserDAO userDAO;
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    Properties appProps;

    @RequestMapping(value={"/login", "/"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication && !("anonymousUser").equals(authentication.getName())){
            modelAndView.setViewName("home");
        }
        else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }
    @RequestMapping(value="/changepassword", method = RequestMethod.GET)
    public ModelAndView changePassword(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("changepassword");
        return modelAndView;
    }
    @RequestMapping(value="/admin/users", method = RequestMethod.GET)
    public String users(Model model){
        model.addAttribute("allRoles", roleDAO.listRoles());
        model.addAttribute("user", new User());
        return "/admin/users";
    }
}
