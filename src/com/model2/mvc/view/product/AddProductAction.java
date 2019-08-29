package com.model2.mvc.view.product;

import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;


/**
 * Servlet implementation class ListProductAction
 */
@WebServlet("/AddProductAction")
public class AddProductAction extends Action {
	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
	
		ProductVO productVO=new ProductVO();
		productVO.setProdName(request.getParameter("prodName"));
		productVO.setProdDetail(request.getParameter("prodDetail"));
		productVO.setManuDate(request.getParameter("manuDate"));
		productVO.setPrice(Integer.parseInt(request.getParameter("price")));
		productVO.setFileName(request.getParameter("fileName"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		productVO.setRegDate(Date.valueOf(sdf.format(new java.util.Date())));
		
		System.out.println(productVO);
		
		ProductService service=new ProductServiceImpl();
		service.addProduct(productVO);
		
		request.setAttribute("productVO", productVO);

		return "forward:/product/addProduct.jsp";
	}

}
