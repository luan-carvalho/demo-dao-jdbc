package application;

import model.dao.FactoryDao;
import model.dao.SellerDao;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = FactoryDao.createSellerDao();

	}

}
