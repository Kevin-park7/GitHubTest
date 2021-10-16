package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class GetPurchaseAction extends Action {


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Purchase purchase = new Purchase();
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		PurchaseService service=new PurchaseServiceImpl();
		Purchase vo=service.getPurchase(tranNo);
		System.out.println("getprodNo>>>>>:"+tranNo);
		System.out.println("getprodNo¿¡ vo>>>>>:"+vo);
		request.setAttribute("vo", vo);
		request.setAttribute("pvo", purchase);
		
		return "forward:/purchase/getPurchaseView.jsp";
	}

}
