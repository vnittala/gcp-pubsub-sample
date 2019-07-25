package com.prowess.services.examples.pubsub;

import com.google.common.flogger.FluentLogger;
import org.javalite.http.Http;
import org.javalite.http.Post;
import org.javalite.http.Put;
import static org.javalite.app_config.AppConfig.p;

public class PubSubPublisher {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();
    private String access_token;
    private String topic_name;
    private String uri;

    public PubSubPublisher(String access_token, String topic_name) {
        this.access_token = "Bearer " + access_token;
        this.topic_name = topic_name;
        this.uri = p("cloud.service.pubsub.url") + topic_name + ":publish";

        logger.atInfo().log("URI: " + uri);
    }


    public String publish(String message) {
        Post post = Http.post(uri, message)
                      .header("Authorization", access_token)
                      .header("Content-Type", "application/json");
        return post.text();
    }
}
