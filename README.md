[![CircleCI][circleciBadge]][circleciUrl]
[![codecov][codecovBadge]][codecovUrl]

[![LinkedIn][linkedin-shield]][linkedin-url]



<br />
<div align="center">
<h3 align="center">Weather API</h3>

  <p align="center">
    Weather APIs - powered with Spring Boot
    <br />
    <a href="https://github.com/gunerkaanalkim/lonely-dolphin/blob/main/README.md"><strong>Explore the docs »</strong></a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
      <a href="#at-a-glance">Codebase at a glance</a>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

Simple Restful APIs providing calculation for weather forecast data

### Built With

* Java
* Spring Boot
* MySQL
* Docker
* Swagger

## Getting Started

This application is developed using Spring Boot, MySQL, Docker and Swagger.

### Prerequisites

Make sure you have the following before running

* Java 11 (openjdk 11.0.16.1 2022-08-12)
* Docker (20.10.17, build 100c701)

### Installation

The application is fully dockerized. Therefore, you can run the following command after editing the .env file located in
the project root directory.

1. Clone the repo
   ```sh
   https://github.com/gunerkaanalkim/lonely-dolphin.git
   ```

2. Change to the project directory and edit .env file
3. Run docker-compose to specify the env file according to your operating system
   ```sh
   docker-compose --env-file .\.env.dev up
   ```
4. Go to swagger page
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

If you want to run the application in your local development environment, please make sure to specify the environment
variables.

```
DB_HOST=jdbc:mysql://localhost:3306/weather;DB_PASSWORD=shouldBeChanged;DB_USERNAME=lonely-dolphin;PROFILE=dev
```

<!-- USAGE EXAMPLES -->

## Usage

### Create

To create a weather forecast data with a sensor request, send the sample request body to the URL

#### Request URL

```
[POST] /weather-forecast
```

#### Request Body

```
{
  "sensorId": 1,
  "metric": "TEMPERATURE",
  "value": 10
}
```

### Search

To get statictic information according to your metric, send the sample request body to the URL

Your date format should be yyyy-MM-dd'T'HH-mm-ss

#### Request URL

```
[POST] /weather-forecast?sensor=1&metrics=HUMIDITY&metrics=TEMPERATURE&statistic=MIN&from=2022-10-21T23%3A19%3A31&to=2022-10-22T23%3A20%3A11
```

### Response Body

```
{
  "HUMIDITY": 10,
  "TEMPERATURE": 10
}
```

## Codebase at a glance
* ControllerAdvise is used for global exception handling
* Strategy Patter was applied to calculate according to metrics.
* Multi-stage Dockerfile was used to compile the project.
* Integration and Unit Tests were implemented where necessary

## Contact

Guner Kaan Alkim - [@github](https://github.com/gunerkaanalkim)

Project Link: [Weather Api](https://github.com/gunerkaanalkim/lonely-dolphin)


[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/kaanalkim/

[circleciBadge]: https://dl.circleci.com/status-badge/img/gh/gunerkaanalkim/lonely-dolphin/tree/main.svg?style=shield

[circleciUrl]: https://dl.circleci.com/status-badge/redirect/gh/gunerkaanalkim/lonely-dolphin/tree/main

[codeCovBadge]: https://codecov.io/gh/gunerkaanalkim/lonely-dolphin/branch/main/graph/badge.svg?token=YSF9trxC8O

[codeCovUrl]: https://codecov.io/gh/gunerkaanalkim/lonely-dolphin

[springboot]:https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white

[java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white

[mysql]:https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white
