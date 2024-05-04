import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Получение списка ингредиентов (GET /api/ingredients)")
public class GetIngredientListTests extends TestDataAndConstants {

    OrderMethods orderMethods;

    @Before
    public void setUp() {
        orderMethods = new OrderMethods();
    }

    @Test
    @DisplayName("Получение списка доступных ингредиентов")
    public void getAvailableIngredients() {
        Response response = orderMethods.getIngredientList();
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("data", notNullValue());
        response.then().assertThat().body("data", Matchers.instanceOf(List.class));
        Allure.step("Список ингредиентов успешно получен");
    }
}
