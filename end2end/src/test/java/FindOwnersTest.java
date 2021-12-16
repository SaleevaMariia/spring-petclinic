import com.codeborne.selenide.Condition;
import data.Owner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class FindOwnersTest {

	private IOwnersManager ow = new OwnersManagerSpring();
	private int createdOwnerId;

	@BeforeEach
	void addOwner(){
		createdOwnerId = ow.createOwner(Owner.builder()
			.firstName("Mariia")
			.lastName("Saleeva")
			.city("Moscow")
			.address("Moscow street")
			.telephone("7773737")
		.build());
	}

	@AfterEach
	void releaseOwner(){
		ow.deleteOwner(createdOwnerId);
	}

	@RepeatedTest(1)
	@Test
	void findOwnersTest(){
		System.out.println("Generated id: " + createdOwnerId);
		open("http://localhost:8080/owners/find");
		$("#lastName").setValue("Saleeva");
		$("button[type='submit']").click();
		$("table.table").shouldBe(Condition.visible);
		$$("tr").find(Condition.text("Name"))
			.shouldHave(Condition.text("Mariia Saleeva"));

	}


}
