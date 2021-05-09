package com.georghs.vertx.exchange;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Hello world!
 *
 */
public class BasicWebVerticle extends AbstractVerticle
{
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicWebVerticle.class);
	
    public static void main( String[] args )
    {
    		Vertx vertx = Vertx.vertx();
    		vertx.deployVerticle(new BasicWebVerticle());
    }
    
    @Override
    public void start() {
    	LOGGER.info("Verticle BasicWebVerticle Started");
    	
    	
    	Router router = Router.router(vertx);
    	
    	// API routing
    	router.get("/api/v1/products").handler(this::getAllProducts);
    	
    	
    	
    	router.get("/yo.html").handler(routingContext -> {
    		
    		ClassLoader classLoader = getClass().getClassLoader();
    		
    		File file = new File(classLoader.getResource("webroot/yo.html").getFile());
    		
    		String mappedHTML = "";
    		
    		try {
    			StringBuilder result = new StringBuilder("");
    			Scanner scanner = new Scanner(file);
    			
    			while (scanner.hasNextLine()) {
    				String line = scanner.nextLine();
    				result.append(line).append("\n");
    			}
    			
    			scanner.close();
    			
    			mappedHTML = result.toString();
    			mappedHTML = replaceAllTokens(mappedHTML, "{name}", "Tom Jay");
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		LOGGER.info(mappedHTML);
    		routingContext.response().putHeader("content-type", "text/html").end(mappedHTML);
    		
    		
    		
    	});
    	
    	
    	router.route().handler(StaticHandler.create().setCachingEnabled(false));
    	
    	vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    	
    }


	private String replaceAllTokens(String input, String token, String newValue) {
		
		String output = input;
		
		while ( output.indexOf(token) != -1) {
			output = output.replace(token, newValue);
		}
		
		return output;
	}
	
	private void getAllProducts(RoutingContext routingContext) {
		
		JsonObject responseJson = new JsonObject();
		
		responseJson.put("itemNumber", "123");
		responseJson.put("description", "My item 123");
		
		routingContext.response()
		.setStatusCode(200)
		.putHeader("content-type", "application/json")
		.end(Json.encode(responseJson));
		
	}

	@Override
    public void stop() {
    	
    }
    
   
}