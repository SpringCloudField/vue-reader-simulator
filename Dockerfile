FROM java:8-jdk-alpine
 
RUN mkdir -p /usr/src/app/
WORKDIR /usr/src/app/
COPY target/vue-reader-simulator-0.0.1-SNAPSHOT.jar /usr/src/app
COPY mongo-init.js /docker-entrypoint-initdb.d/
 
EXPOSE 8082
HEALTHCHECK --start-period=120s CMD curl -f http://localhost:8085/ || exit 1


ENTRYPOINT ["sh", "-c"]
CMD [ "java $JAVA_OPTS -Dspring.data.mongodb.host=mongodb -jar vue-reader-simulator-0.0.1-SNAPSHOT.jar" ]