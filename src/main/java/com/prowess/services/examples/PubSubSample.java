package com.prowess.services.examples;

import com.google.common.flogger.FluentLogger;
import com.google.gson.Gson;
import com.prowess.services.examples.pubsub.PubSubPublisher;
import com.prowess.services.examples.schemas.Customer;
import com.prowess.services.examples.schemas.PublisherPayload;
import com.prowess.services.examples.schemas.PubsubMessage;
import com.prowess.services.examples.utils.GCPOAuthTokenClient;

import java.util.Base64;
import java.util.logging.Level;

import static org.javalite.app_config.AppConfig.p;

public class PubSubSample {

    // Logger
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public static void main(String[] args) {
        logger.at(Level.INFO).log("APPLICATION STARTED");

        try {
            // Get Access Token
            GCPOAuthTokenClient client = new GCPOAuthTokenClient("https://www.googleapis.com/auth/pubsub");
            String access_token = client.getAccessToken();
            //logger.atInfo().log("GCP Access Token: " + access_token);

            // Create a publisher instance
            PubSubPublisher publisher = new PubSubPublisher(access_token, p("cloud.service.pubsub.topic.name"));

            // Create Customer data object
            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Doe");
            customer.setMiddleName("M");
            customer.setStreet("Henderson Street");
            customer.setCity("Orlando");
            customer.setState("FL");
            customer.setZipcode("31121");

            // Setting publisher payload
            PublisherPayload payload = new PublisherPayload();
            PubsubMessage data = new PubsubMessage();

            // Encode Customer Object to Base64
            Gson gson = new Gson();
            String encodedString = Base64.getEncoder().encodeToString(gson.toJson(customer).getBytes());
            logger.atInfo().log("Base64 Encoded String: " + encodedString);

            // Set data
            data.setData(encodedString);

            // Initialize messages array, set length to 1 and add previously created message
            PubsubMessage[] datas = new PubsubMessage[1];
            datas[0] = data;

            payload.setMessages(datas);

            // Create JSON payload from Payload object
            String requestJson = gson.toJson(payload);
            // Log JSON payload
            logger.atInfo().log(requestJson);

            // Publish data to Google Pub/Sub
            String response = publisher.publish(requestJson);
            // Log response from Google Pub/Sub
            logger.atInfo().log(response);
        } catch (Exception ex) {
            logger.atInfo().withCause(ex).log("Errored occured while publishing message");
        }

        logger.at(Level.INFO).log("APPLICATION COMPLETED");

    }
}
