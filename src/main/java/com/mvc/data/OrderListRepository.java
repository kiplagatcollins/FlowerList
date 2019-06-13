package com.mvc.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderListRepository extends JpaRepository<OrderList, Integer> {
	public List<OrderList> findByCustomerName(String customerName);
	@Query(value="select * from orderlist join OrderList_orderlist where ", nativeQuery = true)
	public List<OrderList> findOrders();
	
}
