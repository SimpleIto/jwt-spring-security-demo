package top.simpleito.jwtspringsecuritydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class JwtSpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtSpringSecurityDemoApplication.class, args);
    }

}
