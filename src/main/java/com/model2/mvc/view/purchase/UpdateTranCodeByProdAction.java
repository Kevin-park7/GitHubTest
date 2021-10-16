package com.model2.mvc.view.purchase;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeByProdAction extends Action {


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
	
		Purchase purchase = new Purchase();
		int ProdNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("tranCode :"+request.getParameter("tranCode"));
		System.out.println("prodNo :"+request.getParameter("prodNo"));
		PurchaseService service1 = new PurchaseServiceImpl();
		purchase = service1.getPurchase2(ProdNo);
		purchase.setTranCode(request.getParameter("tranCode"));
		System.out.println("DB 들어가기전"+purchase);
		service1.updateTranCode(purchase);
		System.out.println("DB 들어간이후"+purchase);
		Search search=new Search();
		
		int currentPage=1;
		if(request.getParameter("page") != null)
			currentPage=Integer.parseInt(request.getParameter("page"));
		System.out.println("ListProductAction >>> "+ currentPage+"    "+request.getParameter("searchCondition")+"    "+
				request.getParameter("searchKeyword"));
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출 
				int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
				int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
				search.setPageSize(pageSize);
				System.out.println("pageSize :>>"+pageSize+"pageUnit"+pageUnit);
		
		ProductService service=new ProductServiceImpl();
		System.out.println("ListProductAction >>> searchCondition: "+search.getSearchCondition()+
				" searchKeyword: "+search.getSearchKeyword()+" page unit: "+search.getPageSize());
		Map<String,Object> map=service.getProductList(search);
		Page p = new Page( currentPage, Integer.parseInt(String.valueOf(map.get("totalCount"))), pageUnit,  pageSize );
			
		request.setAttribute("resultPage", p);
		System.out.println(map.get("list"));
		request.setAttribute("list", (List<Product>)map.get("list"));
		request.setAttribute("search", search);
		
		return "forward:/product/listProduct.jsp?menu=manage";
	}

}
