# Use uma imagem base do OpenJDK para executar a aplicação
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o JAR construído da fase anterior para o diretório de trabalho
COPY --from=build /app/target/*.jar app.jar

# Copia o arquivo de credenciais do Firebase para o diretório de recursos da aplicação no contêiner
COPY src/main/resources/leontisfotos-firebase-adminsdk-3nepr-a0c5dd1819.json /app/

# Expõe a porta em que a aplicação será executada
EXPOSE 8080

# Define o comando padrão para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
