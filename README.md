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
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->

## About The Project

The Weather API that developed with Spring Boot.

### Built With

This section should list any major frameworks/libraries used to bootstrap your project. Leave any add-ons/plugins for
the acknowledgements section. Here are a few examples.

* [![Java][java]]
* [![Spring Boot][springboot]]
* [![MySQL][mysql]]
* Docker
* Swagger

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- GETTING STARTED -->

## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* Java 11 (openjdk 11.0.16.1 2022-08-12)
* Docker (20.10.17, build 100c701)

### Installation

The application is fully dockerized. Therefore, you can run the following command after editing the .env file located in the project root directory.

1. Clone the repo
   ```sh
   https://github.com/gunerkaanalkim/lonely-dolphin.git
   ```
   
2. Edit .env file
3. Run docker-compose to specify the env file according to your operating system
   ```sh
   docker-compose --env-file .\.env.dev up
   ```
4. Go to swagger page
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>



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
## Contact

Guner Kaan Alkim - [@github](https://github.com/gunerkaanalkim)

Project Link: [Weather Api](https://github.com/gunerkaanalkim/lonely-dolphin)

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->

## Acknowledgments

Use this space to list resources you find helpful and would like to give credit to. I've included a few of my favorites
to kick things off!

* [Choose an Open Source License](https://choosealicense.com)
* [GitHub Emoji Cheat Sheet](https://www.webpagefx.com/tools/emoji-cheat-sheet)
* [Malven's Flexbox Cheatsheet](https://flexbox.malven.co/)
* [Malven's Grid Cheatsheet](https://grid.malven.co/)
* [Img Shields](https://shields.io)
* [GitHub Pages](https://pages.github.com)
* [Font Awesome](https://fontawesome.com)
* [React Icons](https://react-icons.github.io/react-icons/search)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/kaanalkim/

[circleciBadge]: https://dl.circleci.com/status-badge/img/gh/gunerkaanalkim/lonely-dolphin/tree/main.svg?style=shield

[circleciUrl]: https://dl.circleci.com/status-badge/redirect/gh/gunerkaanalkim/lonely-dolphin/tree/main

[codeCovBadge]: https://codecov.io/gh/gunerkaanalkim/lonely-dolphin/branch/main/graph/badge.svg?token=YSF9trxC8O

[codeCovUrl]: https://codecov.io/gh/gunerkaanalkim/lonely-dolphin

[springboot]:https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white

[java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white

[mysql]:https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white
