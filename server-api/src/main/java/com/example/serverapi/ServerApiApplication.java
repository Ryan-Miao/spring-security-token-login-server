package com.example.serverapi;

import java.io.IOException;
import java.util.TimeZone;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Ryan Miao
 */
@MapperScan(basePackages = "com.example.serverapi.domain.security.mapper")
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class ServerApiApplication {

    public static ApplicationContext context;

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        context = SpringApplication.run(ServerApiApplication.class, args);
    }

    @Controller
    public static class HomeController {

        @GetMapping({"/"})
        public void api(HttpServletResponse response) throws IOException {
            response.sendRedirect("/api/swagger-ui.html");
        }
    }

}
