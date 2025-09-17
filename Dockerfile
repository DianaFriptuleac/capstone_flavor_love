# ===== Build stage =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copio solo i file del wrapper e il pom per sfruttare la cache
COPY mvnw mvnw.cmd pom.xml ./
COPY .mvn .mvn

# Fix line endings di Windows e rendo eseguibile il wrapper
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw

# Scarico le dipendenze (senza ancora copiare il sorgente per massimizzare la cache)
RUN ./mvnw -q -ntp -DskipTests dependency:go-offline

# Ora copio il resto del progetto e faccio il package
COPY src src
RUN ./mvnw -q -ntp clean package -DskipTests

# ===== Runtime stage =====
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copio il jar (usa wildcard per non dipendere dal nome esatto)
COPY --from=build /app/target/*SNAPSHOT.jar app.jar

# Render esporrà PORT, ma il tuo app ascolta su 8080 by default
EXPOSE 8080

# Avvio
ENTRYPOINT ["java", "-jar", "app.jar"]
