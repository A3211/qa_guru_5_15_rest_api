import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RegressinTests {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void successUsersListTest() {
        given()
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .body("page", is(2))
                .body("total", is(12))
                .body("support.text", is("To keep ReqRes free, " +
                        "contributions towards server costs are appreciated!"));
    }

    @Test
    void successLoginTest() {
        given()
                .contentType(JSON)
                .body("{ \"email\": \"eve.holt@reqres.in\", " +
                        "\"password\": \"cityslicka\" }")
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void successCreateTest() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"leader\" }")
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }



    @Test
    void successUpdateTest() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")
                .when()
                .patch("/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void unsuccess404UpdateTest() {
        given()
                .contentType(JSON)
                .body("{ \"name\": \"morpheus\", \"job\": \"zion resident\" }")
                .when()
                .patch("/1api/users/2")
                .then()
                .statusCode(404);
    }

    @Test
    void successDeleteTest() {
        given()
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }



}
