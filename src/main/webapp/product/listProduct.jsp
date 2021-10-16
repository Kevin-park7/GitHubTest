<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ page import="java.util.*"  %>
<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.Search" %>
<%@ page import="com.model2.mvc.common.*" %>

<%--
	request.setCharacterEncoding("UTF-8"); //받아올 데이터의 인코딩
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map");
	Search search=(Search)request.getAttribute("search");
	
	List<User> list= (List<User>)request.getAttribute("list");
	Page resultPage=(Page)request.getAttribute("resultPage");
	
	Search search = (Search)request.getAttribute("search");
	//==> null 을 ""(nullString)으로 변경
	String searchCondition = CommonUtil.null2str(search.getSearchCondition());
	String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
	
	String curPageStr = (String) request.getParameter("page");
	int curPage = 1;
	if (curPageStr != null)
		curPage = Integer.parseInt(curPageStr);
	
	String menu = (String) request.getParameter("menu");
	System.out.println(">>>>>>> "+ menu+"    "+ curPage);
	System.out.println("page 확인 >>>>>>>"+(curPage-1)/5);
	//int i = Integer.parseInt( request.getParameter("page"));
	//System.out.println("page 확인 >>>>>>>"+(( Integer.parseInt( request.getParameter("page"))-1)%5));
	// Page's Menu--> manage, search / page --> # / 
	if(menu.equals("manage")){
		pageContext.forward("updateTranCodeByProd.jsp");
		--%>
		
		<%
		List<Product> list= (List<Product>)request.getAttribute("list");
		Page resultPage=(Page)request.getAttribute("resultPage");
		
		Search search = (Search)request.getAttribute("search");
		//==> null 을 ""(nullString)으로 변경
		String searchCondition = CommonUtil.null2str(search.getSearchCondition());
		String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());
		
		String curPageStr = (String) request.getParameter("page");
		int curPage = 1;
		if (curPageStr != null)
			curPage = Integer.parseInt(curPageStr);
		
		System.out.println(resultPage +"    "+ resultPage.getTotalCount());
		
		String menu = (String) request.getParameter("menu");
		System.out.println(">>>>>>> "+ menu+"    "+ curPage);
		System.out.println("page 확인 >>>>>>>"+(curPage-1)/5);
		//int i = Integer.parseInt( request.getParameter("page"));
		//System.out.println("page 확인 >>>>>>>"+(( Integer.parseInt( request.getParameter("page"))-1)%5));
		// Page's Menu--> manage, search / page --> # / 
		if(menu.equals("manage")){
			pageContext.forward("updateTranCodeByProd.jsp");
		%>
	
		<%
	}else{		// search
		%>			<html>
			<head>
			<title>상품 목록조회</title>
			
			<link rel="stylesheet" href="/css/admin.css" type="text/css">
			
			<script type="text/javascript">
			// 검색 / page 두가지 경우 모두 Form 전송을 위해 JavaScrpt 이용  
			function fncGetUserList(currentPage) {
				document.getElementById("currentPage").value = currentPage;
			   	document.detailForm.submit();		
			}
			</script>
			</head>
			
			<body bgcolor="#ffffff" text="#000000">
			
			<div style="width:98%; margin-left:10px;">
			
			<form name="detailForm" action="/listProduct.do?menu=search" method="post">
			
			<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
				<tr>
					<td width="15" height="37">
						<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
					</td>
					<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">상품 목록조회</td>
							</tr>
						</table>
					</td>
					<td width="12" height="37">
						<img src="/images/ct_ttl_img03.gif" width="12" height="37"/>
					</td>
				</tr>
			</table>
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
				<tr>
	
					<td align="right">
						<select name="searchCondition" class="ct_input_g" style="width:80px">
				
							<option value="0" <%= (searchCondition.equals("0") ? "selected" : "")%>>상품번호</option>
							<option value="1" <%= (searchCondition.equals("1") ? "selected" : "")%>>상품명</option>
							<option value="2" <%= (searchCondition.equals("2") ? "selected" : "")%>>상품가격</option>
						</select>
						<input 	type="text" name="searchKeyword"  value="<%=searchKeyword %>" 
										class="ct_input_g" style="width:200px; height:19px" >
					</td>
					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="17" height="23">
									<img src="/images/ct_btnbg01.gif" width="17" height="23">
								</td>
								<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
									<a href="javascript:fncGetProductList();">검색</a>
								</td>
								<td width="14" height="23">
									<img src="/images/ct_btnbg03.gif" width="14" height="23">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
				<tr>
					<td colspan="11" >
	전체  <%= resultPage.getTotalCount() %> 건수,	현재 <%= resultPage.getCurrentPage() %> 페이지
		</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">상품명</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">가격</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">등록일</td>	
					<td class="ct_line02"></td>
					<td class="ct_list_b">현재상태</td>	
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
					
				<% 	
					
					//int no=list.size();
					for(int i=0; i<list.size(); i++) {
						Product vo = list.get(i);
				%>
				<tr class="ct_list_pop">
					<td align="center"><%= 3*(curPage-1)+i+1 %></td>
					<!--고정된 값이 있으면 고정된값으로 변경 3=> 고정된갑-->
					<td></td>
					
							<td align="left">
							<a href="/getProduct.do?prodNo=<%=vo.getProdNo() %>"><%=vo.getProdName() %></a></td>
					
					<td></td>
					<td align="left"><%=vo.getPrice() %></td>
					<td></td>
					<td align="left"><%=vo.getRegDate() %></td>
					<td></td>
					<td align="left">
					<%if(vo.getProTranCode()==null){ %>
						재고 있음
						<%}else if(vo.getProTranCode().equals("001")){ %>
						구매완료
						<%}else if(vo.getProTranCode().equals("002")){ %>
						배송중
						<%}else if(vo.getProTranCode().equals("003")){ %>
						배송완료
						<%} %>
					</td>	
				</tr>
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>	
				
				<%} %>
				
			</table>
			
			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
				<tr>
					<td align="center">
					<%-- if( resultPage.getCurrentPage() <= resultPage.getPageUnit() ){ %>
							◀ 이전
					<% }else{ %>
							<a href="javascript:fncGetUserList('<%=resultPage.getCurrentPage()-1%>')">◀ 이전</a>
					<% } %>

					<%	for(int i=resultPage.getBeginUnitPage();i<= resultPage.getEndUnitPage() ;i++){	%>
							<a href="javascript:fncGetUserList('<%=i %>');"><%=i %></a>
					<% 	}  %>
	
					<% if( resultPage.getEndUnitPage() >= resultPage.getMaxPage() ){ %>
							이후 ▶
					<% }else{ %>
							<a href="javascript:fncGetList('<%=resultPage.getEndUnitPage()+1%>')">이후 ▶</a>
					<% }--%>
					
					<%
					if(((curPage-1)/5)!=0){
					%>
					<a href="/listProduct.do?page=<%=((curPage-1)/5)*5 %>&searchCondition=<%= search.getSearchCondition() %>&searchKeyword=<%=search.getSearchKeyword() %>&menu=search">◀ 이전 </a>
					<%
					}
					%>
					
					<%
					 System.out.println("page >>>>"+resultPage.getMaxPage());
					 for(int i=((curPage-1)/5)*5+1;i<=(((curPage-1)/5)+1)*5;i++){
						 int y=i-1%5;
						if(i <= resultPage.getMaxPage()){
					%>
						<a href="/listProduct.do?page=<%=i%>&searchCondition=<%= search.getSearchCondition() %>&searchKeyword=<%=search.getSearchKeyword() %>&menu=search"><%=i %> </a>
					<%
						}
						}
					 if(((curPage-1)/5) < ((resultPage.getMaxPage()-1)/5)){
					%>	
					<a href="/listProduct.do?page=<%=(((curPage-1)/5)+1)*5+1 %>&searchCondition=<%= search.getSearchCondition() %>&searchKeyword=<%=search.getSearchKeyword() %>&menu=search">이후 ▶ </a>
					<%} %>
			    	</td>
				</tr>
			</table>
			<!--  페이지 Navigator 끝 -->
			
			</form>
			
			</div>
			</body>
			</html>
					
		
		<%
	}
%>
