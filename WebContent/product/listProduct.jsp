<%@page import="com.model2.mvc.service.purchase.vo.PurchaseVO"%>
<%@page import="com.model2.mvc.service.product.vo.ProductVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"   pageEncoding="EUC-KR"%>
<%@page import="com.model2.mvc.common.SearchVO"%>



<%
HashMap<String,Object> map = (HashMap<String,Object>) request.getAttribute("map");
SearchVO searchVO = (SearchVO) request.getAttribute("searchVO");
PurchaseVO purchaseVO=new PurchaseVO();
int totalVOCount = ((Integer)map.get("count")).intValue();
int totalPage=totalVOCount/searchVO.getPageUnit();
if(totalVOCount%searchVO.getPageUnit() >0){
	totalPage += 1;
}
%>


<html>
<head>
<title>
	<%if(request.getParameter("menu").equals("manage")){%>
		�ǸŻ�ǰ����
	<%}
	  else if(request.getParameter("menu").equals("search")){%>
		��ǰ �����ȸ
	<%}%>
</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
function fncGetProductList(){
	document.detailForm.submit();
}
-->
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
					<td width="93%" class="ct_ttl01">
					
							
						<%if(request.getParameter("menu").equals("manage")){%>
								�ǸŻ�ǰ����
						<%}
						  else if(request.getParameter("menu").equals("search")){%>
								��ǰ �����ȸ
					    <%}%>
					
					</td>
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
				<option value="0">��ǰ��ȣ</option>
				<option value="1">��ǰ��</option>
				<option value="2">��ǰ����</option>
			</select>
			<input type="text" name="searchKeyword" value="<%=(request.getParameter("searchKeyword")==null || request.getParameter("searchKeyword")=="") ? ""   : request.getParameter("searchKeyword")%>"  class="ct_input_g" style="width:200px; height:19px" />
		</td>
	
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetProductList();">�˻�</a>
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

		<td colspan="11" >��ü <%=totalVOCount %> �Ǽ�, ���� <%=searchVO.getPage() %> ������</td>

	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
<%-- while�� or for������ ��ǰ �������� ���������ϴ� �κ�	--%>
<% 
	ArrayList<ProductVO> arr = (ArrayList<ProductVO>)map.get("list");
		for(int i=0; i<arr.size(); i++){ %>    <%-- i<searchVO.getPageUnit()�� �ϸ� searchVO.getPageUnit()�� 3�ε� ���������� ������ 3���̸��ϰ�� arr.get(i)�� java.lang.IndexOutOfBoundsException���Ƿ� i<arr.size()������� --%>
			<tr class="ct_list_pop">
				<td align="center"><%= arr.get(i).getProdNo() %></td>
				<td></td>
				<td align="left">
				  <%
					if(purchaseVO.getPurchaseProd() == arr.get(i)){%>
						<%=arr.get(i).getProdName()%>
				  <%}
					else{
						if(request.getParameter("menu").equals("search")){%>
							<a href="/getProduct.do?prodNo=<%= arr.get(i).getProdNo() %>&menu=search"><%= arr.get(i).getProdName() %></a>
				  	  <%}
						else {%>
							<a href="/updateProductView.do?prodNo=<%= arr.get(i).getProdNo() %>&menu=manage"><%= arr.get(i).getProdName() %></a>
					  <%}
					}%>
					
				</td>
				<td></td>
				<td align="left"><%= arr.get(i).getPrice() %></td>
				<td></td>
				<td align="left"><%= arr.get(i).getRegDate() %></td>
				<td></td>
				<td align="left">
				
				  <%
					if(purchaseVO.getPurchaseProd() == arr.get(i)){%>
						��� ����
				  <%}
					else{%>
						�Ǹ���
				  <%}%>
					
		
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
		<%--��ũ�߰�--%>
		<%
			int startPage=1;
			int pageViewUnit=3;
			if(searchVO.getPage()%pageViewUnit==0){ //���� �������� 3�� ����϶� �������������� 2�� �� ex.3->1, 6->4, 9->7
				startPage=searchVO.getPage()-2; //== startPage=searchVO.getPage()-(searchVO.getPage()%3+(3-1)) //OK- startPage=searchVO.getPage()-(searchVO.getPage()%searchVO.getPageUnit()-1)
			}
			else if(searchVO.getPage()%pageViewUnit!=0){ //���� �������� 3�� ����� �ƴҶ�
				startPage=searchVO.getPage()-(searchVO.getPage()%pageViewUnit-1); //ex. 1->1, 2->1, 3->4(x.3�� ���ex), 4->4, 5->4, 7->7, 8->7, 10->10, 11->10
			}%>
		
		<% 
			int pageTmp=0;
			
			if(request.getParameter("menu").equals("search")){%>
				<a href="listProduct.do?page=<%=startPage-pageViewUnit%>&menu=search&searchCondition=<%=request.getParameter("searchCondition")%>&searchKeyword=<%=request.getParameter("searchKeyword")%>">����</a>
			  <%for(pageTmp+=startPage; pageTmp<=startPage+(pageViewUnit-1); pageTmp++){
					if(totalPage+1==pageTmp){
						break;
					}
					else if(totalPage+1!=pageTmp){%>
						<a href="/listProduct.do?page=<%=pageTmp%>&menu=search&searchCondition=<%=request.getParameter("searchCondition")%>&searchKeyword=<%=request.getParameter("searchKeyword")%>"><%=pageTmp %></a>
				  <%}
				}%>
				<a href="listProduct.do?page=<%=startPage+pageViewUnit%>&menu=search&searchCondition=<%=request.getParameter("searchCondition")%>&searchKeyword=<%=request.getParameter("searchKeyword")%>">����</a>
		  <%}
			else{%>
				<a href="listProduct.do?page=<%=startPage-pageViewUnit%>&menu=manage&searchCondition=<%=request.getParameter("searchCondition")%>&searchKeyword=<%=request.getParameter("searchKeyword")%>">����</a>
			  <%for(pageTmp+=startPage; pageTmp<=startPage+(pageViewUnit-1); pageTmp++){ 
					if(totalPage+1==pageTmp){
							break;
					}
					else if(totalPage+1!=pageTmp){%>
							<a href="/listProduct.do?page=<%=pageTmp%>&menu=manage&searchCondition=<%=request.getParameter("searchCondition")%>&searchKeyword=<%=request.getParameter("searchKeyword")%>"><%=pageTmp %></a>
				  <%}
				}%>
				<a href="listProduct.do?page=<%=startPage+pageViewUnit%>&menu=manage&searchCondition=<%=request.getParameter("searchCondition")%>&searchKeyword=<%=request.getParameter("searchKeyword")%>">����</a>
		 <%}%>
    	    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->

</form>

</div>
</body>
</html>