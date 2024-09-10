# Usando uma imagem Maven para construir o projeto
FROM maven:3.8.5-openjdk-17 AS builder

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o código fonte para o contêiner
COPY ../../pom.xml .
COPY ../../src ./src

# Compila o projeto e gera o jar
RUN mvn clean package -DskipTests

# Usando uma imagem OpenJDK para rodar o jar
FROM openjdk:17

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o jar gerado da fase anterior
COPY --from=builder /app/target/*.jar /app/app.jar

# Comando padrão para rodar o aplicativo
CMD ["java", "-jar", "/app/app.jar"]