package com.ertelecom.lesson_15;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository <Status, Long> {
}
