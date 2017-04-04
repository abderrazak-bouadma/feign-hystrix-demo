package io.unicorn.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by Abderrazak BOUADMA
 * on 04/04/2017.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
}