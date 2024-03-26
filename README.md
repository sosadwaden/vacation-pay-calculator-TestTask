# vacation-pay-calculator-TestTask
Тестовое задание для учебного центра Neoflex, 2024
## Техническое задание
Приложение "Калькулятор отпускных".
Микросервис на SpringBoot + Java 11 c одним API:
GET "/calculacte"

Минимальные требования: Приложение принимает твою среднюю зарплату за 12 месяцев и количество дней отпуска - отвечает суммой отпускных, которые придут сотруднику.
Доп. задание: При запросе также можно указать точные дни ухода в отпуск, тогда должен проводиться рассчет отпускных с учётом праздников и выходных.

Проверяться будет чистота кода, структура проекта, название полей\классов, правильность использования паттернов. Желательно написание юнит-тестов, проверяющих расчет.
## Используемые технологии
Java 17 <br/>
SpringBoot <br/>
SpringMVC <br/>
jUnit <br/>
MockMvc <br/>
Swagger <br/>
Docker
## API
Запрос со средней зарплатой и количеством дней отпуска: <br/>
http://localhost:7070/calculate?vacationDays=28&averageSalary=40000 <br/>
**Response**:
<pre>
{ 
    "message": "The amount of vacation pay (without NDFL)",
    "vacationPay": 33256.32
}
</pre>
Запрос со средней зарплатой и датами отпуска: <br/>
http://localhost:7070/calculate?vacationDays=28&startDateOfVacation=2024-01-01&endDateOfVacation=2024-01-30 <br/>
**Response**:
<pre>
{
    "message": "The amount of vacation pay (without NDFL)",
    "vacationPay": 19003.04
}</pre>
<br/>

## Docker
Собрать проект:
```java
mvn clean package
```

Собрать docker image:
```dockerfile
docker build -t vacation-pay-calculator .
```

Запустить docker container:
```dockerfile
docker run -p 7070:8080 vacation-pay-calculator
```

Остановить docker container:
```dockerfile
docker stop vacation-pay-calculator
```
## Swagger документация
Swagger-ui: <br/>
http://localhost:7070/swagger-ui/index.html
![swaggerui](https://github.com/sosadwaden/vacation-pay-calculator-TestTask/assets/106944660/940122d5-8658-4839-ba45-881ad7f6dbce)

Api docs: <br/>
http://localhost:7070/v3/api-docs
![apidocs](https://github.com/sosadwaden/vacation-pay-calculator-TestTask/assets/106944660/ccd01d56-b184-4436-8149-976c61bdfa71)

## Postman
Запрос со средней зарплатой и количеством дней отпуска: <br/>
![request1](https://github.com/sosadwaden/vacation-pay-calculator-TestTask/assets/106944660/ed48e562-be8a-4c95-9d22-0fb2d570e5ef)
Запрос со средней зарплатой и датами отпуска: <br/>
![request2](https://github.com/sosadwaden/vacation-pay-calculator-TestTask/assets/106944660/8ac0ae1c-9a30-4d33-a185-4d4ee4fbae67)
