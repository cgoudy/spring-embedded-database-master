Simple Note Taking REST app

###1. Technologies used
* Maven 3.0
* Spring 4.1.6.RELEASE
* HSQLDB 2.3.2

###2. To Run this project locally
a) $ git clone https://github.com/rest/spring-embedded-database
b) $ mvn package (from root project directory)

###3. Import war file into running Tomcat instance

###4. You can 
a) Add a note (POST /api/notes
b) Get all notes (GET /api/notes)
c) Get a specific note (GET /api/notes/{id})
d) Get a note by note content (GET /api/notes/?content=somethingToSearchOn)

###5. Access `http://localhost:8080/spring-mvc-embedded-db-1.0-SNAPSHOT/api/notes`

###6. Additional questions:
1) How well does your note-searching-api scale or not scale? How would you make your search more efficient? I think it is very scalable....the biggest thing that would limit it would be the "like" search for parts of a note with the %bar% pattern guarantees a table-scan for every lookup.
2) How would you add security to your API? You could add security on the endpoints with something like the OAUTH package which would allow you to secure any and all the endpoints.
3) What features should we add to this API next? 1) PUT to overwrites (updates) a pre-existing note and 2) DELETE to delete existing notes
4) How would you test the API? A sample test is included in the test directory.
