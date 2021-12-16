import data.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AddOwnerTest {

	private IOwnersManager ow = new OwnersManagerSpring();

	@Test
	void checkAddOwner(){
		open("http://localhost:8080/owners/find");
		$("a.btn").click();
		$("#firstName").setValue("Ivan");
		$("#lastName").setValue("Ivanov");
		$("#address").setValue("USA");
		$("#city").setValue("California");
		$("#telephone").setValue("7775544");
		$("button.btn-primary").click();

		Owner actualOwner = ow.findByLastName("Ivanov").get(0);
		Assertions.assertEquals("California", actualOwner.getCity());

	}


}
