package uitest.selenide;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import com.codeborne.selenide.SelenideElement;

import uitest.AbstractHangmanTest;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class HangmanSpec extends AbstractHangmanTest {
  @Before
  public void startGame() {
    open("/hangman");
    $(byText("ENG")).click();
  }

  @Test
  public void showsTopicAndMaskedWordAtTheBeginning() {
    $("#topic").shouldHave(text("house"));
    $("#wordInWork").shouldHave(text("____"));
  }

  @Test
  public void userCanGuessLetters() {
    letter("S").click();
    $("#wordInWork").shouldHave(text("s___"));
    letter("S").shouldHave(cssClass("used"));
  }

  @Test
  public void userWinsWhenAllLettersAreGuessed() {
    letter("S").click();
    letter("O").click();
    letter("F").click();
    letter("A").click();
    $("#gameWin").shouldBe(visible);
  }

  @Test
  public void userHasNoMoreThan5Tries() {
    letter("B").click();
    assertHangmanImageByErrors(1);
    letter("D").click();
    assertHangmanImageByErrors(2);
    letter("E").click();
    assertHangmanImageByErrors(3);
    letter("G").click();
    assertHangmanImageByErrors(4);
    letter("H").click();
    assertHangmanImageByErrors(5);
    letter("I").click();
    letter("B").shouldHave(cssClass("nonused"));
    $("#gameLost").shouldBe(visible);
  }

  private void assertHangmanImageByErrors(int numErrors) {
    for (int i = 1; i <= numErrors; i++) {
      $("hangman" + i).isDisplayed();
    }
  }

  @Test
  public void userCanChooseLanguage() {
    $(By.linkText("EST")).click();
    $("#topic").shouldHave(text("maja"));
    $("#wordInWork").shouldHave(text("____"));
    $$("#alphabet .letter").shouldHave(size(27));

    $(By.linkText("RUS")).click();
    $("#topic").shouldHave(text("дом"));
    $("#wordInWork").shouldHave(text("______"));
    $$("#alphabet .letter").shouldHave(size(33));

    $(By.linkText("ENG")).click();
    $("#topic").shouldHave(text("house"));
    $("#wordInWork").shouldHave(text("____"));
    $$("#alphabet .letter").shouldHave(size(26));
  }

  private SelenideElement letter(String letter) {
    return $(byText(letter));
  }
}
