package com.example.apifirebaseimagens;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        FileInputStream serviceAccount =
//                src/main/resources/leontisfotos-firebase-adminsdk-3nepr-4b41f628f0.json
                new FileInputStream("/app/leontisfotos-firebase-adminsdk-3nepr-a0c5dd1819.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("leontisfotos.appspot.com")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
