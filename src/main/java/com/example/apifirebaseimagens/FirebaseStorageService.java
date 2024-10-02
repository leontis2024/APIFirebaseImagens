package com.example.apifirebaseimagens;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Service
public class FirebaseStorageService {
    private String uploadFile(File file, String fileName,String nomeTabela) throws IOException {
        String folderName = nomeTabela+"/";
        BlobId blobId = BlobId.of("leontisfotos.appspot.com", folderName+fileName); // Replace with your bucker name
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/"+fileName.substring(fileName.lastIndexOf(".") +1)).build();
        InputStream inputStream = FirebaseStorageService.class.getClassLoader().getResourceAsStream("leontisfotos-firebase-adminsdk-3nepr-a0c5dd1819.json"); // change the file name with your one
        Credentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/leontisfotos.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, URLEncoder.encode(folderName+fileName, StandardCharsets.UTF_8));
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
            fos.close();
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") );
    }


    public String upload(MultipartFile multipartFile, String nomeTabela, String id) {
        try {
            String fileName = multipartFile.getOriginalFilename();                        // to get original file name
            fileName =nomeTabela+id.concat(this.getExtension(fileName));  // to generated random string values for file name.

            File file = this.convertToFile(multipartFile, fileName);                      // to convert multipartFile to File
            String URL = this.uploadFile(file, fileName,nomeTabela);                                   // to get uploaded file link
            file.delete();
            return URL;
        } catch (Exception e) {
            e.printStackTrace();
            return "Image couldn't upload, Something went wrong";
        }
    }

}
