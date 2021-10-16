package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseViewAction extends Action {

	public UpdatePurchaseViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub		
		Purchase purchase = new Purchase();
		
		//purchase.setBuyer(request.getParameter("buyerId"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		PurchaseService service = new PurchaseServiceImpl();
		service.updatePurcahse(purchase);
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase vo=service.getPurchase(tranNo);
		request.setAttribute("vo", vo);
		request.setAttribute("pvo", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}

}
