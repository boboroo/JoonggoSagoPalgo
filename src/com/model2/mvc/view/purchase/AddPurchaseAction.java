package com.model2.mvc.view.purchase;

import java.text.SimpleDateFormat;
import java.sql.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;


/**
 * Servlet implementation class ListProductAction
 */
@WebServlet("/AddPurchaseAction")
public class AddPurchaseAction extends Action {
	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		PurchaseVO purchase=new PurchaseVO();
		
		ProductDAO productDAO = new ProductDAO();
		ProductVO product = productDAO.findProductNo(Integer.parseInt(request.getParameter("prodNo")));	
		purchase.setPurchaseProd(product);
		
		UserDAO userDAO = new UserDAO();
		UserVO buyer = userDAO.findUser(request.getParameter("buyerId"));
		purchase.setBuyer(buyer);
		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setTranCode("1");

		PurchaseService service=new PurchaseServiceImpl();
		service.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
