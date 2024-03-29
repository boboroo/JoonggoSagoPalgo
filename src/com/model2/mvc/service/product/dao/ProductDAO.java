package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ProductDAO {

	public ProductDAO() {}

	public ProductVO findProductNo(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		ProductVO productVO = null;
		while (rs.next()) {
			productVO = new ProductVO();
			productVO.setProdNo(prodNo);
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("manufacture_day"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setFileName(rs.getString("image_file"));
			productVO.setRegDate(rs.getDate("reg_date"));
		}
		
		con.close();

		return productVO;
	}

	

	public HashMap<String,Object> getProductList(SearchVO searchVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product ";
		
		if (searchVO.getSearchCondition() != null) {//LIKE '%구체적문자%'
			if (searchVO.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no LIKE '%" + searchVO.getSearchKeyword()
				+ "%'";
			}
			else if (searchVO.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name LIKE '%" + searchVO.getSearchKeyword()
				+ "%'";
			}
			else if (searchVO.getSearchCondition().equals("2")) {
			sql += " WHERE price LIKE '%" + searchVO.getSearchKeyword()
			+ "%'";
			}
		}
		sql += " ORDER BY prod_no";
		
		PreparedStatement stmt = 
				con.prepareStatement(	sql,
						ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();

		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		if(((searchVO.getPage()-1)*searchVO.getPageUnit())+1>total) {//게시물이 출력될 페이지 수가 넘어섰을때, 즉 총 페이지 수를 넘어섰을때. page:'4'(4p에 출력될 게시글- '10~12'번째 게시글) total: 게시글 '9'개
			searchVO.setPage(searchVO.getPage()-searchVO.getPageUnit());
		}
		
		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1); //위치해있는 페이지의 첫번째행에 해당되는 DB튜플에 커서를 위치시킴.
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());

		
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			ProductVO vo;
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				vo = new ProductVO();
				vo.setProdNo(Integer.parseInt(rs.getString("prod_no")));
				vo.setProdName(rs.getString("prod_name"));
				vo.setProdDetail(rs.getString("prod_detail"));
				vo.setManuDate(rs.getString("manufacture_day"));
				vo.setPrice(Integer.parseInt(rs.getString("price")));
				vo.setFileName(rs.getString("image_file"));
				vo.setRegDate(rs.getDate("reg_date"));

				list.add(vo);
				if (!rs.next())
					break;

			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());
		
		con.close();
		
		return map;
	}

	public void insetProduct(ProductVO productVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO product VALUES (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replace("-",""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.executeUpdate();

		con.close();
	}

	public void updateProduct(ProductVO productVO) throws Exception {
		
		Connection con = DBUtil.getConnection();
		String sql = "UPDATE product SET prod_name=?,prod_detail=?,manufacture_day=?, price=?,image_file=? WHERE prod_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replace("-",""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		
		stmt.executeUpdate();
		con.close();
	}

}
