package ru.netology.delivery.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    public long convert(int gap) {
        return LocalDate.now().plusDays(gap).atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
    }

    @BeforeEach
    void setup() {
        Configuration.headless = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and postpone meeting")
    void shouldSuccessfulPlanAndPostponeMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(secondMeetingDate);
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/positivename.csv")
    void shouldPositiveTestWithDefaultDateTask(String name) {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(secondMeetingDate);
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

    @Test
    void shouldPositiveTestDropDawnTask() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").val("ол");
        $(withText("Орёл")).click();
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(secondMeetingDate);
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/positivecity.csv")
    void shouldPositiveTestNewRegion(String city) {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").val(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(secondMeetingDate);
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

    @Test
    void shouldPositiveTestCalendar() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("button .icon_name_calendar").click();
        if (LocalDate.now().getMonthValue() != LocalDate.now().plusDays(daysToAddForFirstMeeting).getMonthValue()) {
            $("[data-step='1']").click();
        }
        $("[data-day='" + convert(daysToAddForFirstMeeting) + "']").click();
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + firstMeetingDate));
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(secondMeetingDate);
        $(".button").click();
        $("[data-test-id='replan-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Необходимо подтверждение"));
        $("[data-test-id='replan-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("У вас уже запланирована встреча на другую дату. Перепланировать? Перепланировать"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__title").shouldBe(visible).shouldBe(exactText("Успешно!"));
        $("[data-test-id='success-notification'] .notification__content").shouldBe(visible).shouldBe(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/negativecity.csv", delimiter = '|')
    void shouldNegativeCityTest(String city, String fail) {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id='city'] input").val(city);
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] .input__sub").shouldBe(visible).shouldBe(exactText(fail));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/negativename.csv", delimiter = '|')
    void shouldNegativeNameTest(String name, String fail) {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(name);
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] .input__sub").shouldBe(visible).shouldBe(exactText(fail));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/negativephone.csv", delimiter = '|')
    void shouldNegativePhoneTest(String phone, String fail) {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(phone);
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='phone'] .input__sub").shouldBe(visible).shouldBe(exactText(fail));
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/negativedate.csv", delimiter = '|')
    void shouldNegativeDateTest(int gap, String pattern, String fail) {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var firstMeetingDate = LocalDate.now().plusDays(gap).format(DateTimeFormatter.ofPattern(pattern));
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible).shouldBe(exactText(fail));
    }

    @Test
    void shouldDateNotSelected() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible).shouldBe(exactText("Неверно введена дата"));
    }

    @Test
    void shouldWithoutCheckbox() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        $("[data-test-id='city'] input").val(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(firstMeetingDate);
        $("[data-test-id='name'] input").val(validUser.getName());
        $("[data-test-id='phone'] input").val(validUser.getPhone());
        $(".button").click();
        $("[data-test-id='agreement']").shouldBe(visible).shouldBe(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
