<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>

<%
	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map");
	Search search=(Search)request.getAttribute("searchVO");
	
	int total=0;
	ArrayList<Purchase> list=null;
	if(map != null){
		total=((Integer)map.get("count")).intValue();
		list=(ArrayList<Purchase>)map.get("list");
	}
	
	int currentPage=search.getCurrentPage();
	
	int totalPage=0;
	if(total > 0) {
		totalPage= total / search.getPageSize() ;
		if(total%search.getPageSize() >0)
			totalPage += 1;
	}
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
	//if(menu.equals("manage")){
		//pageContext.forward("updateTranCodeByProd.jsp");
%>



<html>
<head>
<title>구매 목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
	function fncGetUserList() {
		document.detailForm.submit();
	}
</script>
</head>

<body bgcolor="#ffffff" text="#000000">
<div style="width: 98%; margin-left: 10px;">

<form name="detailForm" action="/listUser.do" method="post">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">구매 목록조회</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">
	<tr>
		<td colspan="11">전체 <%=total %> 건수, 현재 <%=currentPage%> 페이지</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">회원명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">전화번호</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">배송현황</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">정보수정</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	<%
	int no=list.size();
	for(int i=0; i<list.size(); i++){
		Purchase vo = (Purchase)list.get(i);
	
	%>

	<tr class="ct_list_pop">
		<td align="center">
			<a href="/getPurchase.do?tranNo=<%=vo.getTranNo() %>">	<%=list.size() * (curPage-1)+i+1 %></a>
		</td>
		<td></td>
		<td align="left">
			<a href="/getUser.do?userId=<%=vo.getBuyer().getUserId()%>"><%=vo.getBuyer().getUserId()%></a>
		</td>
		<td></td>
		<td align="left"><%=vo.getReceiverName() %></td>
		<td></td>
		<td align="left"><%=vo.getReceiverPhone() %></td>
		<td></td>
		<td align="left">현재
		<% 
				
				if(vo.getTranCode().equals("1  ")){%>
					구매완료
				<%}else if(vo.getTranCode().equals("001")) {%>
					구매완료
				<%}else if(vo.getTranCode().equals("002")) {%>
					판매중
				<%}else if(vo.getTranCode().equals("003")) {%>
					배송완료 
				<%}else%>
					
				상태 입니다.</td>
				
		<td>
		 
		 </td>
		<td align="left">
			<%if(vo.getTranCode().equals("002")){ %><a href="/updateTranCode.do?tranNo=<%=vo.getTranNo() %>&tranCode=003">상품도착
			</a>
		 <%} %>
		</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>
	<%} %>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
		 <%
					if(((curPage-1)/5)!=0){
					%>
					<a href="/listPurchase.do?page=<%=((curPage-1)/5)*5 %>&searchCondition=<%= search.getSearchCondition() %>&searchKeyword=<%=search.getSearchKeyword() %>&menu=manage">◀ 이전 </a>
					<% 
					}
					%>
					
					<%
					 System.out.println("page >>>>"+totalPage);
					 for(int i=((curPage-1)/5)*5+1;i<=(((curPage-1)/5)+1)*5;i++){
						 int y=i-1%5;
						if(i <= totalPage){
					%>
						<a href="/listPurchase.do?page=<%=i%>&searchCondition=<%= search.getSearchCondition() %>&searchKeyword=<%=search.getSearchKeyword() %>&menu=manage"><%=i %> </a>
					<%
						}
						}
					 if(((curPage-1)/5) < ((totalPage-1)/5)){
					%>	
					<a href="/listPurchase.do?page=<%=(((curPage-1)/5)+1)*5+1 %>&searchCondition=<%= search.getSearchCondition() %>&searchKeyword=<%=search.getSearchKeyword() %>&menu=manage">이후 ▶ </a>
					<%} %>
		</td>
	</tr>
</table>

<!--  페이지 Navigator 끝 -->
</form>

</div>

</body>
</html>