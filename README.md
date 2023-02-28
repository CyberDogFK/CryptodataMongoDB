# Cryptodata Parser

This application work with cryptocurrencies. Save the most common data about currencies,
and give instruments to work with them. 

Every 10 second application automatically ask data from CEX.IO API last data about most common cryptocurrencies
in USD values. And save in to MongoDB.
After that you can see prices with endpoints, or take csv file from api with that data.
This application easy to expansion.

# Accepted cryptocurrencies

| Currency  | Abbreviate |
|-----------|------------|
| US Dollar | USD        |
| Bitcoin   | BTC        |
| Ethereum  | ETH        |
| Ripple    | XRP        |

For now - every value and prices in USD

## Endpoints

|                                   Endpoint                                   | Method |                                    Description                                    |
|:----------------------------------------------------------------------------:|:------:|:---------------------------------------------------------------------------------:|
|              `/cryptocurrencies/minprice?name=[currency_name]`               |  GET   |                  Return lowest price for selected cryptocurrency                  |
|              `/cryptocurrencies/maxprice?name=[currency_name]`               |  GET   |                 Return biggest price for selected cryptocurrency                  |
| `/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]` |  GET   |            Return selected page with selected save for cryptocurrency             |
|                           `/cryptocurrencies/csv`                            |  GET   | Start downloading csv report file with max and min price for every cryptocurrency |

## Technologies

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data
- Mongo DB
- Lombok
- Mockito
- JUnit
- TestContainers
- Docker

## How to start

If you have docker, in terminal:
1. Download repo to you computer
2. Use
````
./mvnw clean package
````
3. Use, and wait booting
````
docker-compose up
````

If not:
1. Download repo to you computer
2. Change
   src/main/resources/application.properties
   , change values to you database, i create this application for work with MySQl, so recommend use the same
3. Use in command line, from directory.
```
./mvnw spring-boot:run
````
4. GoTo localhost:6868 and have fun
