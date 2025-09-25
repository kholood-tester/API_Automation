package tests;

import base.BaseApiTest;
import org.json.simple.JSONObject;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static utils.ExtentTestNGListener.assertAndReport;


public class createUser extends BaseApiTest {

    @Test(groups = "create")
    public void createNewUser() {
        JSONObject body = new JSONObject();
        body.put("name", "kholood");
        body.put("job", "QC Engineer");

        Response res =
                given()
                        .spec(reqSpec)
                        .body(body.toJSONString())
                        .when()
                        .post("/users");

        assertAndReport("POST /users", res, 201, WARN_THRESHOLD_MS);

        // Existing content validations:
        res.then()
                .body("id", notNullValue());

        String id = res.then().extract().path("id");
        put("userId", id);
    }
}
