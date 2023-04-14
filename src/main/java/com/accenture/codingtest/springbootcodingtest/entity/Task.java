package com.accenture.codingtest.springbootcodingtest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "TASK")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "TITLE",nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS",nullable = false)
    private String status;

    @OneToOne(mappedBy = "task")
    @JsonBackReference
    private User user_id;
}
