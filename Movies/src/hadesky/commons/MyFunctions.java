package hadesky.commons;

import hadesky.domain.Category;
import hadesky.service.impl.BusinessImpl;


public class MyFunctions {
	private static BusinessImpl s = new BusinessImpl();
	public static String showCategoryName(String categoryId){
		Category c = s.findOneCategory(categoryId);
		if(c!=null){
			return c.getName();
		}
		return null;
	}
}