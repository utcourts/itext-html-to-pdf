# Maven with openjdk8
FROM maven:3.8.5-openjdk-8 as maven

# Copy config files
COPY documentation/* /usr/local/customlib/properties/

RUN mkdir itext-html-pdf-oss
COPY . itext-html-pdf-oss
WORKDIR /itext-html-pdf-oss

ARG NEXUS_USERNAME=nexususer
ARG NEXUS_PASSWORD=nexuspass
ARG GITLAB_USERNAME=gitlabuser
ARG GITLAB_PASSWORD=gitlabpass

#Build war file
RUN mvn clean package -DskipTests -s .m2/settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true

#Tomcat with openjdk8
FROM tomcat:9.0.76-jdk21-openjdk as itext-html-pdf-oss

ENV TZ="America/Denver"

# RUN useradd -ms /bin/bash -u 1000670000 tomcat

RUN mkdir -p /usr/local/customlib/properties /export/itext-html-pdf-oss && \
    chown -R 0:0 /usr/local/customlib /usr/local/tomcat /export/itext-html-pdf-oss && \
    chmod -R g+rwX /usr/local/customlib /usr/local/tomcat /export/itext-html-pdf-oss

# Copy the required files into the Tomcat directory
COPY deployment/*.xml /usr/local/tomcat/conf/
COPY deployment/setenv.sh /usr/local/tomcat/bin/

RUN echo "org.apache.tomcat.util.digester.PROPERTY_SOURCE=org.apache.tomcat.util.digester.EnvironmentPropertySource" >> /usr/local/tomcat/conf/catalina.properties

# Remove the existing ROOT application in Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file into the Tomcat webapps directory
COPY --from=maven /itext-html-pdf-oss/target/*.war /usr/local/tomcat/webapps/ROOT.war
COPY --from=maven /usr/local/customlib/properties/ /usr/local/customlib/properties/

# Add the permission to tomcat folder
# RUN chown -R tomcat:tomcat /usr/local/customlib/properties/
# RUN chown -R tomcat:tomcat /usr/local/tomcat
# RUN mkdir -p /usr/local/logs

# USER tomcat

# Start Tomcat
CMD ["catalina.sh", "run"]