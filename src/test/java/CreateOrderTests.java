import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Создание заказа (POST /api/orders)")
public class CreateOrderTests extends TestDataAndConstants {

    UserMethods userMethods;
    OrderMethods orderMethods;
    List<String> ingredientsHash;
    List<String> ingredients = new ArrayList<>();
    Order order;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        orderMethods = new OrderMethods();
        order = new Order(ingredients);
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @After
    public void cleanUp() {
        userMethods.delete(accessToken);
    }

    @Test
    @DisplayName("Создание заказа с валидными хеш-кодами ингредиенто (без передачи токена)")
    public void createOrderWithCorrectIngredientWithoutTokenTest() {
        Response response = orderMethods.getIngredientList();
        ingredientsHash = response.then().extract().jsonPath().getList("data._id");

        ingredients.add(ingredientsHash.get(0));
        ingredients.add(ingredientsHash.get(1));
        order.setIngredients(ingredients);

        response = orderMethods.createWithoutToken(order);

        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("name", notNullValue()).and()
                .body("order.number", notNullValue());
        Allure.step("Заказ успешно создан в системе");

    }

    @Test
    @DisplayName("Создание заказа без указания ингредиентов (без передачи токена)")
    public void createOrderWithoutIngredientWithoutTokenTest() {
        order.setIngredients(ingredients);
        Response response = orderMethods.createWithoutToken(order);
        String expectedMessage = CREATE_ORDER_400_BAD_REQUEST;
        response.then()
                .statusCode(400).and().body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Создание заказа с невалидным хеш-кодом ингредиента (без передачи токена)")
    public void createOrderWithIncorrectIngredientWithoutTokenTest() {
        ingredients.add(INCORRECT_HASHCODE);
        order.setIngredients(ingredients);
        orderMethods.createWithoutToken(order).then().statusCode(500);
        Allure.step("Получение ожидаемого результата: '" + CREATE_ORDER_500_BAD_REQUEST + "'");
    }

    //================================ with Token ==============================================
    @Test
    @DisplayName("Создание заказа с валидными хеш-кодами ингредиентов (с передачей токена)")
    public void createOrderWithCorrectIngredientWithTokenTest() {
        Response response = orderMethods.getIngredientList();
        ingredientsHash = response.then().extract().jsonPath().getList("data._id");

        ingredients.add(ingredientsHash.get(0));
        ingredients.add(ingredientsHash.get(1));
        order.setIngredients(ingredients);

        response = orderMethods.createWithToken(order, accessToken);
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("name", notNullValue()).and()
                .body("order.number", notNullValue()).and()
                .body("order.owner.name", equalTo(user.getName())).and()
                .body("order.owner.email", equalTo(user.getEmail())).and()
                .body("order.status", notNullValue());
        Allure.step("Заказ успешно создан в системе, заказ привязан к пользователю");
    }

    @Test
    @DisplayName("Создание заказа без указания ингредиентов (с передачей токена)")
    public void createOrderWithoutIngredientWithTokenTest() {
        order.setIngredients(ingredients);
        Response response = orderMethods.createWithToken(order, accessToken);
        String expectedMessage = CREATE_ORDER_400_BAD_REQUEST;
        response.then()
                .statusCode(400).and().body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Создание заказа с невалидным хеш-кодом ингредиента (с передачей токена)")
    public void createOrderWithIncorrectIngredientWithTokenTest() {
        ingredients.add(INCORRECT_HASHCODE);
        order.setIngredients(ingredients);
        orderMethods.createWithToken(order, accessToken).then().statusCode(500);
        Allure.step("Получение ожидаемого результата: '" + CREATE_ORDER_500_BAD_REQUEST + "'");
    }
}
