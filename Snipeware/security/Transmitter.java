package Snipeware.security;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import Snipeware.Client;

public class Transmitter {
	public static String sendToServer(String HWID) {
		try {
			String userName = System.getProperty("os.name").toLowerCase().contains("windows") ? new com.sun.security.auth.module.NTSystem().getName() : System.getProperty("user.name");
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGetWithEntity e = new HttpGetWithEntity();
			e.setURI(Client.wtf);
			HttpEntity payload = new StringEntity("{"
					+ "\n  \"hwid\": \""+HWID+"\","
					+ "\n  \"name\": \""+userName+"\","
					+ "\n  \"did\": \""+Client.DID+"\","
					+ "\n  \"version\": \""+Client.build+"\""
					+ "\n}", ContentType.APPLICATION_JSON);
			
			e.setEntity(payload);
			HttpResponse response = httpClient.execute(e);
			return response.getStatusLine().toString();
			
			}catch(Exception e) {
				
				return null;
			}
	}

}
