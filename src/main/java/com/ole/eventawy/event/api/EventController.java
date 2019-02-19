package com.ole.eventawy.event.api;


import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ole.eventawy.event.data.model.Event;
import com.ole.eventawy.event.dto.EventDto;
import com.ole.eventawy.event.service.EventService;

import com.ole.eventawy.exception.EventawyException;
import com.ole.eventawy.exception.ObjectNotFoundException;
import com.ole.eventawy.util.Utils;

/**
 * 
 * This class is the API used to manage the Events in the system
 * @author Reda ElSayed
 *
 */
@RestController
@RequestMapping("/api/event")
public class EventController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private EventService service;
	
	@Autowired
    private ModelMapper modelMapper;
	

	@RequestMapping(value= "/add" , method=RequestMethod.POST)
	public String insertEvent(@RequestBody @Valid EventDto eventdto) throws EventawyException {
		
		// Convert the recieved DTO to Entity to be saved in the db
		Event event = Utils.convertToEntity(modelMapper, eventdto, Event.class);
		return service.inserEvent(event);

	}
	
	@RequestMapping(value= "/find/{id}" , method=RequestMethod.GET)
	public EventDto findEvent(@PathVariable(name="id",required=true) String id) throws EventawyException, ObjectNotFoundException {
		
		Event event = service.findEvent(id);
		// convert the Entity to dto to be send to the end user
		EventDto eventDto = Utils.convertToDto(modelMapper, event, EventDto.class);
		return eventDto;
		

	}

}
