package com.georghs.finishedapi.database;

import java.util.List;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.mongo.FindOptions;
import io.vertx.ext.mongo.MongoClient;

public class MongoManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(MongoManager.class);

	private MongoClient mongoClient = null;

	public MongoManager(MongoClient mongoClient) {

		this.mongoClient = mongoClient;

	}

	public void registerConsumer(Vertx vertx) {
		
		vertx.eventBus().consumer("com.tomj.mongoservice", message -> {

			System.out.println("Recevied message: " + message.body());

			JsonObject inputJson = new JsonObject(message.body().toString());

			if (inputJson.getString("cmd").equals("findAll")) {
				getAllStocks(message);
			}

			if (inputJson.getString("cmd").equals("findById")) {
				
				LOGGER.info("stockId: " + inputJson.getString("stockId"));
				
				getStockById(message, inputJson.getString("stockId"));
			}

		});

	}

	private void getAllStocks(Message<Object> message) {

		FindOptions findOptions = new FindOptions();

		// findOptions.setLimit(1);

		mongoClient.findWithOptions("stocks", new JsonObject(), findOptions, results -> {

			try {
				List<JsonObject> objects = results.result();

				if (objects != null && objects.size() != 0) {

					System.out.println("Got some data len=" + objects.size());

					JsonObject jsonResponse = new JsonObject();

					jsonResponse.put("stocks", objects);

					message.reply(jsonResponse.toString());

				} else {

					JsonObject jsonResponse = new JsonObject();

					jsonResponse.put("error", "No items found");

					message.reply(jsonResponse.toString());
				}

			} catch (Exception e) {
				LOGGER.info("getAllStocks Failed exception e=", e.getLocalizedMessage());

				JsonObject jsonResponse = new JsonObject();

				jsonResponse.put("error", "Exception and No items found");

				message.reply(jsonResponse.toString());
			}

		});

	}
	
	private void getStockById(Message<Object> message, String productId) {

		FindOptions findOptions = new FindOptions();

		//findOptions.setLimit(1);

		System.out.println("findOptions " + findOptions);
		System.out.println("stockId "+ productId);
		
		mongoClient.findWithOptions("stocks", new JsonObject().put("_id", productId), findOptions, results -> {

			System.out.println("results "+ results);

			try {
				List<JsonObject> objects = results.result();

				if (objects != null && objects.size() != 0) {

					System.out.println("Got some data len=" + objects.size());

					JsonObject jsonResponse = objects.get(0);

					message.reply(jsonResponse.toString());

				} else {

					JsonObject jsonResponse = new JsonObject();

					jsonResponse.put("error", "No items found");

					message.reply(jsonResponse.toString());
				}

			} catch (Exception e) {
				LOGGER.info("getAllStocks Failed exception e=", e.getLocalizedMessage());

				JsonObject jsonResponse = new JsonObject();

				jsonResponse.put("error", "Exception and No items found");

				message.reply(jsonResponse.toString());
			}

		});

	}


}
