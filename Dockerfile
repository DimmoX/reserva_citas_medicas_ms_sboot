FROM eclipse-temurin:21-jdk AS buildstage

RUN apt-get update && apt-get install -y maven

WORKDIR /app

# Copia de archivos necesarios para la construcción del microservicio
COPY pom.xml .
COPY src /app/src
COPY Wallet_QXL9P21826DZWM8E /app/wallet

# Variable de entorno para la conexión a la base de datos
ENV TNS_ADMIN=/app/wallet
# Build del microservicio
RUN mvn clean package

# contenedor de producción
FROM eclipse-temurin:21-jdk
# Se copia el contendor temporal y se define donde dejar el jar generado
COPY --from=buildstage /app/target/*.jar /app/licencias_medicas.jar
# Se copia el wallet al nuevo contenedor
COPY --from=buildstage /app/wallet /app/wallet

# Ejecución del microservicio
ENTRYPOINT ["java", "-jar", "/app/licencias_medicas.jar"]