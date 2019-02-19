package com.ole.eventawy.event.data.respository;



import org.springframework.data.couchbase.core.query.N1qlPrimaryIndexed;
import org.springframework.data.couchbase.core.query.ViewIndexed;
import org.springframework.data.couchbase.repository.CouchbasePagingAndSortingRepository;


import com.ole.eventawy.event.data.model.Event;


@N1qlPrimaryIndexed
@ViewIndexed(designDoc = "event")
public interface EventRepository extends CouchbasePagingAndSortingRepository<Event,String>{
	

}
