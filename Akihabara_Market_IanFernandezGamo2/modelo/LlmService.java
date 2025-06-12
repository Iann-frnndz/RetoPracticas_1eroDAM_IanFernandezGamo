package modelo;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import com.google.gson.*;

public class LlmService {

	// añadimos los atributos de la api y el modelo para trabajar con ellos 
	 	private final String apiKey;
	    private final String model;

	    public LlmService() {
	        // Cargamos nuestra API KEY que vamos a utilizar de nuestro archivo config.properties
	        Properties config = new Properties();
	        try {
	        	// Probamos que la conexion con la API sea correcta y funcione el modelo.
	            config.load(Files.newBufferedReader(Paths.get("config.properties")));
	            apiKey = config.getProperty("OPENROUTER_API_KEY");
	            model = config.getProperty("MODEL", "mistralai/mistral-7b-instruct:free"); 
	        } catch (IOException e) { // en caso de que algo en nuestro archivo .properties este incorrecto lanzará error
	            throw new RuntimeException("Error al leer config.properties", e);
	        }
	    }
	    
	    // Vamos a utilizar este metodo para enviar los prompts de la IA al usuario 
	    public String enviarPrompt(String promptUsuario) {
	        try {
	            // Construimos el objeto JSON para que va a representar el cuerpo de la solicitud y le asignamos el modelo de nuestra IA
	            JsonObject body = new JsonObject();
	            body.addProperty("model", model);
	            
	            // se construye lo que recibe la api y responde al usuario con el prompt correspondiente 
	            JsonArray messages = new JsonArray();
	            JsonObject message = new JsonObject();
	            message.addProperty("role", "user");
	            message.addProperty("content", promptUsuario);
	            messages.add(message);
	            
	            // el array de mensajes se añade al cuerpo de la solicitud hecha
	            body.add("messages", messages);

	            // Creamos el objeto HttpRequest que nos contiene la URL de la API, la autorizacion de la API KEY el tipo de contenido y el cuerpo de la slicitud qeu se ha convertido a JSON
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create("https://openrouter.ai/api/v1/chat/completions"))
	                    .header("Authorization", "Bearer " + apiKey)
	                    .header("Content-Type", "application/json")
	                    .header("HTTP-Referer", "http://localhost") 
	                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
	                    .build();

	            // debemos de crear un cliente HTTP y enviamos la solicitud y recibe respuesta del servidor que la guardamos como String
	            HttpClient client = HttpClient.newHttpClient();
	            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

	            // Y procesamos la respuesta que nos va a dar  en el caso de que sea 200 que significa que está OKEY
	            if (response.statusCode() == 200) {
	                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
	                JsonArray choices = jsonResponse.getAsJsonArray("choices");
	                JsonObject firstChoice = choices.get(0).getAsJsonObject();
	                JsonObject messageContent = firstChoice.getAsJsonObject("message");
	                return messageContent.get("content").getAsString().trim();
	            } else { // en el caso de qeu el codigo no este correcto lanzamos un error que incluye el codigo y la respuesta del servidor
	                return "Error del servidor: " + response.statusCode() + "\n" + response.body();
	            }

	        } catch (Exception e) { // si no conseguimos conectar con el modelo mandamos un mensaje
	            return "Error al conectar con OpenRouter: " + e.getMessage();
	        }
	    }
}
