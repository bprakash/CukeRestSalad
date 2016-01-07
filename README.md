# CukeRestSalad

## Who can use this?
This module is intended to help in testing REST services. It gives you an intuitive way of forming a request in multiple steps using Gherkin syntax. Most of the steps needed for calling a request is already implemented. It also has a lot of step definitions for parsing json response and validating different nodes in the response.
Anyone who has json based REST services can use this for testing their services.

## Pre Requisites
JDK8

## Getting Started
Lets say you want to test a REST service as a black box. 
- Create the project you want to use for testing. This will host all you feature files.
- Add the below dependancy similar to - [SampleCukeRestTest](https://github.com/bharathcp/SampleCukeRestTest) :
```gradle
  compile('org.cukesalad:CukeRestSalad:1.0.0')
```
- Create feature files inside your project under src/main/resources/feature
- Run the below commands for linux/mac:
```shell
> cd <git project root>
> sh gradlew clean build
> unzip build/distributions/<your project name>-1.0.zip -d build/distributions/
> sh build/distributions/<your project name>-1.0/bin/<your project name> org.cukesalad.rest.runner.Runner
```

## Sample feature file:
```gherkin
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
```javascript
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
Below step definitions validate the the response:
```gherkin
    And The response should contain "$..expensive" with value "10"
    And The response should contain "$..expensive"
    And The response should contain "$..author" with values:
    | Nigel Rees  | Evelyn Waugh  | Herman Melville | J. R. R. Tolkien  |
    And The "$..book[*]" array has element with below attributes:
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

## XML response support
This plugin assumes json response by default. But if the http response contains a header - "Content-Type: application/xml", then it will try to parse the response as xml. If the response is xml, then the xpath in the feature files should comply with the java xpath syntax. These are pretty good tutorials to start off - "[Java XPath Tutorial](http://viralpatel.net/blogs/java-xml-xpath-tutorial-parse-xml/)" and "[How XPath Works](https://docs.oracle.com/javase/tutorial/jaxp/xslt/xpath.html)"
For the sake of demonstration we will take and example xml and define step definition for the same:

```xml
<response>
	<store>
		<book>
			<category>reference</category>
			<author>Nigel Rees</author>
			<title>Sayings of the Century</title>
			<price>8.95</price>
		</book>
		<book>
			<category>fiction</category>
			<author>Evelyn Waugh</author>
			<title>Sword of Honour</title>
			<price>12.99</price>
		</book>
		<book>
			<category>fiction</category>
			<author>Herman Melville</author>
			<title>Moby Dick</title>
			<isbn>0-553-21311-3</isbn>
			<price>8.99</price>
		</book>
		<book>
			<category>fiction</category>
			<author>J. R. R. Tolkien</author>
			<title>The Lord of the Rings</title>
			<isbn>0-395-19395-8</isbn>
			<price>22.99</price>
		</book>
		<bicycle>
			<color>red</color>
			<price>19.95</price>
		</bicycle>
		<unicorn></unicorn>
	</store>
	<expensive>10</expensive>
</response>
```

The step definitions for the above xml would be:
```gherkin
    And The response should contain "//store//color" with value "red"
    And The response should contain "//expensive"
    And The response should contain "//author" with values:
    | Nigel Rees  | Evelyn Waugh  | Herman Melville | J. R. R. Tolkien  |
    And The "//book" array has element with below attributes:
     | category  | author           | title                  | price |
     | reference | Nigel Rees       | Sayings of the Century | 8.95  | 
     | fiction   | Evelyn Waugh     | Sword of Honour        | 12.99 | 
     | fiction   | Herman Melville  | Moby Dick              | 8.99  | 
     | fiction   | J. R. R. Tolkien | The Lord of the Rings  | 22.99 | 
    And The response should contain "//unicorn" as empty array
    And The response should contain "//book" with 4 elements
    And The response is empty 
```

##Latest release:
Release 1.0.1

## How to contribute
These are just a few steps I could think of. If there are any other feature that you wish for, please go ahead and create the same in the [issue tracker](https://github.com/cukesalad/CukeRestSalad/issues). I will make best efforts to add them ASAP.
If you wish contribute by coding, please fork the repository and raise a pull request. 

