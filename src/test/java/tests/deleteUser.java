package tests;

import base.BaseApiTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static utils.ExtentTestNGListener.assertAndReport;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class deleteUser extends BaseApiTest {

    private String id;

    @BeforeClass
    public void fetchId() {
        id = get("userId");
        Assert.assertNotNull(id, "userId must be available from CreateUserTest");
    }

    @Test(groups = "delete", dependsOnGroups = "update")
    public void deleteCreatedUser() {
        Response res =
                given()
                        .spec(reqSpec)
                        .when()
                        .delete("/users/{id}", id);
        assertAndReport("DELETE /users/{id}", res, 204, WARN_THRESHOLD_MS);

    }
}
