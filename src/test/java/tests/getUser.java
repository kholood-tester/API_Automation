package tests;

import base.BaseApiTest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static utils.ExtentTestNGListener.assertAndReport;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class getUser extends BaseApiTest {

    private String id;

    @BeforeClass
    public void fetchId() {
        id = get("userId");
        Assert.assertNotNull(id, "userId must be available from CreateUserTest");
    }

    @Test(groups = "verify", dependsOnGroups = "delete")
    public void getDeletedUser() {
        Response res =
                given()
                        .spec(reqSpec)
                        .when()
                        .get("/users/{id}", id);

// ✅ status/time assert + warn if slow
        assertAndReport("GET /users/{id}", res, 404, WARN_THRESHOLD_MS);

    }
}
