import data.Owner;

import java.util.List;

public interface IOwnersManager {
	int createOwner(Owner owner);

	void deleteOwner(int createdOwnerId);

	List<Owner> findByLastName(String lastName);
}
