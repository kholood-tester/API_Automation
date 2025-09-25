package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    protected RequestSpecification reqSpec;
    protected ITestContext ctx;
    protected static final long WARN_THRESHOLD_MS = 5000;

    @BeforeClass
    public void setupBase(ITestContext context) {
        this.ctx = context;
        RestAssured.baseURI = "https://reqres.in/api";

        reqSpec = new RequestSpecBuilder()
                .setBaseUri(RestAssured.baseURI)
                .addHeader("x-api-key", "reqres-free-v1")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    /** Helpers to store/retrieve values in the TestNG suite context */
    protected void put(String key, Object value) { ctx.setAttribute(key, value); }
    @SuppressWarnings("unchecked")
    protected <T> T get(String key) { return (T) ctx.getAttribute(key); }
}
