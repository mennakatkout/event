package com.ole.eventawy.event.data.model;

import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.ole.eventawy.util.DocumentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * This class represents the Document Event in couchbase
 * 
 * @author Reda ElSayed
 *
 */

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
	
	@Id
	@GeneratedValue(delimiter="-",strategy=GenerationStrategy.UNIQUE)
	private String uuid;
	
	@Field
	private DocumentType type= DocumentType.EVENT;
	
	@Field
	private String name;
	
	

}
