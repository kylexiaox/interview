package com.Interview.util;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class RestTest {
	public static void main(String[] args) {
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		WebResource service = client.resource(getBaseURI());
		// This will print HTTP request the client is sending
		System.out.println(service.path("rest").path("register").path("kyle").accept(
				MediaType.APPLICATION_JSON).post(String.class).toString());	

	}

    // Here, the Web application root ... 
    // (then the remainder of the path should contain 'rest/*')
	private static URI getBaseURI() {
		return UriBuilder.fromUri(
				"http://localhost:8080/OnlineInterview/").build();
	}

}