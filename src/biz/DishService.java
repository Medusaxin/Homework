package biz;

import java.util.ArrayList;

import bean.Dish;
import dao.DishDao;
import utils.DaoFactory;
import utils.PageModel;

public class DishService {
	
	private DishDao dishdao;

	public DishDao getDishdao() {
		return dishdao;
	}

	public void setDishdao(DishDao dishdao) {
		this.dishdao = dishdao;
	}
	
	/**
	 * @param pageNO
	 * @param pageSize
	 * @return
	 */
	public PageModel<Dish> findDish4PageList(int pageNO,int pageSize){
		dishdao = (DishDao) DaoFactory.newInstance("DishDao");
		String strsql = "select dishid Dishid,name Dishname,img Img,price Price from dish limit ?,?";
		int actualpageNO = (pageNO-1)*pageSize;
		Object[] params = {actualpageNO,pageSize};
		ArrayList<Dish> dishlist = dishdao.findDishes(strsql, params);
//		PageModel<Dish> pagemodel = new PageModel<Dish>();
//		pagemodel.setList(dishlist);
//		pagemodel.setPageNO(pageNO);
//		pagemodel.setPageSize(pageSize);
//		pagemodel.setTotalrecords(getTotalDishs());
		PageModel<Dish> pagemodel = new PageModel<Dish>(pageSize,pageNO,getTotalDishs(),dishlist);
		return pagemodel;
//		return dishdao.findDishs(strsql, params);
	}
	
	public int getTotalDishs(){
		dishdao = (DishDao) DaoFactory.newInstance("DishDao");
		String strsql = "select count(*) from dish";
		return dishdao.getTotalDishs(strsql);
	}

}
