package com.barista.coffee.reportservice.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.barista.coffee.reportservice.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

	@Query(value = "select SUM(quantity) from Order order where date >= :date and status = :status")
	public Long numberOfCoffeesSoldToday(@Param("date") Date date, @Param("status") String status);

	@Query("select productname, SUM(quantity) from Order order where date >= :date and status = :status group by productname, quantity")
	public Object mostSoldCoffeeOfTheDay(@Param("date") Date date, @Param("status") String status);
	
}
