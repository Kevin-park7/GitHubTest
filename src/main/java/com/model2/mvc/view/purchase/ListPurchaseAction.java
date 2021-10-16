package com.model2.mvc.view.purchase;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	public ListPurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Search search=new Search();
		Purchase purchase= new Purchase();
		HttpSession session = request.getSession();
		User buyer = (User) session.getAttribute("user");
//		UserVO buyerVO = new UserVO();
//		buyerVO.setUserId(userId);
//		purchaseVO.setBuyer(buyerVO);

	
		int page=1;
		if(request.getParameter("page") != null)
			page=Integer.parseInt(request.getParameter("page"));
		System.out.println("ListPurchaseAction >>> "+ page+"    "+request.getParameter("searchCondition")+"    "+
				request.getParameter("searchKeyword"));
		search.setCurrentPage(page);
		search.setSearchCondition(request.getParameter("searchCondition"));
		String keyword = (request.getParameter("searchKeyword")==null)? "": request.getParameter("searchKeyword");
		search.setSearchKeyword(keyword);
		
		String pageUnit=getServletContext().getInitParameter("pageSize");
		search.setPageSize(Integer.parseInt(pageUnit));
		
		PurchaseService service=new PurchaseServiceImpl();
		System.out.println("ListProductAction >>> searchCondition: "+search.getSearchCondition()+
				" searchKeyword: "+search.getSearchKeyword()+" page unit: "+search.getPageSize());
		HashMap<String,Object> map = service.getPurchaseList(search, buyer.getUserId());
		System.out.println("map에 저장된 결과"+map);
		request.setAttribute("map", map);
		request.setAttribute("searchVO", search);
		request.setAttribute("pageUnit", pageUnit);
		
		
		return "forward:/purchase/listPurchase.jsp";
	}

}
