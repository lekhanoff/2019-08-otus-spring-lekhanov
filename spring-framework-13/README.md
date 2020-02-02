# spring-framework-13
Домашнее задание №13. Spring Security: ACL <br /><br />
Сборка: mvn clean install <br />
Запуск: java -jar -Dfile.encoding=UTF8 target/spring-framework-13-0.0.1-SNAPSHOT.jar<br />
или mvn spring-boot:run <br />
<br />    
<br />
Вход в приложение: http://127.0.0.1:8080/welcome <br />
Тестовые пользователи admin/admin, user1/admin, user2/admin, user3/admin <br />
У пользователя admin есть роль ROLE_ADMIN. Этой роли доступно изменение и чтение всех данных.<br />
У пользователя user1 есть роль ROLE_USER. Этой роли доступно чтение всех данных.<br />
У пользователя user2 есть роль ROLE_RUS_CLASSIC. Этой роли доступны книги русской классики.<br />
У пользователя user3 есть роль ROLE_FOREIGN_CLASSIC. Этой роли доступны книги зарубежной классики.<br />
