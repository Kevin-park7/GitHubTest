package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action {

	public UpdatePurchaseAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		Purchase purchase = new Purchase();
		User user = new User();
		user.setUserId(request.getParameter("buyerId"));
		//String date = request.getParameter("divyDate");
		//java.util.Date u = new java.util.Date();
		//java.sql.Date s = new java.sql.Date(u.getDate());
		//System.out.println("sqldate typeº¯È¯"+s);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchase.setBuyer(user);
		PurchaseService service = new PurchaseServiceImpl();
		service.updatePurcahse(purchase);
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		//Purchase vo=service.getPurchase(tranNo);
		request.setAttribute("purch", purchase);
		return "forward:/purchase/getpurchase.jsp";
		
	}

}