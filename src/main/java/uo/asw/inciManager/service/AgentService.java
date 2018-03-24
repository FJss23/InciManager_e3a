package uo.asw.inciManager.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AgentService {

	private String idConnectedAgent;

	RestTemplate restTemplate = new RestTemplate();

	/**
	 * Metodo que sirve de comunicacion con agents , se le envia el usuario, login y
	 * pass y devuelve el id del usuario.
	 * 
	 * @param login
	 * @param password
	 * @param kind
	 * @return
	 */
	public Map<String, Object> communicationAgents(String login, String password, String kind) {
		Map<String, Object> datosAgente = new HashMap<String, Object>();
		Map<String, Object> datosJson = new HashMap<String, Object>();
		String urlAgents = "http://localhost:8091/user";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		datosJson.put("login", login);
		datosJson.put("password", password);
		datosJson.put("kind", kind);
		HttpEntity<Map<String, Object>> request = new HttpEntity<Map<String, Object>>(datosJson, headers);
		try {
			ResponseEntity<String> response = restTemplate.postForEntity(urlAgents, request, String.class);
			JSONObject json = new JSONObject(response.getBody());
			System.out.println("------------ OK -------------");
			/* añadimos los que sean necesarios... */
			datosAgente.put("id", json.getString("id"));
			return datosAgente;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
				throw ex;
			}
		}

		return null;
	}

	public void setIdConnected(String idAgent) {
		idConnectedAgent = idAgent;
	}

	public String getIdConnected() {
		return idConnectedAgent;
	}
}
