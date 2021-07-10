package com.practice.libraryservice.repository;

import com.practice.libraryservice.entity.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library,Integer> {

    Optional<Library> findByUserId(Integer integer);
}
