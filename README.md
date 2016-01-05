# CukeRestSalad

## Who can use this?
This module is intended to help in testing REST services. It gives you an intuitive way of forming a request in multiple steps using Gherkin syntax. Most of the steps needed for calling a request is already implemented. It also has a lot of step definitions for parsing json response and validating different nodes in the response.
Anyone who has json based REST services can use this for testing their services.

## Pre Requisites
JDK8

## Getting Started

- Clone the repository
- In the same directory level as CukeRestSalad, create a gradle project similar to [SampleCukeRestTest](https://github.com/bharathcp/SampleCukeRestTest) that will have all the cucumber tests. This project is going to be the project you will develop to test the rest service. Your directories should be as below:
```
<git clone path>/CukeRestSalad
<git clone path>/SampleCukeRestTest
```
- Create feature files inside SampleCukeRestTest project under src/main/resources/feature
- Run the below commands for linux/mac:
```
cd <git clone path>/SampleCukeRestTest
sh gradlew clean build
unzip build/distributions/SampleCukeRestTest-1.0.zip -d build/distributions/
sh build/distributions/SampleCukeRestTest-1.0/bin/SampleCukeRestTest com.bdd.restsalad.steps.Runner
```

## Sample feature file:
```
Feature: A feature to demonstrate REST cucumber util by testing FB graph api /me

  Scenario: testing for invalid access token on FB graph api /me
    Given I start building a request with "POST" method and URL "https://graph.facebook.com/v2.5/me"
    And I add "access_token" equal to "invalid_access_token" as parameter to request
    And I add post body to the request as:
      """
      	{
      		"attr" : "any value"
      	}
      """
    And I add the below values as headers to the request:
    | headerName    | headerValue       |
    | Content-Type  | application/json  |
    And I retrieve the resource
    Then the status code returned should be 400
    And The response should contain "$..error.message" with value "Invalid OAuth access token."
    And The response should contain "$..error.type" with value "OAuthException"
    And The response should contain "$..error.code" with value "190"
    And The response should contain "$..error.fbtrace_id"

```

## Other validations already available:
This module uses [jayway/JsonPath](https://github.com/jayway/JsonPath) for parsing and validating response. Assuming a sample response for demonstration there are already many step definitions for validation already implemented.

Given the response is:
```
{
    "store": {
        "book": [
            {
                "category": "reference",
                "author": "Nigel Rees",
                "title": "Sayings of the Century",
                "price": 8.95
            },
            {
                "category": "fiction",
                "author": "Evelyn Waugh",
                "title": "Sword of Honour",
                "price": 12.99
            },
            {
                "category": "fiction",
                "author": "Herman Melville",
                "title": "Moby Dick",
                "isbn": "0-553-21311-3",
                "price": 8.99
            },
            {
                "category": "fiction",
                "author": "J. R. R. Tolkien",
                "title": "The Lord of the Rings",
                "isbn": "0-395-19395-8",
                "price": 22.99
            }
        ],
        "bicycle": {
            "color": "red",
            "price": 19.95
        },
        "unicorn": []
    },
    "expensive": 10
}
```
Below step definitions validate the :
```
    And The response should contain "$..expensive" with value "10"
    And The response should contain "$..expensive"
    And The response should contain "$..author" with values:
    | Nigel Rees  | Evelyn Waugh  | Herman Melville | J. R. R. Tolkien  |
    And The \"([^\"]*)\" array has element with below attributes:
     | category  | author           | title                  | price |
     | reference | Nigel Rees       | Sayings of the Century | 8.95  | 
     | fiction   | Evelyn Waugh     | Sword of Honour        | 12.99 | 
     | fiction   | Herman Melville  | Moby Dick              | 8.99  | 
     | fiction   | J. R. R. Tolkien | The Lord of the Rings  | 22.99 | 
    And The response should contain "$..unicorn[*]" as empty array
    And The response should contain "$..book[*]" with 4 elements
    And The response is empty 
```
The last step will fail because the reponse is not empty.

These are just a few steps I could think of. Please go ahead and contribute more steps if you think it is appropriate. Else suggest any other step that is needed and I will add them.
