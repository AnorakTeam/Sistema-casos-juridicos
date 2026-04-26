FROM eclipse-temurin:21-jdk AS builder

WORKDIR /workspace

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw && ./mvnw -q -DskipTests dependency:go-offline

COPY src/ src/

RUN ./mvnw -q -DskipTests package


FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /workspace/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
