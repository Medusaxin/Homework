package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import bean.Dish;
import bean.Users;
import biz.DishService;
import biz.UserService;
import utils.PageModel;

/**
 * Servlet implementation class LoginControl
 */
@WebServlet("/LoginControl")
public class LoginControl extends baseControl {
	private static final long serialVersionUID = 1L;
	private int pageSize = 6;// 放到配置文件
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControl() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /***
	 * 登录验证
	 * 
	 * @param request
	 * @param response
	 */
	private void loginCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// step 1 获取用户提交的用户名和口令
		String username = request.getParameter("loginName");
		String password = request.getParameter("loginPass");
		Users loginuser = new Users();
		loginuser.setUsername(username);
		loginuser.setPassword(password);
		// step 2 数据库验证用户
		UserService userserv = new UserService();
		RequestDispatcher rd = null;
		if (userserv.validateUser(loginuser)) {
			// 验证通过 ，跳转到show.jsp
			HttpSession session = request.getSession(true);
			session.setAttribute("loginuser", loginuser);
			DishService dishserv = new DishService();
			// int pageSize = 6;
			int pageNO = 1;
			PageModel<Dish> pagemodel = dishserv.findDish4PageList(pageNO, pageSize);
			request.setAttribute("dishlist", pagemodel.getList());
			logger.debug(pagemodel.getTotalrecords());
			logger.info(pagemodel.getTotalrecords());
			logger.warn(pagemodel.getTotalrecords());
			logger.error(pagemodel.getTotalrecords());
			request.setAttribute("pageModel", pagemodel);
			// rd = request.getRequestDispatcher("show.jsp");
			rd = request.getRequestDispatcher("show2.jsp?pageNO=1&totalpages=" + pagemodel.getTotalPages());
			rd.forward(request, response);
		} else {
			// 否则，重新登录
			response.sendRedirect("login.html");
			// request.getRequestDispatcher("login.html").forward(request,
			// response);
		}
	}
    
	private void pageListView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取当前页号
		logger.debug(request.getParameter("pageNO"));
		int pageNO = Integer.parseInt(request.getParameter("pageNO"));
		// 分页查询
		// int pageSize = 6;
		// 生成pageModel对象
		DishService dishserv = new DishService();
		PageModel<Dish> pagemodel = dishserv.findDish4PageList(pageNO, pageSize);
		// 跳转到show页面
		logger.debug(pagemodel.getList());
		request.setAttribute("dishlist", pagemodel.getList());
		request.setAttribute("pageModel", pagemodel);
		RequestDispatcher rd = request
				.getRequestDispatcher("show2.jsp?pageNO=" + pageNO + "&totalpages=" + pagemodel.getTotalPages());
		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String actiontype = request.getParameter("actiontype");
		switch (actiontype) {
		case "login":
			// 登录
			loginCheck(request, response);
			break;
		case "pagelist":
			// 分页显示
			pageListView(request, response);
			break;
		case "detail":
			// 显示某一个菜品的详细信息
		case "cart":
			// 添加到购物车
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
