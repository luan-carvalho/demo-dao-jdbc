package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class FactoryDao {
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC();
	}

}
