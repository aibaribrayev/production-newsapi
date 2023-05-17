# REST API for News

Spring Boot is an application for storing, processing and displaying new

### Requirements
JDK: Java 17 or compatible version.

Docker: You can download Docker from the official website for your operating system.

### Running the application locally with Docker
Clone the repository to the desired folder. On the terminal navigate to the root directory of the project, and run: "docker-compose up". It will pull the docker image for the api from my docker hub repository and run the application and the database on docker containers. Once the containers have started, you can access the application at http://localhost:8080/swagger-ui/index.html.

You can also build a Docker image locally. I configured the "build.gradle" file to create a Docker image with the gradlew command.

- First, make sure that Docker is running.
- In the terminal navigate to the root directory of the project
- and run ```./gradlew buildImage```. It will build an image called "newsapi:latest". 
- If you want to use this image instead of the image from my docker repository, open the docker-compose.yaml file and change the image for the "app" service from ```aibar/newsapi:latest``` to ```newsapi:latest```.
- Save and run in the terminal ```docker-compose up```. 

Note: I added swagger-ui for documentation, but since I have also added spring security the requests with external instruments such as Postman or Insomnia is preferred. 


### Using the application

You can send requests with Postman or any other software.

### 1. Register

to register new user send post request to ```http://localhost:8080/auth/register``` with the body
```
{
    "name": "YourName"
    "email":"youremail@email.com",
    "password":"yourpassword"
}
```

The application will test if the email has a valid format and if the password is not too short. An exception will appear with the message "Email has already been taken" if the user with such an email already exists. If everything is fine the JWT token will be returned in the response body.

This token will be used to access CRUD requests for news sources and topics. The expiration time is 12 hours.

To authenticate with an already registered email send a post request to ```http://localhost:8080/auth/authenticate``` with the body

```
{
    "email":"youremail@email.com",
    "password":"yourpassword"
}
```
If the account exists, then new JWT token will be generated for next 12 hours. You can change it in application.properties. 


### 2. News, Source and Task requests

To access any of the requests below, include the JWT token generated during authentication in the request headers.
This needs to be included in the request headers:

    Authorization: Bearer {JWT_TOKEN}

Replace {JWT_TOKEN} with the token obtained during authentication.

#### Source Endpoints

- Get All Sources

To retrieve all sources, send a GET request to ```/source```.

- Get Source by ID

To retrieve a specific source by its ID, send a GET request to ```/source/{id}```. Replace {id} with the actual ID of the source.

- Add new Source

To add a new source, send a POST request to /source with the following JSON body: ```{ "name": "Source Name" }```

- Update Source

To update an existing source, send a PUT request to ```/source/{id}```.

```{ "name": "Updated Source Name" }```
- Delete Source

To delete a source, send a DELETE request to ```/source/{id}```.

--

#### Topic Endpoints

- Get All Topics

To retrieve all topics, send a GET request to /topic.

- Get Topic by ID

To retrieve a specific topic by its ID, send a GET request to /topic/{id}. Replace {id} with the actual ID of the topic.

- Add Topic

To add a new topic, send a POST request to /topic with the following JSON body:

```{ "name": "Topic Name" }```
- Update Topic

To update an existing topic, send a PUT request to /topic/{id}. Include the following JSON body with the updated information:

```{ "name": "Updated Topic Name" }```

- Delete Topic

To delete a topic, send a DELETE request to /topic/{id}.

-- 
#### News Endpoints

- Get All News

    
To retrieve all news articles, send a GET request to /news. You can optionally specify the pagination parameters page and size as query parameters. The default values are 0 for page and 10 for size.

- Get News by ID

To retrieve a specific news article by its ID, send a GET request to /news/{id}. An exception with the message "can't find news with {id}" will be thrown if the news with id doesn't exist.

- Add News

To add to a news , send a POST request to ```/news``` with the following JSON body:

    { "title": "News Title", "description": "News Description", "sourceId": 1, "topicId": 1 }
Please make sure to replace sourceId and topicId with the actual IDs of the corresponding source and topic. An exception with a corresponding message will be returned if the source or topic id doesn't exist.

- Update News

To update an existing news article, send a PUT request to /news/{id}. Include the following JSON body with the updated information:

    { "title": "Updated News Title", "description": "Updated News Description", "sourceId": 1, "topicId": 1 }

- Delete News

To delete a news article, send a DELETE request to ```/news/{id}```.

### Scheduled statistical task
- Daily at midnight a file statistics_date.csv containing the number of news for
  each source is created. The task is performed on multiple threads. For testing purposes only the number of threads is set to 1 per 10 sources. It can be increased  if needed. 

