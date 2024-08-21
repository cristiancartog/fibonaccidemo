# Fionacci test service for coding demo

## build, test, run

````
mvn clean package
cd target
java -jar .\fibonaccier-0.0.1-SNAPSHOT.jar
````

check out the test/resources folder, there are http requests for manually testing the service

## design notes

- all endpoints are grouped in the FibonacciController on the */fibo* path as they concern the same single functionality
- the */next* and */back* endpoints use POST as they change a resource on the server
- the values endpoint is a GET as there are no side-effects for this operation
- the result of the GET values (a list) is wrapped in an API model (UserListResult) which also provides the name uf the user
- to differentiate between users, the **mandatory** "user" parameter was introduced for all requests
- the */next* endpoint will always generate user data
- for the */back* and */values* endpoints, the user data has to have been generated by a previous call to */next*, 
otherwise an exception is thrown and converted to a 404 for the client
- since there was no indication about the size of the expected fibonacci values, I estimated a very low number and modeled it with 
Integer. Long, would be the next step as it would still fit the size of a JSON *number*. But for larger values, one needs BigInteger
and a strategy to serialize/deserialize it to JSON (or whatever the means of communication is used)
