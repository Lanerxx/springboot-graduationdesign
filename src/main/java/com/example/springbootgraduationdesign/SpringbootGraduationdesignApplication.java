package com.example.springbootgraduationdesign;

import com.example.springbootgraduationdesign.repository.impl.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class SpringbootGraduationdesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootGraduationdesignApplication.class, args);
    }

}
