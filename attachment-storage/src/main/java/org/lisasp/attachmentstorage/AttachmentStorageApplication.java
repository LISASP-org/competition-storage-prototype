package org.lisasp.attachmentstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AttachmentStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttachmentStorageApplication.class, args);
    }

}
