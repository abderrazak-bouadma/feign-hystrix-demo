package io.unicorn;

import io.unicorn.domain.Todo;
import io.unicorn.domain.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class TodoApiApplication {

    @Autowired
    private TodoRepository todoRepository;

    public static void main(String[] args) {
        SpringApplication.run(TodoApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> Stream.generate(() -> 1).limit(50)
                .forEach(e -> {
                    Todo entity = new Todo();
                    entity.setTitle("Generated Hello world " + System.currentTimeMillis());
                    todoRepository.save(entity);
                });
    }

    @RestController
    @RequestMapping("/api/todos")
    public class TodoController {

        @Autowired
        private TodoRepository todoRepository;

        @GetMapping
        public ResponseEntity<List<Todo>> findAll() {
            return ResponseEntity.ok().body(todoRepository.findAll());
        }

        @PostMapping
        public ResponseEntity<Todo> create(@RequestParam String todoTitle) {
            Todo todo = new Todo();
            todo.setTitle(todoTitle);
            return ResponseEntity.ok().body(todoRepository.save(todo));
        }

        @GetMapping("/{id}")
        ResponseEntity<Todo> getById(@PathVariable @NotNull Long id) {
            Todo todo = todoRepository.findOne(id);
            if (todo == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(todo);
        }
    }

}
