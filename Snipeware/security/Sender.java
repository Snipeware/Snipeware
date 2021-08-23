package Snipeware.security;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import Snipeware.Client;

public class Sender {
	public static String sendToServer(String HWID) {
		try {
			String server = "https://wawawawawawsnipewarontop.herokuapp.com/auth";
			String userName = new com.sun.security.auth.module.NTSystem().getName();
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGetWithEntity e = new HttpGetWithEntity();
			e.setURI(new URI(server));
			HttpEntity payload = new StringEntity("{"
					+ "\n  \"hwid\": \""+HWID+"\","
					+ "\n  \"name\": \""+userName+"\","
					+ "\n  \"did\": \""+Client.DID+"\","
					+ "\n  \"version\": \"1.0\""
					+ "\n}", ContentType.APPLICATION_JSON);
			
			e.setEntity(payload);
			HttpResponse response = httpClient.execute(e);
			System.out.println(response);
			return response.getStatusLine().toString();
			
			}catch(Exception e) {
				
				return null;
			}
	}

}
