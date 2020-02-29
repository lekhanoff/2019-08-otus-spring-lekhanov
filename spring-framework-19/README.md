# spring-framework-19
Проектная работа<br /><br />
Сборка: mvn clean install <br />
Запуск: java -jar -Dfile.encoding=UTF8 target/spring-library-0.0.1-SNAPSHOT.jar<br />
или mvn spring-boot:run <br />

<br />    
Сборка Docker-образа: docker build --build-arg JAR_FILE=target/*.jar -t dlekhanov/spring-library:0.0.1 .
<br />
Запуск микросервиса в Docker: docker run -p 8080:8080 dlekhanov/spring-library:0.0.1
<br />
Запуск postgres и микросервиса с помощью docker compose: docker-compose up

<br />    
<br />
Вход в приложение: http://127.0.0.1:8080/welcome 
Тестовые пользователи admin/admin и user/admin