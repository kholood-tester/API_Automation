package tests;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class demo_newUserFlow {
    static String id;

    @Test  (priority = 1)
    public void createNewUser(){
        JSONObject request = new JSONObject();
        request.put("name", "kholood");
        request.put("job", "QC Engineer");

        baseURI = "https://reqres.in/api";

        id = given()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
        .when()
                .post("/users")
        .then()
                .statusCode(201)
                .extract()
                .path("id");
        System.out.println("New User ID is:  " + id);
    }

    @Test  (priority = 2, dependsOnMethods = "createNewUser")
    public void updateCreatedUser(){
        JSONObject request = new JSONObject();
        request.put("name", "kholood");
        request.put("job" , "Automation Engineer");

        baseURI = "https://reqres.in/api";

        given()
                .header("x-api-key", "reqres-free-v1")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
        .when()
                .patch("/users/{id}", id)
        .then()
                .statusCode(200)
                // Update checked her as it's not supported in free tier to store updated and get it
                .body("job", equalTo("Automation Engineer"))
                .body("updatedAt", notNullValue());
    }

    @Test   (priority = 3, dependsOnMethods = "updateCreatedUser")
    public void deleteCreatedUser(){
        baseURI = "https://reqres.in/api";

        given()
                .header("x-api-key", "reqres-free-v1")
                .when()
                .delete("/users/{id}", id)
                .then()
                .statusCode(204);
    }

    @Test   (priority = 4, dependsOnMethods = "deleteCreatedUser")
    public void getDeleteUser(){
        baseURI = "https://reqres.in/api";

        given()
                .header("x-api-key", "reqres-free-v1")
                .get("/users/{id}", id)
        .then()
                .statusCode(404);
    }
}
