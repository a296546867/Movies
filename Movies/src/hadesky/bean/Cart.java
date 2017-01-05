package hadesky.bean;

import hadesky.domain.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


public class Cart implements Serializable {
	//key:�������Ӧ���鼮��id��value��������
	private Map<String, CartItem> items = new HashMap<String, CartItem>();
	private int totalQuantity;
	private float amount;//�ܽ������
	public Map<String, CartItem> getItems() {
		return items;
	}
	public void setItems(Map<String, CartItem> items) {
		this.items = items;
	}
	public int getTotalQuantity() {
		totalQuantity = 0;
		for(Map.Entry<String, CartItem> item:items.entrySet()){
			totalQuantity+=item.getValue().getQuantity();
		}
		return totalQuantity;
	}
	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	public float getAmount() {
		amount=0;
		for(Map.Entry<String, CartItem> item:items.entrySet()){
			amount+=item.getValue().getTotalPrice();
		}
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	//添加到购物车
	public void addBook(Book book){
		if(items.containsKey(book.getId())){
			//�鼮�Ѿ�����
			CartItem item = items.get(book.getId());
			item.setQuantity(item.getQuantity()+1);
		}else{
			CartItem item = new CartItem(book);
			item.setQuantity(1);
			items.put(book.getId(), item);
		}
	}
}
