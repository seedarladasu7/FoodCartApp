package com.service.food.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.service.food.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer>{
	
	Optional<List<FoodItem>> findByVendorIdAndDate(Integer vendorId, String date);
	
	@Query("select u from FoodItem u where u.itemName like %:itemName% and date =:date")
	Optional<List<FoodItem>> findAllByItemName(@Param("itemName") String itemName, String date);

}
