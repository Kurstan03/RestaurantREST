FROM openjdk:19 as build
WORKDIR /app
COPY . ./
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM openjdk:19
WORKDIR /app
COPY --from=build /app/target/RestaurantREST-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "RestaurantREST-0.0.1-SNAPSHOT.jar"]
EXPOSE 5050