FROM adoptopenjdk:13.0.1_9-jdk-hotspot

WORKDIR /service

ENV CONTAINER_OPTS "-XX:+UseContainerSupport -XX:MaxRAMPercentage=80"
ENV JAVA_OPTS ""
ENV SERVICE_PARAMS ""
ENV SERVICE_ADDITIONAL_PARAMS ""

ADD target/webhook-receiver.jar /service/
CMD java $CONTAINER_OPTS $JAVA_OPTS -jar webhook-receiver.jar $SERVICE_PARAMS $SERVICE_ADDITIONAL_PARAMS