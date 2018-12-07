FROM openjdk:8-alpine

WORKDIR /service
ENV JAVA_OPTS ""
ENV SERVICE_PARAMS ""
ADD target/webhook-receiver.jar /service/
CMD java $JAVA_OPTS -jar webhook-receiver.jar $SERVICE_PARAMS

