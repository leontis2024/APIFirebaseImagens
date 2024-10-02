# Use uma imagem base do Maven para construir a aplicação
FROM maven:3.8.3-openjdk-17 as Build
 
# Define o diretório de trabalho dentro do container
WORKDIR /app
 
# Copia o arquivo pom.xml e o diretório src para o diretório de trabalho
COPY pom.xml .
COPY src ./src

# Copia o arquivo de credenciais do Firebase para o diretório correto
COPY src/main/resources/leontisfotos-firebase-adminsdk-3nepr-4b41f628f0.json /app/src/main/resources/

# Executa o comando Maven para construir a aplicação
RUN mvn clean package -DskipTests

# Use uma imagem base do OpenJDK para executar a aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR construído da fase anterior para o diretório de trabalho
COPY --from=Build /app/target/*.jar app.jar

# Copia o arquivo de credenciais do Firebase para o diretório de recursos da aplicação no contêiner
COPY src/main/resources/leontisfotos-firebase-adminsdk-3nepr-4b41f628f0.json /app/

# Expõe a porta em que a aplicação será executada
EXPOSE 8080

# Define o comando padrão para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
