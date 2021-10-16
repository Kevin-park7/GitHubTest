package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction extends Action {

	public AddPurchaseViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("jasdlfj"+request.getParameter("prodNo"));
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service=new ProductServiceImpl();
		Product vo=service.getProduct(prodNo);
		System.out.println("purchase ¿¡getprodNo>>>>>:"+prodNo);
		System.out.println("purchase ¿¡getprodNo¿¡ vo>>>>>:"+vo);
		request.setAttribute("vo", vo);
		
		
		return "forward:/purchase/addpurchaseView.jsp";
	}

}
