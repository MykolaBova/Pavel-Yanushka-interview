package org.pavel.yanushka.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableAutoConfiguration
@ComponentScan("org.pavel.yanushka.server")
public class ServerApplication {
    @Autowired
    Environment env;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
