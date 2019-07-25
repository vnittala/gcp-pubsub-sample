package com.prowess.services.examples.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.common.collect.Lists;
import com.google.common.flogger.FluentLogger;

import static org.javalite.app_config.AppConfig.p;

import java.io.FileInputStream;
import java.io.IOException;


public class GCPOAuthTokenClient {

    // Logger class
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    // Variables
    private final String scope;
    private final String service_account_file = p("cloud.service.account.file.path");

    // Constructor
    public GCPOAuthTokenClient(String scope) {
        logger.atInfo().log("Received scope: " + scope);
        this.scope = scope;
    }

    public String getAccessToken() {

        logger.atInfo().log("Creating access token with scope: " + scope);
        try {
            GoogleCredential credential = GoogleCredential
                                            .fromStream(new FileInputStream(service_account_file))
                    .createScoped(Lists.newArrayList(scope));
            ;
            credential.refreshToken();
            String accessToken = credential.getAccessToken();
            return accessToken;

        } catch (IOException e) {
            logger.atInfo().withCause(e).log(e.getMessage());
            return null;
        }
    }

}
