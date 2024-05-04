import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserMethods extends TestDataAndConstants{

    @Step("Создание пользователя с заданными параметрами")
    public Response create(User user) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(user)
                .post("/api/auth/register");

    }

    @Step("Ввод логина/пароля пользователя")
    public Response login(UserLoginDetails userLoginDetails) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(userLoginDetails)
                .post("/api/auth/login");
    }

    @Step("Удаление пользователя по токену ")
    public Response delete(String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .delete("/api/auth/user");
    }

    @Step("Удаление пользователя (без передачи токена) ")
    public Response deleteWithoutToken() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .delete("/api/auth/user");
    }

    @Step("Получение информации о пользователе (с передачей токена)")
    public Response getInfoWithToken(String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .get("/api/auth/user");
    }

    @Step("Получение информации о пользователе (без передачи токена)")
    public Response getInfoWithoutToken() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get("/api/auth/user");
    }

    @Step("Обновление информации о пользователе (с передачей токена)")
    public Response updateInfoWithToken(User user, String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(user)
                .patch("/api/auth/user");
    }

    @Step("Обновление информации о пользователе (без передачи токена)")
    public Response updateInfoWithoutToken(User user) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(user)
                .patch("/api/auth/user");
    }

    @Step("Получение заказов пользователя (с передачей токена)")
    public Response getOrderListWithToken(String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .get("/api/orders");
    }

    @Step("Получение заказов пользователя (без передачи токена)")
    public Response getOrderListWithoutToken() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get("/api/orders");
    }
}
