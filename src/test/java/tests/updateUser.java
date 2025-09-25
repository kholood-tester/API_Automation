package tests;

import base.BaseApiTest;
import org.json.simple.JSONObject;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static utils.ExtentTestNGListener.assertAndReport;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class updateUser extends BaseApiTest {

    private String id;

    @BeforeClass
    public void fetchId() {
        id = get("userId");
        Assert.assertNotNull(id, "userId must be available from CreateUserTest");
    }

    @Test(groups = "update", dependsOnGroups = "create")
    public void updateCreatedUser() {
        JSONObject body = new JSONObject();
        body.put("name", "kholood");
        body.put("job", "Automation Engineer");

        Response res =
                given()
                        .spec(reqSpec)
                        .body(body.toJSONString())
                        .when()
                        .patch("/users/{id}", id);

// ✅ status/time assert + warn if slow
        assertAndReport("PATCH /users/{id}", res, 200, WARN_THRESHOLD_MS);

        res.then()
                .body("job", equalTo("Automation Engineer"))
                .body("updatedAt", notNullValue());

    }
}
