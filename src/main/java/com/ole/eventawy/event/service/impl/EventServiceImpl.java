package com.ole.eventawy.event.service.impl;


import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ole.eventawy.event.data.model.Event;
import com.ole.eventawy.event.data.respository.EventRepository;
import com.ole.eventawy.event.service.EventService;
import com.ole.eventawy.exception.EventawyException;
import com.ole.eventawy.exception.ObjectNotFoundException;
import com.ole.eventawy.logging.LogExecutionTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is the implementation of the interface @EventService 
 * to handle the event sub-system business
 * 
 * @author Reda ElSayed
 *
 */

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventServiceImpl implements EventService{

	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);
	@Autowired
	private EventRepository repository ;

	@Override
	public String inserEvent(Event event) throws EventawyException {
		// TODO Auto-generated method stub
		
		 return repository.save(event).getUuid();
		
	}
	
	@LogExecutionTime
	public Event findEvent(String id)throws EventawyException,ObjectNotFoundException {
		
		if(id==null || "".equals(id)) {
			throw new EventawyException("Event id can't be null or empty");
		}
		
		Optional<Event> result = repository.findById(id);
		
		
		
		if(!result.isPresent()) {
			throw new ObjectNotFoundException("The event of id : "+id+" not found");
		}
		
		Event event = result.get();
		
		return event;
	}

}
