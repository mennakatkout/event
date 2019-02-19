package com.ole.eventawy.event.service;

import com.ole.eventawy.event.data.model.Event;
import com.ole.eventawy.exception.EventawyException;
import com.ole.eventawy.exception.ObjectNotFoundException;

/**
 * This interface used to manager Event sub-system business.
 * @author NSQN1761
 *
 */
public interface EventService {
	
	/**
	 * This method used to insert new event in the system
	 * 
	 * @param even        the event to be inserted
	 * @return String     the created event id
	 * @see Event
	 **/
	public String inserEvent(Event even) throws EventawyException;
	
	
	/**
	 * This method used to retrive already exist event, 
	 * if there is no event mapped to this id the system will throw ObjectNotFoundException 
	 * 
	 * @param String       The event id
	 * @return Event       The event mapped to this id
	 * @see Event
	 **/
	public Event findEvent(String id)throws EventawyException,ObjectNotFoundException;

}
