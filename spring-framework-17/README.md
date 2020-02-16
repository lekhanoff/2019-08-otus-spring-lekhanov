# spring-framework-17
Домашнее задание №17. Docker, оркестрация, облака, облачные хостинги. <br /><br />
Сборка: mvn clean install <br />
Запуск: java -jar -Dfile.encoding=UTF8 target/spring-framework-17-0.0.1-SNAPSHOT.jar<br />
или mvn spring-boot:run <br />

<br />    
Сборка Docker-образа: docker build --build-arg JAR_FILE=target/*.jar -t dlekhanov/spring-framework-17:0.0.1 .
<br />
Запуск микросервиса в Docker: docker run -p 8080:8080 dlekhanov/spring-framework-17:0.0.1
<br />
Запуск postgres и микросервиса с помощью docker compose: docker-compose up

<br />    
<br />
Вход в приложение: http://localhost:8080/welcome