FROM openjdk:11

USER root

ARG APP_HOME=/opt/@project.name@

COPY @project.artifactId@-@project.version@.jar /tmp/

RUN	mkdir -p ${APP_HOME} && mv /tmp/@project.artifactId@-@project.version@.jar ${APP_HOME}

WORKDIR ${APP_HOME}

CMD ["java", "-jar", "@project.artifactId@-@project.version@.jar"]