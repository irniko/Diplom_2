import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Получение списка заказов пользователя (GET /api/orders)")
public class GetUserOrderListTests extends TestDataAndConstants {
    UserMethods userMethods;
    OrderMethods orderMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        orderMethods = new OrderMethods();
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @After
    public void cleanUp() {
        userMethods.delete(accessToken);
    }

    @Test
    @DisplayName("Получение списка заказов пользователя (с передачей токена)")
    public void checkGetOrderListWithToken() {
        userMethods.login(UserLoginDetails.fromUser(user));
        Response response = userMethods.getOrderListWithToken(accessToken);
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("orders", notNullValue()).and()
                .body("total", notNullValue()).and()
                .body("totalToday", notNullValue());
        Allure.step("Список заказов пользователя успешно получен");
    }

    @Test
    @DisplayName("Получение списка заказов пользователя (без передачи токена)")
    public void checkGettingOrderListWithoutToken() {
        String expectedMessage = _403_NEED_AUTHORIZATION;
        Response response = userMethods.getOrderListWithoutToken();
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }
}
