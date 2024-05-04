import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Удаление пользователя (DELETE /api/auth/user)")
public class DeleteUserTests extends TestDataAndConstants {

    UserMethods userMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userMethods.delete(accessToken);
        }
    }

    @Test
    @DisplayName("Удаления пользователя из системы (с передачей токена)")
    public void checkUserDeleteTest() {
        Response response = userMethods.delete(accessToken);
        String expectedMessage = DELETE_202_USER_SUCCESSFULLY_REMOVED;
        response.then()
                .statusCode(202).and()
                .body("success", equalTo(true)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Удаления пользователя из системы (без передачи токена)")
    public void checkClientDeleteWithoutToken() {
        Response response = userMethods.deleteWithoutToken();
        String expectedMessage = _403_NEED_AUTHORIZATION;
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }
}
