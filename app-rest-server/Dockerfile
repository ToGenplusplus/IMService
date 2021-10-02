FROM adoptopenjdk/openjdk15:ubi
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY target/IMS-springboot-rest-application.jar app.jar
EXPOSE 5000
CMD ["java","-jar","app.jar"]