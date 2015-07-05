package de.hdm.sms.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.sms.server.db.DatebaseConnection;
import de.hdm.sms.shared.bo.Product;
import de.hdm.sms.shared.bo.User;

public class ProductMapper {
	private static ProductMapper productmapper = null;
	public Connection con = DatebaseConnection.connection();

	protected ProductMapper() {

	}

	public static ProductMapper productmapper() {
		if (productmapper == null) {
			productmapper = new ProductMapper();
		}
		return productmapper;
	}

	public void insertProduct(Product p) {
		Connection con = DatebaseConnection.connection();
		// Date now = new Date();
		try {
			Statement state = con.createStatement();
			String sqlquery = "INSERT INTO Product (Name, Componentgroup_id, Modifier, Creationdate, LastModified) VALUES ("
					+ "'"
					+ p.getProductName()
					+ "','"
					+ p.getComponentGroupId()
					+ "', '"
					+ p.getModifier()
					+ "', '"
					+ DateHelperClass.getCurrentTime()
					+ "', '"
					+ DateHelperClass.getCurrentTime() + "');";

			state.executeUpdate(sqlquery);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Product> loadAllProducts() {
		Connection con = DatebaseConnection.connection();
		ArrayList<Product> resultList = new ArrayList<>();

		try {
			Statement state = con.createStatement();
			ResultSet result = state
					.executeQuery("SELECT * From Product INNER JOIN Componentgroup ON Product.Componentgroup_id = Componentgroup.Id ORDER BY Product.Id");

			while (result.next()) {
				Product po = new Product();
				po.setId(result.getInt("Id"));
				po.setProductName(result.getString("Product.Name"));
				po.setComponentGroupId(result.getInt("Componentgroup_id"));

				resultList.add(po);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultList;
	}

	public Product getOneProductUserById(int tempId) {
		Connection con = DatebaseConnection.connection();

		Product p = new Product();

		try {

			Statement state = con.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT * From Product INNER JOIN Componentgroup ON Product.Componentgroup_id = Componentgroup.Id AND Product.Id = '"
							+ tempId + "'");

			while (rs.next()) {
				p.setId(rs.getInt("Id"));
				p.setModifier(rs.getInt("Modifier"));
				p.setModificationDate(rs.getTimestamp("Product.LastModified"));
				p.setCreationDate(rs.getTimestamp("Product.Creationdate"));
				p.setProductName(rs.getString("Product.Name"));
				p.setComponentGroupName(rs.getString("Componentgroup.Name"));
				p.setComponentGroupId(rs.getInt("Componentgroup.Id"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return p;
	}

	public User getLastModifierProductById(int tempid) {
		Connection con = DatebaseConnection.connection();

		User u = new User();

		try {

			Statement state = con.createStatement();
			ResultSet rs = state
					.executeQuery("SELECT * From Product INNER JOIN User ON Product.Modifier = User.Id AND User.Id='"
							+ tempid + "';");

			while (rs.next()) {

				u.setId(rs.getInt("Id"));
				u.setFirstName(rs.getString("Firstname"));
				u.setLastName(rs.getString("Lastname"));
				u.seteMailAdress(rs.getString("EmailAdress"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return u;
	}

	public void updateProduct(Product p) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("UPDATE Product SET LastModified= '"
					+ DateHelperClass.getCurrentTime() + "', Name= '"
					+ p.getProductName() + "', Modifier= '"
					+ p.getModifier() + "'" + "WHERE Id = '" + p.getId()
					+ "';");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public void deleteProduct(Product p) {

		Connection con = DatebaseConnection.connection();

		try {

			Statement state = con.createStatement();

			state.executeUpdate("DELETE FROM Product WHERE Id='"
					+ p.getId() + "';");

		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

		
	
}
