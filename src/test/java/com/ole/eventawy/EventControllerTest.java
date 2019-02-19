package com.ole.eventawy;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.GenericContainer;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.cluster.ClusterManager;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import com.ole.eventawy.event.data.model.Event;
import com.ole.eventawy.event.data.respository.EventRepository;
import com.ole.eventawy.event.service.EventService;
import com.ole.eventawy.util.CouchbaseWaitStrategy;


@RunWith(SpringRunner.class)
@SpringBootTest(classes={EventawyApplication.class})
@WebAppConfiguration
@ActiveProfiles("tst")
public class EventControllerTest {
	
//	@ClassRule
//    public static GenericContainer couchbase =
//            new GenericContainer("eventawy_db:latest")
//                    .withExposedPorts(8091, 8092, 8093, 8094, 11207, 11210, 11211, 18091, 18092, 18093)
//                    .waitingFor(new CouchbaseWaitStrategy());

	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Autowired
	EventRepository repository;
	
//	@MockBean
//	EventRepository repositoryMock;
//	
	@Autowired
	EventService service;
	
	public  String createdEventId="";

	private static String INSERTED_EVENT_NAME="TEST_EVENT_NAME";
	 private static MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
		      MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	 
	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
	  public void setConverters(HttpMessageConverter<?>[] converters) {
	    mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
	        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);
	    Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	  }
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		
	}
	
	@After
	public void clean() {
	   // release your resources
	   // Delete your test data
		
		
		repository.deleteById(createdEventId);
		
	}
	
	
	
	/*
	 * Initalize docker container run the couchbase custom image to run the test cases... 
	 * 
	 * */
//	@Test
//    public void eventawyBucketTest() throws InterruptedException {
//        CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
//                .bootstrapCarrierDirectPort(couchbase.getMappedPort(11210))
//                .bootstrapCarrierSslPort(couchbase.getMappedPort(11207))
//                .bootstrapHttpDirectPort(couchbase.getMappedPort(8091))
//                .bootstrapHttpSslPort(couchbase.getMappedPort(18091))
//                .build();
//        CouchbaseCluster cc = CouchbaseCluster.create(env);
//        ClusterManager cm = cc.clusterManager("Administrator", "password");
//        assertTrue(cm.hasBucket("eventawy"));
//        Bucket bucket = cc.openBucket("eventawy");
//        
//        bucket.close();
//}


	/*
	 * Check the error handling in case the requested event not found in db
	 * 
	 * */
	 @Test
	  public void getNonExistingEvent() throws Exception {
		   mockMvc.perform(get("/api/event/find/notFoundId")).andExpect(status().isNotFound())
	    .andExpect(content().contentType(CONTENT_TYPE));
		 
	
	  }
	 
	@Test
	public void addEvent() throws Exception {
		
		// Given event create and not null  
		Event createdEvent = new Event();
		
		createdEvent.setName(INSERTED_EVENT_NAME);
		//when
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/event/add")
				.accept(MediaType.APPLICATION_JSON).content(json(createdEvent))
				.contentType(MediaType.APPLICATION_JSON);

		ResultActions action = mockMvc.perform(requestBuilder);
		
		//Then
		
		createdEventId= action.andReturn().getResponse().getContentAsString();
		
		 action.andExpect(status().isOk())
		.andExpect(jsonPath("$").value(createdEventId));

		
	  
		
	}
	

//	@Test
//	public void findEventByIdMock() throws Exception {
//		
//		
//		Event mockEvent = new Event();
//		mockEvent.setUuid("123");
//		mockEvent.setName(INSERTED_EVENT_NAME);
//		Mockito.when(repositoryMock.save(Mockito.any())).thenReturn(mockEvent);
//	
//		 mockMvc.perform(get("/api/event/find/123"))
//				
//		// Then
//	    .andExpect(status().isOk())
//	    .andExpect(jsonPath("$.uuid").value("123"))
//	    .andExpect(jsonPath("$.name").value(INSERTED_EVENT_NAME));
//
//		
//		
//	}
//	
	
	
	@Test
	public void findEventById() throws Exception {
		
		// Given the event is created with Id "createdEventId" in add event
		Event event = new Event();
	    event.setName(INSERTED_EVENT_NAME);
	    
	    createdEventId =   service.inserEvent(event);
		
		//when
		 mockMvc.perform(get("/api/event/find/"+createdEventId))
				
		// Then
	    .andExpect(status().isOk())
	    .andExpect(jsonPath("$.uuid").value(createdEventId))
	    .andExpect(jsonPath("$.name").value(INSERTED_EVENT_NAME));

		
		
	}
	
	
	
	
	 @SuppressWarnings("unchecked")
	  protected String json(Object o) throws IOException {
	    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
	    mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
	    return mockHttpOutputMessage.getBodyAsString();
	  }
	
}
