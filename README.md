# CukeRestSalad

## Who can use this?
This module is intended to help in testing REST services. It gives you an intuitive way of forming a request in multiple steps using Gherkin syntax. Most of the steps needed for calling a request is already implemented. It also has a lot of step definitions for parsing json response and validating different nodes in the response.
Anyone who has json based REST services can use this for testing their services.

## Pre Requisites
JDK8

## Getting Started

1. Clone the repository
2. In the same directory level as CukeRestSalad, create a gradle project similar to [SampleCukeRestTest](https://github.com/bharathcp/SampleCukeRestTest) that will have all the cucumber tests. This project is going to be the project you will develop to test the rest service.
3. Create feature files inside this project under src/main/resources/feature

