package com.ertelecom.server;

import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {
    // поиск по статусу
    public static Specification<Task> statusContains(Long statusId) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status").get("statusId"), statusId);
    }

    // поиск по имени владельца
    public static Specification<Task> ownerNameContains(String ownerName) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) ->
                                        criteriaBuilder.like( root.get("owner").get("userName"), "%"+ownerName+"%");
    }

    // кому назначена задача
    public static Specification<Task> executorIdContains(Long executorId) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("executor").get("userId"), executorId);
    }

}
