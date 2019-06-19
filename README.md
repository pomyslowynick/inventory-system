<!-- TABLE OF CONTENTS -->
## Inventory System

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Contact](#contact)




<!-- ABOUT THE PROJECT -->
## About The Project

![Inventory System Screen Shot](https://github.com/pomyslowynick/inventory-system/blob/master/images/inventory_system_menu_screenshot.png)

[Link to Project on GitHub](ttps://github.com/pomyslowynick/inventory-system)

This my Inventory System project that I made for my Graduate Software Developer Interview, I tried to make it to the best of my ability and use time available to me as efficiently as possible.
It was made using Spring Boot and Vaadin Java frameworks.
Main features of my application:
* It lets user add, remove and update items in inventory.
* Items have four properties: name, price, category and quantity.
* Whole inventory is constrained to maximum of 200 items, and each item can have maximum quantity of 5.
* You can filter items by their category, price and display last 5 added items.

This is RESTful API, calls are made using HTTP requests and data is transferred using JSON format.

### Built With
Major components that I have used in building this application, smaller ones not mentioned here can be found in my pom.xml:
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Vaadin](https://vaadin.com/)
* [HyperSQL Database](http://hsqldb.org/)
* [Maven](https://maven.apache.org/)
* [Lombok](https://projectlombok.org/)


<!-- GETTING STARTED -->
## Getting Started

I will provide instructions to how run my application using InteliJ Ultimate edition, if I will have time I will provide instructions for other IDE's as well.

### Prerequisites

In order for code to compile you need lombok plugin, if you are using InteliJ you can download plugin:
* [Lombok](https://plugins.jetbrains.com/plugin/6317-lombok)

Rest should be provided by Maven in pom.xml .

### Installation
1. Clone my repo ```git clone https://github.com/pomyslowynick/inventory-system```
2. In your InteliJ click ***File*** -> ***Open*** -> ***Project from existing sources...***
3. Navigate to directory where you have cloned the repo.
3. Choose pom.xml file.
4. Click next and accept all default values.
5. Run project.
6. In your web browser go to [localhost:8090](localhost:8090).

<!-- CONTACT -->
## Contact

Rafal Rosa - rafal.rosa@mycit.ie

Project Link: [Inventory System](https://github.com/pomyslowynick/inventory-system)






