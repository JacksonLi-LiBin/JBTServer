package com.matrix.jbt.tool;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class GetURLUtil {
	static ClientConfig config;
	static Client client;
	static WebResource service;

	static {
		config = new DefaultClientConfig();
		client = Client.create(config);
		service = client.resource(getBaseURI());
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(ReadProperties.read("url", "url")).build();
	}

	public static WebResource getService() {
		return service;
	}
}
