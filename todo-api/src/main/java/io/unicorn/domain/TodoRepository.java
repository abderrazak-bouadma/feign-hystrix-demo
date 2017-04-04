package io.unicorn.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Abderrazak BOUADMA
 * on 04/04/2017.
 */
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
