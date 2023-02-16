package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import db.DbIntegrityException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;

	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department dp) {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("INSERT INTO Department " + "(Name) " + "VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, dp.getName());

			int result = st.executeUpdate();

			if (result > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					dp.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);

			} else {
				throw new DbException("Unexpected error! No rows affected.");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Department dp) {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("UPDATE Department SET Department.name = ? WHERE Department.Id = ?");

			st.setString(1, dp.getName());
			st.setInt(2, dp.getId());
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleleById(Integer id) {

		PreparedStatement st = null;

		try {

			st = conn.prepareStatement("DELETE FROM Department WHERE Department.Id = ?");

			st.setInt(1, id);
			st.executeUpdate();

		} catch (SQLException e) {
			throw new DbIntegrityException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public Department findById(Integer id) {

		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * " + "FROM Department " + "WHERE Department.Id = ?");

			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {

				Department dp = instantiateDepartment(rs);
				return dp;
			} else {
				return null;
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	@Override
	public List<Department> findAll() {

		List<Department> list = new ArrayList<>();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			st = conn.prepareStatement("SELECT * " + "FROM Department ORDER BY Name");

			rs = st.executeQuery();

			while (rs.next()) {
				Department dp = instantiateDepartment(rs);
				list.add(dp);
			}

			return list;

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {

		Department dp = new Department();
		dp.setName(rs.getString("Name"));
		dp.setId(rs.getInt("Id"));
		return dp;

	}

}
