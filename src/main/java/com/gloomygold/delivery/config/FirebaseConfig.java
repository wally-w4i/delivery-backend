package com.gloomygold.delivery.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        // TODO: Replace "firebase-adminsdk.json" with the actual path to your service account key file.
        // It's recommended to store this file outside the source control for production.
        // For development, placing it in src/main/resources is common.
        InputStream serviceAccount = new ClassPathResource("firebase-adminsdk.json").getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                // Optionally, set the database URL if you're using Realtime Database
                // .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        } else {
            return FirebaseApp.getInstance();
        }
    }
}
