package application;

import java.time.LocalDate;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller = new Seller(null, "Chris Greg", "elatataonasua@gmail.com", LocalDate.now(), 4000.00, new Department(2, null));
//		sellerDao.inset(seller);
		
		List<Seller> list = sellerDao.findAll();
		list.forEach(System.out::println);
		
	}

}
