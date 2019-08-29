package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	
	ProductService productService = new ProductServiceImpl();
	UserService userService = new UserServiceImpl();

	public PurchaseDAO() {}

	public PurchaseVO findProductNo(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE tran_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);

		ResultSet rs = stmt.executeQuery();

		PurchaseVO purchase = null;
		while (rs.next()) {
			purchase = new PurchaseVO();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setBuyer(userService.getUser(rs.getString("buyer_Id")));
			purchase.setPurchaseProd(productService.getProduct(Integer.parseInt(rs.getString("prod_no"))));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName((rs.getString("receiver_name")));
			purchase.setReceiverPhone((rs.getString("receiver_phone")));
			purchase.setDivyAddr((rs.getString("demailaddr")));
			purchase.setDivyRequest((rs.getString("dlvy_request")));
			purchase.setTranCode((rs.getString("tran_status_code")));
			purchase.setOrderDate((rs.getDate("order_data")));
			if(rs.getString("dlvy_date")!=null) {
				purchase.setDivyDate(rs.getString("dlvy_date").trim().split(" ")[0]);
			}
		}
		
		con.close();

		return purchase;
	}
	
	
	public PurchaseVO findProductNo2(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM transaction WHERE prod_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		PurchaseVO purchase = null;
		while (rs.next()) {
			purchase = new PurchaseVO();
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setBuyer(userService.getUser(rs.getString("buyer_Id")));
			purchase.setPurchaseProd(productService.getProduct(prodNo));
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName((rs.getString("receiver_name")));
			purchase.setReceiverPhone((rs.getString("receiver_phone")));
			purchase.setDivyAddr((rs.getString("demailaddr")));
			purchase.setDivyRequest((rs.getString("dlvy_request")));
			purchase.setTranCode((rs.getString("tran_status_code")));
			purchase.setOrderDate((rs.getDate("order_data")));
			purchase.setDivyDate(rs.getString("dlvy_date"));
		}
		
		con.close();

		return purchase;
	}

	
	public Map<String,Object> getPurchaseList(SearchVO search,String buyerId) throws Exception {
		
		Map<String , Object>  map = new HashMap<String, Object>();
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction ";
		
		sql += "WHERE buyer_id = '"+ buyerId+"' ORDER BY tran_no";


		int total = this.getTotalCount(sql);
		
		System.out.println("ProductDAO :: totalCount  :: " + total);

		//==> CurrentPage 게시물만 받도록 Query 다시구성
		sql = makeCurrentPageSql(sql, search);
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		System.out.println(search);

		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		PurchaseVO vo;
		
		while(rs.next()){
			vo = new PurchaseVO();
			vo.setTranNo(Integer.parseInt(rs.getString("tran_no")));
			vo.setPurchaseProd(productService.getProduct(Integer.parseInt(rs.getString("prod_no"))));
			vo.setBuyer(userService.getUser(rs.getString("buyer_id")));
			vo.setPaymentOption(rs.getString("payment_option"));
			vo.setReceiverName(rs.getString("receiver_name"));
			vo.setReceiverPhone(rs.getString("receiver_phone"));
			vo.setDivyAddr(rs.getString("demailaddr"));
			vo.setDivyRequest(rs.getString("dlvy_request"));
			vo.setTranCode(rs.getString("tran_status_code"));
			vo.setOrderDate(rs.getDate("order_data"));
			vo.setDivyDate(rs.getString("dlvy_date"));

			System.out.println(vo);

			list.add(vo);
		}
		
		//==> totalCount 정보 저장
		map.put("totalCount", new Integer(total));
		//==> currentPage 의 게시물 정보 갖는 List 저장
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}
	
	
	public void insertPurchase(PurchaseVO purchase) throws Exception {

		Connection con = DBUtil.getConnection();
		
		String transactionSql = "INSERT INTO transaction VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";

		PreparedStatement transactionStmt = con.prepareStatement(transactionSql);
		transactionStmt.setInt(1, purchase.getPurchaseProd().getProdNo());//rs.getInt("prod_no"))에서 변경함
		transactionStmt.setString(2, purchase.getBuyer().getUserId());
		transactionStmt.setString(3, purchase.getPaymentOption());
		transactionStmt.setString(4, purchase.getReceiverName());
		transactionStmt.setString(5, purchase.getReceiverPhone());
		transactionStmt.setString(6, purchase.getDivyAddr());
		transactionStmt.setString(7, purchase.getDivyRequest());
		transactionStmt.setString(8, purchase.getTranCode());
		/**/
		if(purchase.getDivyDate()==null) {
			transactionStmt.setString(9, purchase.getDivyDate());
		}
		else if(purchase.getDivyDate()!=null) {
			transactionStmt.setString(9, purchase.getDivyDate().replace("-",""));
		}
		transactionStmt.executeUpdate();

		con.close();
	}

	
	public void updatePurchase(PurchaseVO purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql="UPDATE transaction SET payment_option=?, receiver_name=?, receiver_phone=?, demailaddr=?, dlvy_request=?, dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());

		stmt.executeUpdate();
		
		con.close();

	}

	
	public void updateTranCode(PurchaseVO purchase) throws Exception {
		
		Connection con = DBUtil.getConnection();
		
		String sql="UPDATE transaction SET tran_status_code = ? WHERE prod_no = ?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getPurchaseProd().getProdNo());
		
		stmt.executeUpdate();
		
		con.close();
	}
	
	
	// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {

		sql = "SELECT COUNT(*) "+
				"FROM ( " +sql+ ") countTable";
		
		System.out.println("getTotalCount()안에서 sql: "+sql);

		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}

		pStmt.close();
		con.close();
		rs.close();

		return totalCount;
	}

	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , SearchVO search){ 
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
}