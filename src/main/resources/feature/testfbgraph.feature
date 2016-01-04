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
      | headerName   | headerValue      |
      | Content-Type | application/json |
    And I retrieve the resource
    Then the status code returned should be 400
    And The response should contain "$..error.message" with value "Invalid OAuth access token."
    And The response should contain "$..error.type" with value "OAuthException"
    And The response should contain "$..error.code" with value "190"
    And The response should contain "$..error.fbtrace_id"
