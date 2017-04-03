package io.unicorn;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class TodoApiApplication {

    private static int count = 0;
    public static void main(String[] args) {
        SpringApplication.run(TodoApiApplication.class, args);
    }

    @RestController
    @RequestMapping("/api/todos")
    public class TodoController {

        private final List<Todo> db = Lists.newArrayList();

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

        public int generateId() {
            return UUID.randomUUID().clockSequence();
        }
    }
}
