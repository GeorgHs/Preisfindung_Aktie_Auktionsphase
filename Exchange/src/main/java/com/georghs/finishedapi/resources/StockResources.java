package com.georghs.finishedapi.resources;

import java.util.ArrayList;
import java.util.List;

import com.georghs.finishedapi.entity.Stock;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


public class StockResources {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockResources.class);

	private Vertx vertx = null;
	
	
	public Router getAPISubRouter(Vertx vertx) {
		
		this.vertx = vertx;
		
		Router apiSubRouter = Router.router(vertx);
		
		// API routing
		apiSubRouter.route("/*").handler(this::defaultProcessorForAllAPI);
		
//		apiSubRouter.route("/v1/products*").handler(BodyHandler.create());
//		apiSubRouter.get("/v1/products").handler(this::getAllProducts);
//		apiSubRouter.get("/v1/products/:id").handler(this::getProductById);
//		apiSubRouter.post("/v1/products").handler(this::addStock);
//		apiSubRouter.put("/v1/products/:id").handler(this::updateStockById);
//		apiSubRouter.delete("/v1/products/:id").handler(this::deleteStockById);
		
		apiSubRouter.route("/v1/stocks*").handler(BodyHandler.create());
		apiSubRouter.get("/v1/stocks").handler(this::getAllStocks);
		apiSubRouter.get("/v1/stocks/:id").handler(this::getStocksById);
		apiSubRouter.post("/v1/stocks").handler(this::addStock);
		apiSubRouter.put("/v1/stocks/:id").handler(this::updateStockById);
		apiSubRouter.delete("/v1/stocks/:id").handler(this::deleteStockById);
		
		return apiSubRouter;
	}
	
	
	// Called for all default API HTTP GET, POST, PUT and DELETE
	public void defaultProcessorForAllAPI(RoutingContext routingContext) {
		
		String authToken = routingContext.request().getHeader("AuthToken");
				
//		if (authToken == null || !authToken.equals("123")) {
//			LOGGER.info("Failed basic auth check");
//
//			routingContext.response().setStatusCode(401).putHeader(HttpHeaders.CONTENT_TYPE, "application/json").end(Json.encodePrettily(new JsonObject().put("error", "Not Authorized to use these API's")));
//		}
//		else {
			LOGGER.info("Passed basic auth check");
			
			// Allowing CORS - Cross Domain API calls
			routingContext.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			routingContext.response().putHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE");
			
			// Call next matching route
			routingContext.next();			
//		}
		
	}
	
	// Get all products as array of products
	public void getAllStocks(RoutingContext routingContext) {
		
	    JsonObject cmdJson = new JsonObject();
	    
	    cmdJson.put("cmd", "findAll");

		vertx.eventBus().send("com.tomj.mongoservice", cmdJson.toString(), reply -> {
			
			if (reply.succeeded()) {
				
				JsonObject replyResults = new JsonObject(reply.result().body().toString());
				
				System.out.println("Got Reply message=" + replyResults.toString());
				
				routingContext.response()
					.setStatusCode(200)
					.putHeader("content-type", "application/json")
					.end(Json.encodePrettily(replyResults));


				
			}
			
		});

		

	}
	
	// Get one products that matches the input id and return as single json object
	public void getStocksById(RoutingContext routingContext) {
		
		final String stockId = routingContext.request().getParam("id");
		
	    JsonObject cmdJson = new JsonObject();
	    
	    cmdJson.put("cmd", "findById");
	    cmdJson.put("stockId", stockId);

		vertx.eventBus().send("com.tomj.mongoservice", cmdJson.toString(), reply -> {
			
			if (reply.succeeded()) {
				
				JsonObject replyResults = new JsonObject(reply.result().body().toString());
				
				System.out.println("Got Reply message=" + replyResults.toString());
				
				routingContext.response()
					.setStatusCode(200)
					.putHeader("content-type", "application/json")
					.end(Json.encodePrettily(replyResults));


				
			}
			
		});


		
	}
	
	// Insert one item passed in from the http post body return what was added with unique id from the insert
	public void addStock(RoutingContext routingContext) {
		
		JsonObject jsonBody = routingContext.getBodyAsJson();
		
		System.out.println(jsonBody);
		
		Double number = jsonBody.getDouble("currentPrice");
		String timeOfPriceSet = jsonBody.getString("timeOfPriceSet");
		
		Stock newItem = new Stock("", number, timeOfPriceSet);
		
		// Add into database and get unique id
		newItem.setId("556677");
		
		routingContext.response()
		.setStatusCode(201)
		.putHeader("content-type", "application/json")
		.end(Json.encodePrettily(newItem));

		
	}
	
	// Update the item based on the url product id and return update product info
	public void updateStockById(RoutingContext routingContext) {
		
		final String productId = routingContext.request().getParam("id");
		
		JsonObject jsonBody = routingContext.getBodyAsJson();


		Double number = jsonBody.getDouble("currentPrice");
		String timeOfPriceSet = jsonBody.getString("timeOfPriceSet");
		
		Stock updatedItem = new Stock(productId, number, timeOfPriceSet);

		routingContext.response()
		.setStatusCode(200)
		.putHeader("content-type", "application/json")
		.end(Json.encodePrettily(updatedItem));

		
	}
	
	// Delete item and return 200 on success or 400 on fail
	public void deleteStockById(RoutingContext routingContext) {
		
		final String productId = routingContext.request().getParam("id");

		routingContext.response()
		.setStatusCode(200)
		.putHeader("content-type", "application/json")
		.end();

		
	}

}
