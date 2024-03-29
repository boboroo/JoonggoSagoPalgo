package com.model2.mvc.view.product;


import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


public class UpdateProductAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		int prodNo=(Integer)session.getAttribute("prodNo");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ProductVO productVO = new ProductVO();
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdNo(prodNo);
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		ProductService service=new ProductServiceImpl();
		service.updateProduct(productVO);
		
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu="+request.getParameter("menu");
	}
}