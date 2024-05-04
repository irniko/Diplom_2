import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Получение/изменение данных пользователя (GET/PATCH /api/auth/user)")
public class UpdateUserInfoTests extends TestDataAndConstants{

    UserMethods userMethods;
    String accessToken;

    @Before
    public void setUp() {
        userMethods = new UserMethods();
        accessToken = userMethods.create(user).path("accessToken").toString();
    }

    @After
    public void cleanUp() {
        userMethods.delete(accessToken);
    }

    @Test
    @DisplayName("Получение данных о пользователе (с передачей токена)")
    public void getUserInfoWithTokenTest() {
        userMethods.login(UserLoginDetails.fromUser(user));
        Response response = userMethods.getInfoWithToken(accessToken);
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("user.email", equalTo(user.getEmail())).and()
                .body("user.name", equalTo(user.getName()));
        Allure.step("Пользовательские данные успешно получены: '" + user + "'");
    }

    @Test
    @DisplayName("Изменение поля 'email' (с передачей токена)")
    public void updateUserEmailWithTokenTest() {
        userMethods.login(UserLoginDetails.fromUser(user));
        user.setEmail(faker.internet().emailAddress());
        Response response = userMethods.updateInfoWithToken(user, accessToken);
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("user.email", equalTo(user.getEmail())).and()
                .body("user.name", equalTo(user.getName()));
        Allure.step("Пользовательские данные успешно изменены на: '" + user + "'");
    }

    @Test
    @DisplayName("Изменение поля 'password' (с передачей токена)")
    public void updateUserPasswordWithTokenTest() {
        userMethods.login(UserLoginDetails.fromUser(user));
        user.setEmail(faker.internet().password());
        Response response = userMethods.updateInfoWithToken(user, accessToken);
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("user.email", equalTo(user.getEmail())).and()
                .body("user.name", equalTo(user.getName()));
        Allure.step("Пользовательские данные успешно изменены на: '" + user + "'");
    }

    @Test
    @DisplayName("Изменение поля 'name' (с передачей токена)")
    public void updateUserNameWithTokenTest() {
        userMethods.login(UserLoginDetails.fromUser(user));
        user.setEmail(faker.name().username());
        Response response = userMethods.updateInfoWithToken(user, accessToken);
        response.then()
                .statusCode(200).and()
                .body("success", equalTo(true)).and()
                .body("user.email", equalTo(user.getEmail())).and()
                .body("user.name", equalTo(user.getName()));
        Allure.step("Пользовательские данные успешно изменены на: '" + user + "'");
    }

    // ========================= with Token =====================================
    @Test
    @DisplayName("Получение данных о пользователе (без передачи токена)")
    public void getUserInfoWithoutTokenTest() {
        String expectedMessage = _403_NEED_AUTHORIZATION;
        Response response = userMethods.getInfoWithoutToken();
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }
    @Test
    @DisplayName("Изменение поля 'email' (без передачи токена)")
    public void updateUserEmailWithoutTokenTest() {
        user.setEmail(faker.internet().emailAddress());
        String expectedMessage = _403_NEED_AUTHORIZATION;
        Response response = userMethods.updateInfoWithoutToken(user);
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Изменение поля 'password' (без передачи токена)")
    public void updateUserPasswordWithoutTokenTest() {
        user.setPassword(faker.internet().password());
        String expectedMessage = _403_NEED_AUTHORIZATION;
        Response response = userMethods.updateInfoWithoutToken(user);
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }

    @Test
    @DisplayName("Изменение поля 'name' (без передачи токена)")
    public void updateUserNameWithoutTokenTest() {
        user.setName(faker.name().username());
        String expectedMessage = _403_NEED_AUTHORIZATION;
        Response response = userMethods.updateInfoWithoutToken(user);
        response.then()
                .statusCode(401).and()
                .body("success", equalTo(false)).and()
                .assertThat().body("message", equalTo(expectedMessage));
        Allure.step("Получение ожидаемого результата: '" + expectedMessage + "'");
    }
}
