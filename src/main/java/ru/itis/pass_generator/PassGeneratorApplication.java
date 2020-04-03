package ru.itis.pass_generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = "ru.itis.pass_generator")
@SpringBootApplication
public class PassGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassGeneratorApplication.class, args);
        Generator generator = new Generator();
        generator.start();
        generator.generate();
    }
}

// java -jar pass_generator-0.1.jar