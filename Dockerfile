# Maven with openjdk8
FROM maven:3.8.5-openjdk-17 as maven

# Copy config files
COPY documentation/* /usr/local/customlib/properties/

RUN mkdir itext-html-pdf-oss
COPY . itext-html-pdf-oss
WORKDIR /itext-html-pdf-oss


#Build war file
RUN mvn clean package -DskipTests -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true

#Tomcat with openjdk8
FROM tomcat:10.1.11-jdk21-openjdk as itext-html-pdf-oss

ENV TZ="America/Denver"

RUN mkdir -p /export/paperwork && \
    chown -R 0:0 /usr/local/tomcat /export/paperwork && \
    chmod -R g+rwX /usr/local/tomcat /export/paperwork

RUN echo "org.apache.tomcat.util.digester.PROPERTY_SOURCE=org.apache.tomcat.util.digester.EnvironmentPropertySource" >> /usr/local/tomcat/conf/catalina.properties

# Remove the existing ROOT application in Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy the WAR file into the Tomcat webapps directory
COPY --from=maven /itext-html-pdf-oss/target/*.war /usr/local/tomcat/webapps/ROOT.war


# Start Tomcat
CMD ["catalina.sh", "run"]