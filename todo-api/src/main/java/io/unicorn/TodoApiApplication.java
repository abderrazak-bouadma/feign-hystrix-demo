package io.unicorn;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class TodoApiApplication {

    private static final List<Todo> db = Lists.newArrayList();
    private static int count = 0;

    public static void main(String[] args) {
        SpringApplication.run(TodoApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            for (int i = 0; i < 100; i++) {
                Todo t = new Todo();
                t.setId(count++);
                t.setTitle("Hello there " + System.currentTimeMillis());
                db.add(t);
            }
        };
    }


    @RestController
    @RequestMapping("/api/todos")
    public class TodoController {

        @GetMapping
        public ResponseEntity<List<Todo>> findAll() {
            return ResponseEntity.ok().body(db);
        }

        @PostMapping
        public ResponseEntity<Todo> create(@RequestParam String todoTitle) {
            Todo todo = new Todo();
            todo.setTitle(todoTitle);
            todo.setId(count++);
            db.add(todo);
            return ResponseEntity.ok().body(todo);
        }
    }

    @Data
    public class Todo {
        private long id;
        private String title;
    }
}
