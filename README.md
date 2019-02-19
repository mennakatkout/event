# Eventawy

This project based on  sprint boot and couchbase (noSQL document db)

https://www.couchbase.com/

   you should create bucket( like db schema in oracle) with name eventawy and create user with username/password (eventawy/password) has access on this bucket
   
   - the file application.properties contain the db connection details 
   
   - the spring boot app has a REST api
    - /api/event/add to add event to the db
    - /api/event/find/{id} to retrive event with the id from db
    
    these api details in the file (\src\main\java\com\ole\eventawy\event\api\EventController.java)
    
    
    
   
   
