import data.Owner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OwnersManagerSpring implements IOwnersManager{

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(
		DataSourceProvider.INSTANCE.getDataSource());

	@Override
	public int createOwner(Owner owner) {
		return new SimpleJdbcInsert(jdbcTemplate)
			.withTableName("owners")
			.usingGeneratedKeyColumns("id")
			.executeAndReturnKey(Map.of(
				"first_name", owner.getFirstName(),
				"last_name", owner.getLastName(),
				"address", owner.getAddress(),
				"city", owner.getCity(),
				"telephone", owner.getTelephone()
			)).intValue();
	}

	@Override
	public void deleteOwner(int createdOwnerId) {
		jdbcTemplate.update("DELETE FROM owners WHERE id = ?", createdOwnerId);
	}

	@Override
	public List<Owner> findByLastName(String lastName) {
		List<Owner> owners = jdbcTemplate.query(
			"Select * from owners where last_name = ?",
			new Object[]{lastName},
			new RowMapper<Owner>(){
				public Owner mapRow(ResultSet result, int rowNum) throws SQLException{
					return Owner.builder()
						.id(result.getInt("id"))
						.firstName(result.getString("first_name"))
						.lastName(result.getString("last_name"))
						.city(result.getString("city"))
						.address(result.getString("address"))
						.telephone(result.getString("telephone"))
						.build();
				}
			}
		);
		return owners;
	}
}
