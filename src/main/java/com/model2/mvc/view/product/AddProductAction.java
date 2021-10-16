package com.model2.mvc.view.product;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;


public class AddProductAction extends Action {

	@Override
	public String execute(HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println("AddProductAction");
		Product product=new Product();
		//String manuDate = CommonUtil.toSsnStr(request.getParameter("manuDate"));
		//System.out.println(">>>> "+request.getParameter("manuDate"));
		String manuDate = request.getParameter("manuDate").replace("-", "");
		//System.out.println(">>>> "+manuDate);
		product.setProdName(request.getParameter("prodName"));
		product.setManuDate(manuDate);	// YYmmdd
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		product.setProdDetail(request.getParameter("prodDetail"));
//		Calendar cal = Calendar.getInstance();
//		Date date = new Date();
//		SimpleDateFormat sdformat = new SimpleDateFormat("yy/M/dd");
//		String ss=sdformat.format(new java.util.Date());
//		
//		product.setRegDate(cal);
//		
//		System.out.println("Addproduct"+product);
//		
		ProductService service=new ProductServiceImpl();
		service.addProduct(product);
		HashMap<String,Object> map = new HashMap ();
		map.put("prodName", request.getParameter("prodName"));
		map.put("manuDate", request.getParameter("manuDate"));
		map.put("price", request.getParameter("price"));
		map.put("fileName", request.getParameter("fileName"));
		map.put("prodDetail", request.getParameter("prodDetail"));
		
		HttpSession session=request.getSession();
		map.get("prodName");
		System.out.println("session >>"+map);
		session.setAttribute("product", map);
		
		return "redirect:/product/addProduct.jsp";
	}
}