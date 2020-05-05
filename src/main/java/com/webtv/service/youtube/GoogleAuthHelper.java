package com.webtv.service.youtube;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.webtv.exception.ServerError;

public final class GoogleAuthHelper {
    /**
     * Define a global instance of the HTTP transport.
     */
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Define a global instance of the JSON factory.
     */
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();

    /**
     * This is the directory that will be used under the user's home directory where
     * OAuth tokens will be stored.
     */
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

    public static GoogleAuthorizationCodeFlow codeFlow(List<String> scopes, String credentialDatastore) {
        Reader clientSecretReader = new InputStreamReader(GoogleAuthHelper.class.getResourceAsStream("/client_secrets.json"));
        try {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);

            // This creates the credentials datastore at ~/.oauth-credentials/${credentialDatastore}
            FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY));
            DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);

            return new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
                    .build();
        } catch(IOException ex) {
            throw new ServerError(ex);
        }
    }
}