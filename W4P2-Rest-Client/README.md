# 🚀 Coding Shuttle: Week 4 (PART 2) - REST CLIENT
Welcome to Week 4 of the Coding Shuttle course! In this week, we will cover the below-mentioned topics.

# RestClient 
The RestClient is a synchronous HTTP client that offers a modern, fluent API. It offers an abstraction over HTTP libraries that allows for convenient conversion from a Java object to an HTTP request, and the creation of objects from an HTTP response.

## Building RestClient

```java
RestClient restClient = RestClient.builder()
        .baseUrl(BASE_URL) 
        .defaultHeader(HttpHeaders.AUTHORIZATION, 
                encodeBasic(properties.getUsername(),properties.getPassword())) 
        .build();
```

## Using the RestClient
```java
CustomerResponse customer = restClient.get() 
        .uri("/{id}",3) 
        .accept(MediaType.APPLICATION_JSON) 
        .retrieve() 
        .body(CustomerResponse.class);
```
Apart from get(), we have post(), put(), patch() and delete() methods as well.

## Handling Errors in RestClient
```java
ResponseEntity response = restClient.delete() 
        .... 
        .onStatus(HttpStatusCode::is4xxClientError, 
            (req, res)-> 
            logger.error("Couldn't delete "+res.getStatusText()) ) 
        .toBodilessEntity();
```

Check out **`RestClientConfig`** & **`EmployeeClientImpl`** classes for more details.