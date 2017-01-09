package hadesky.dao;

import hadesky.domain.Order;

import java.util.List;

public interface OrderDao {

	void save(Order order);

	void update(Order order);

	Order findById(String orderId);

	Order findByOrderNum(String orderNum);
	/**
	 * ���ն����� ��������
	 * @param id
	 * @return
	 */
	List<Order> findOrdersByCustomerId(String customerId);

}
