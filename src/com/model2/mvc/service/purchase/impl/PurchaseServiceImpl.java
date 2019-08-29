package com.model2.mvc.service.purchase.impl;


import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;



public class PurchaseServiceImpl implements PurchaseService {

	
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}

	public void addPurchase(PurchaseVO purchase) throws Exception{
		purchaseDAO.insertPurchase(purchase);
	}
	
	public PurchaseVO getPurchase(int tranNo) throws Exception{
		return purchaseDAO.findProductNo(tranNo);
	}
	
	public PurchaseVO getPurchase2(int prodNo) throws Exception{
		return purchaseDAO.findProductNo2(prodNo);
	}
	
	public Map<String, Object> getPurchaseList(SearchVO search,String buyerId) throws Exception{
		return purchaseDAO.getPurchaseList(search,buyerId);
	}
	
	public Map<String,Object> getSaleList(SearchVO search) throws Exception{
		return null;
	}
	
	public void updatePurcahse(PurchaseVO purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);
	}
	
	public void updateTranCode(PurchaseVO purchase) throws Exception {
		purchaseDAO.updateTranCode(purchase);
	}
	

}
