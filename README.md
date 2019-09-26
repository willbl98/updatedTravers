# Device Manager Website
##Programming Notes
###Maven 
####Properties Access via @Value
If you are inheriting from the spring-boot-starter-parent POM, the default filter token of the maven-resources-plugins has been changed from ${*} to @ (i.e. @maven.token@ instead of ${maven.token}) to prevent conflicts with Spring-style placeholders. If you have enabled maven filtering for the application.properties directly, you may want to also change the default filter token to use other delimiters.

So instead of using ${prop.name} you should use @prop.name@
Example: @Value("${system.name}") --> @Value("@system.name@")

