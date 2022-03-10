Some clarification:

* There is no check if the new content is the same as current
* There is no requirements (other than unique numerical value) about request number - so why not use id?
* There is no modification date in history - there is no such requirements
* Change in the content of the request is visible in the history
* Content of therequest could be change to null
* Filter by name searches if the name contains a search term

unit tests may not cover all cases.

Swagger address -> localhost:8080/swagger-ui.html