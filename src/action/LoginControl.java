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
	private int pageSize = 6;// �ŵ������ļ�
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginControl() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /***
	 * ��¼��֤
	 * 
	 * @param request
	 * @param response
	 */
	private void loginCheck(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// step 1 ��ȡ�û��ύ���û����Ϳ���
		String username = request.getParameter("loginName");
		String password = request.getParameter("loginPass");
		Users loginuser = new Users();
		loginuser.setUsername(username);
		loginuser.setPassword(password);
		// step 2 ���ݿ���֤�û�
		UserService userserv = new UserService();
		RequestDispatcher rd = null;
		if (userserv.validateUser(loginuser)) {
			// ��֤ͨ�� ����ת��show.jsp
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
			// �������µ�¼
			response.sendRedirect("login.html");
			// request.getRequestDispatcher("login.html").forward(request,
			// response);
		}
	}
    
	private void pageListView(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ��ǰҳ��
		logger.debug(request.getParameter("pageNO"));
		int pageNO = Integer.parseInt(request.getParameter("pageNO"));
		// ��ҳ��ѯ
		// int pageSize = 6;
		// ����pageModel����
		DishService dishserv = new DishService();
		PageModel<Dish> pagemodel = dishserv.findDish4PageList(pageNO, pageSize);
		// ��ת��showҳ��
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
			// ��¼
			loginCheck(request, response);
			break;
		case "pagelist":
			// ��ҳ��ʾ
			pageListView(request, response);
			break;
		case "detail":
			// ��ʾĳһ����Ʒ����ϸ��Ϣ
		case "cart":
			// ��ӵ����ﳵ
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
