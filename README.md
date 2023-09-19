# Contribute To Community
## Decription
ContributeToCommunity.org is a place where people volunteer for various community events happening in the locality. The organization posts volunteering positions through various media platforms and ingest data through multiple channels to maintain its central database.

## Tech stack
- Java 17
- Spring(Boot, Data JPA)
- JUnit, Mockito
- PostgreSQL
- Docker

## Environment Setup
It should be installed Java 17, IntelliJ IDEA, Docker.
Run "clean install" maven command from the IDE first (or "mvn clean install" from the root directory if you're using maven outside of IDE).
To build a container and run the application run the command "docker-compose up --build"
Then you can use just "docker compose up" and "docker compose stop" to run or shut down the container respectively.

There is a file data.xlsx in the resource directory with initail data. The app reads this file always after run for filling out of data.
In case if something does wrong during uploading the file application continue working.

## API

| Endpoint  | Parameters                                    | Description                          | Response                                                                                                                                                                                                                                                                                                                    |
|-----------|-----------------------------------------------|--------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| /api/volunteers/{jobId}    | URL param "jobId"                               | Returns list of volunteers for jobId | ``` [{"id":1, "firstName":"firstName1","lastName":"lastName1","jobs":[{"id":1,"name":"job1","description":null}]}] ```                                                                                                                                                                                                      |
| /api/volunteersPag | Request integer params "pageIndex" and "pageSize" | Returns pagable list of volunteers   | ``` {"content":[{"id":1,"firstName":"firstName1","lastName":"lastName1","jobs":[{"id":1,"name":"job1","description":null}]}],"pageable":"INSTANCE","last":true,"totalPages":1,"totalElements":1,"size":1,"number":0,"sort":{"empty":true,"sorted":false,"unsorted":true},"first":true,"numberOfElements":1,"empty":false} ``` |