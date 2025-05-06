package com.backend.BiteBox.Repository;

import com.backend.BiteBox.entity.FoodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends MongoRepository<FoodEntity, String> {
    // This interface will automatically provide CRUD operations for FoodEntity
    // You can add custom query methods here if needed
}
