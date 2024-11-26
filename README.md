**Library Management System** is a  RESTful API for managing books and borrowers in a library. 

**Features include:**

Borrower and Book Registration: Register new borrowers and books.

Book Management: Borrow and return books.

**Techonlogies:**

Java version : Java 17 with Maven for dependencies.

Spring Boot: Simplifies configuration and setup.

**Prerequisites**

Java 17

Maven

GitHub account


**Build and Run**

1.Clone the repository:

git clone https://github.com/Muthu8891/Library_Management_System.git

cd library_management_system

2.Build the project using Maven:

mvn clean install

2.Run the application:

java -jar target/library_management_system-0.0.1-SNAPSHOT.jar

**Logging**

The log configuration can be found in applicaiton.properties

**Running Unit Test**

Unit tests are implemented to ensure the functionality and reliability of the Library Management System. 

The tests can be run using Maven, and the results will be generated in a report format.

**Running Test:**

To run the unit test, execute the following command in the root directory of your project:

mvn test

**Postman**
A Postman collection is available to facilitate testing and exploring the Library Management System API. 
This collection includes various endpoints for managing borrowers and books. 
You can import this collection into Postman to quickly set up your API requests.

**Postman Collection Information:**

Name: Library Management System

Description: Postman for Library Management System API

Schema: Postman  Schema

Register a New Borrower

Method: POST

URL: {{base_url}}/api/borrowers

Headers:

Content-Type: application/json

Body:

{

  "email": "eric.wing@outlook.com",

  "name": "Eric Wing"

}

Register a New Book

Method: POST

URL: {{base_url}}/api/books

Headers:

Content-Type: application/json

Body:

{

  "isbn": "0123456789",

  "title": "Test Book",

  "author": "Test Author"

}

List All Books

Method: GET

URL: {{base_url}}/api/books

Borrow a Book

Method: POST

URL: {{base_url}}/api/borrowers/{{borrower_id}}/borrow/{{book_id}}

Headers:

Content-Type: application/json

Body:

{

  "borrowerId": {{borrower_id}},

  "bookId": {{book_id}}

}

Return a Book

Method: POST

URL: {{base_url}}/api/borrowers/{{borrower_id}}/return/{{book_id}}

Headers:

Content-Type: application/json

Verify Borrower Details

Method: GET

URL: {{base_url}}/api/borrowers/{{borrower_id}}

Verify Book Details

Method: GET

URL: {{base_url}}/api/books/{{book_id}}

Variables Included:

base_url: http://localhost:8092

borrower_id: 1

book_id: 1


**Importing the Collection into Postman**

**To import the Postman:**

Download the postman.json file from the repository.

Open Postman and click on Import.

Select the postman.json file and click Open.

This will import all the endpoints into Postman, making it easier to test and interact with the Library Management System API.


**API Endpoints**

Register new borrower:

POST /api/borrowers

{

  "name": "Wric Wing",
  
  "email": "eric.wing@outlook.com"
  
}

Register a new book:

POST /api/books

{

  "isbn": "0123456789",
  
  "title": "Test Book",
  
  "author": "Test Author"
  
}

Get a list of all books:

GET /api/books

Borrow a book:

POST /api/borrowers/{borrowerId}/borrow/{bookId}

Return a borrowed book:

POST /api/borrowers/{borrowerId}/return/{bookId}

Get borrower details:

GET /api/borrowers/{borrowerId}


Get book details:

GET /api/books/{bookId}
