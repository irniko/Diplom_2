import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderMethods extends TestDataAndConstants{

    @Step("Получение списка доступных ингредиентов")
    public Response getIngredientList() {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .get("/api/ingredients");
    }

    @Step("Создание заказа с заданными ингредиентами (с передачей токена)")
    public Response createWithToken(Order order, String accessToken) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(order)
                .post("/api/orders");
    }

    @Step("Создание заказа с заданными ингредиентами (без передачи токена)")
    public Response createWithoutToken(Order order) {
        return given()
                .baseUri(BASE_URL)
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/orders");
    }
}
