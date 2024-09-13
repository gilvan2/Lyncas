# Lyncas
Desafio Backend

Build e deploy local
    Com testes: mvn clean install && mvn spring-boot:run
    Sem testes: mvn clean install -DskipTests && mvn spring-boot:run

docker(Caso necessário):
    banco de dados:docker run --name lyncas-db-container -p 5432:5432 -d lyncas-db
    aplicação:
        ./mvnw clean package
        docker build -t lyncas-image-app:latest -f docker/app/Dockerfile .
        docker run --name lyncas-app-container -p 8080:8989 -d lyncas-app

docker-compose:
    docker-compose up -d

Acesso local
    http://localhost:8989/