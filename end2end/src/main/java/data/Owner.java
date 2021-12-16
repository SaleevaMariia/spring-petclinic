package data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String telephone;

}
