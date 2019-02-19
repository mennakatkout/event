package com.ole.eventawy.event.data.respository.impl;



import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;



@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "event")

public interface EventCouchbaseRepositoryImpl /*extends EventRepository, CouchbasePagingAndSortingRepository<Event,String>*/{

}
