package hadesky.dao;

import java.util.List;

import hadesky.domain.Category;

public interface CategoryDao {

	void addCategory(Category category);

	Category findOneCategory(String id);

	List<Category> findAllCategoryes();

}
