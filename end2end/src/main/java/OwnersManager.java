import data.Owner;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OwnersManager implements IOwnersManager {

	private DataSource ds = DataSourceProvider.INSTANCE.getDataSource();

	@Override
	public int createOwner(Owner owner){
		String rawSql = "INSERT INTO owners (first_name, last_name, address, city, telephone)" +
			" VALUES (?, ?, ?, ?, ?)";

		try(Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(rawSql,
				Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, owner.getFirstName());
			ps.setString(2, owner.getLastName());
			ps.setString(3, owner.getAddress());
			ps.setString(4, owner.getCity());
			ps.setString(5, owner.getTelephone());
			ps.executeUpdate();
			ResultSet generatedKey = ps.getGeneratedKeys();
			if(generatedKey.next()){
				return generatedKey.getInt(1);
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return -1;
	}

	@Override
	public void deleteOwner(int createdOwnerId) {
		String rawSql = "DELETE FROM owners " +
			"WHERE id = ?";

		try(Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(rawSql)){

			ps.setInt(1, createdOwnerId);
			ps.executeUpdate();

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}

	@Override
	public List<Owner> findByLastName(String lastName){
		String rawSql = "SELECT * FROM owners" +
			" WHERE last_name = ?";

		List<Owner> resultList = new ArrayList<>();

		try(Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(rawSql)){

			ps.setString(1, lastName);
			ResultSet result = ps.executeQuery();
			while (result.next()){
				resultList.add(Owner.builder()
					.id(result.getInt("id"))
					.firstName(result.getString("first_name"))
					.lastName(result.getString("last_name"))
					.city(result.getString("city"))
					.address(result.getString("address"))
					.telephone(result.getString("telephone"))
					.build());
			}
			return resultList;

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return Collections.emptyList();
	}
}
