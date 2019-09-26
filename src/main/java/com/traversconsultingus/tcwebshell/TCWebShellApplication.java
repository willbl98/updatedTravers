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

package com.traversconsultingus.tcwebshell;
import lombok.extern.java.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

@Log
@ComponentScan(basePackages = {"com.traversconsultingus.tcwebshell.dao", "com.traversconsultingus.tcwebshell.configuration", "com.traversconsultingus.tcwebshell.request", "com.traversconsultingus.tcwebshell.interceptors"})
@EnableJpaRepositories(basePackages = {"com.traversconsultingus.tcwebshell.repository"})
@EntityScan(basePackages = {"com.traversconsultingus.tcwebshell.entity"})
@SpringBootApplication
public class TCWebShellApplication extends SpringBootServletInitializer {

    @Override
    protected  SpringApplicationBuilder configure(SpringApplicationBuilder application){
        return application.sources(TCWebShellApplication.class);
    }
    private static ConfigurableApplicationContext context;

    public static void main(String[] args) throws Exception {

        context = SpringApplication.run(TCWebShellApplication.class, args);
    }

    @Bean
    public Properties appProps() {
        Properties properties = new Properties();

        try {
            properties.load(TCWebShellApplication.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties properties = appProps();

        mailSender.setHost(properties.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(properties.getProperty("spring.mail.properties.mail.smtp.port")));
        mailSender.setUsername(properties.getProperty("spring.mail.username"));
        mailSender.setPassword(properties.getProperty("spring.mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", properties.getProperty("spring.mail.properties.mail.transport.protocol"));
        props.put("mail.smtp.auth", properties.getProperty("spring.mail.properties.mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", properties.getProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        props.put("mail.smtp.starttls.required", properties.getProperty("spring.mail.properties.mail.smtp.starttls.required"));
        props.put("mail.debug", properties.getProperty("spring.mail.properties.mail.debug"));

        return mailSender;
    }

    /*

    @Bean
    public DefaultSftpSessionFactory defaultSftpSessionFactory() {
        DefaultSftpSessionFactory defaultSftpSessionFactory = new DefaultSftpSessionFactory();

        defaultSftpSessionFactory.setHost(appProps().getProperty("defaultSftpSessionFactory.host"));
        defaultSftpSessionFactory.setPrivateKey(new ClassPathResource("/META-INF/keys/sftpJH"));
        defaultSftpSessionFactory.setPort(Integer.parseInt(appProps().getProperty("defaultSftpSessionFactory.port")));
        defaultSftpSessionFactory.setUser(appProps().getProperty("defaultSftpSessionFactory.user"));

        return defaultSftpSessionFactory;
    }

    @Bean
    public DefaultSftpSessionFactory sftpSessionFactory() {
        DefaultSftpSessionFactory defaultSftpSessionFactory = new DefaultSftpSessionFactory();

        defaultSftpSessionFactory.setHost(appProps().getProperty("defaultSftpSessionFactory.host"));
        defaultSftpSessionFactory.setPrivateKey(new ClassPathResource("/META-INF/keys/sftpJH"));
        defaultSftpSessionFactory.setPort(Integer.parseInt(appProps().getProperty("defaultSftpSessionFactory.port")));
        defaultSftpSessionFactory.setUser(appProps().getProperty("defaultSftpSessionFactory.user"));

        return defaultSftpSessionFactory;
    }

    */

}
