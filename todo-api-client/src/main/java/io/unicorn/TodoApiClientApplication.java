package io.unicorn;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@EnableFeignClients(clients = {TodoApiClientApplication.TodoClient.class})
public class TodoApiClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApiClientApplication.class, args);
    }

    @FeignClient(url = "http://localhost:8080", name = "todo-api", fallback = TodoClientFallback.class)
    public interface TodoClient {

        @RequestMapping(value = "/api/todos", method = RequestMethod.GET)
        ResponseEntity<List<TodoDto>> getAll();
    }

    @Component
    public class TodoClientFallback implements TodoClient {
        @Override
        public ResponseEntity<List<TodoDto>> getAll() {
            return ResponseEntity.noContent().build();
        }
    }


    @RestController
    @RequestMapping("/api/client/todos")
    public class ClientTodoController {

        @Autowired
        private TodoClient todoClient;

        @GetMapping
        public ResponseEntity<List<TodoDto>> all() {
            return todoClient.getAll();
        }
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream");
    }
}
