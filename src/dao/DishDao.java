package dao;

import java.util.ArrayList;

import bean.Dish;

public interface DishDao {
public Dish findDishByID(String dishid);
	
	public ArrayList<Dish> findDishes();
	
	public int getTotalDishs(String strsql);
	
	public ArrayList<Dish> findDishes(String strsql, Object[] params);
	
	public int insertDish(Dish dish);

}
