FROM docker-dev.javatar.space/jdk-11:1.0

WORKDIR /service
ENV JAVA_OPTS ""
ENV SERVICE_PARAMS ""
ADD target/webhook-receiver.jar /service/
CMD java-entrypoint webhook-receiver.jar