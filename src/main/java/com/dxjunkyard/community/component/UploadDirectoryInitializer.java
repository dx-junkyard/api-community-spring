package com.dxjunkyard.community.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadDirectoryInitializer implements CommandLineRunner {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void run(String... args) throws Exception {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();  // ディレクトリが存在しない場合、作成する
            System.out.println("ディレクトリが作成されました: " + uploadDir);
        }
    }
}

