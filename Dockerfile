FROM openjdk

WORKDIR /app

COPY target/order-manager-0.0.1-SNAPSHOT.jar /app/order-manager.jar

ENTRYPOINT [ "java", "-jar", "order-manager.jar" ]