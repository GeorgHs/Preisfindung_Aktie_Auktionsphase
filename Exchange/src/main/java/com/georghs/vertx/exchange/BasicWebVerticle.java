package com.georghs.vertx.exchange;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.georghs.vertx.exchange.stock.Stock;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
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
    	DeploymentOptions options = new DeploymentOptions();
    	
    	options.getConfig().put("http.port", 8080);
    	
    	
    		Vertx vertx = Vertx.vertx();
    		vertx.deployVerticle(new BasicWebVerticle(), options);
    }
    
    @Override
    public void start() {
    	LOGGER.info("Verticle BasicWebVerticle Started");
    	
    	
    	Router router = Router.router(vertx);
    	
    	// API routing
    	router.get("/api/v1/products").handler(this::getAllStocks);
    	
    	
    	
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
	
	private void getAllStocks(RoutingContext routingContext) {
		
		JsonObject responseJson = new JsonObject();
		
		JsonArray items = new JsonArray();
		
//		JsonObject firstItem = new JsonObject();
//		firstItem.put("number", "123");
//		firstItem.put("description", "My item 123");
//		
//		items.add(firstItem);
//		
//		JsonObject secondItem = new JsonObject();
//		secondItem.put("number", "321");
//		secondItem.put("description", "My item 321");
//		
//		items.add(secondItem);
//		
//		responseJson.put("products", items);
		
		
		Stock firstItem = new Stock(23.2,"stock1");
		Stock secondItem = new Stock(23.2,"stock2");
		
		List<Stock> stocks = new ArrayList<Stock>();
		
		stocks.add(firstItem);
		stocks.add(secondItem);
		
		responseJson.put("products", stocks);
		
//		routingContext.response()
//		.setStatusCode(200)
//		.putHeader("content-type", "application/json")
//		.end(Json.encode(responseJson));
		
		routingContext.response()
		.setStatusCode(400)
		.putHeader("content-type", "application/json")
		.end(Json.encodePrettily(new JsonObject().put("error", "Could not find all products")));
		
	}

	@Override
    public void stop() {
    	
    }
    
   
}
