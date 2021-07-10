package com.practice.libraryservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "library")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
@Setter
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "book_ids")
    private String bookIds;

    public List<Integer> getBookIds(){
        return Stream.of(bookIds.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public void setToBookIds(List<Integer> bookIdList){
        this.bookIds = bookIdList.stream().map(String::valueOf)
                .collect(Collectors.joining(","));
    }
}
