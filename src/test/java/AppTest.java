import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Best restaurants are here!");
  }

  @Test
  public void rootPageContainsCuisineList(){
    Cuisine newCuisine = new Cuisine("England");
    newCuisine.save();
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("England");
  }

  @Test
  public void resturantsPageDisplaysResturantForCuisine() {
    Cuisine newCuisine = new Cuisine("Ukrainian");
    newCuisine.save();
    Restaurant newRestaurant = new Restaurant("Pravda", newCuisine.getId());
    newRestaurant.save();
    goTo("http://localhost:4567/");
    click("a", withText("Ukrainian"));
    assertThat(pageSource()).contains("Pravda");
  }

  @Test
  public void createsNewCuisineSuccessfully() {
    goTo("http://localhost:4567/");
    fill(".cuisineType").with("Mexican");
    submit(".btn");
    assertThat(pageSource()).contains("Mexican");
  }

  @Test
  public void createsNewRestuarantSeccessfully() {
    Cuisine newCuisine = new Cuisine("Mexican");
    newCuisine.save();
    goTo("http://localhost:4567/newrestaurant");
    fill("#newRestaurant").with("Dela Soul");
    fillSelect("#cuisineSlection").withText("Mexican");
    submit(".btn");
    goTo("http://localhost:4567/restaurants/" + newCuisine.getId());
    assertThat(pageSource()).contains("Dela Soul");
  }

  @Test
  public void wholeListOfRestaurantsIsShowed() {
    Cuisine firstCuisine = new Cuisine("American");
    firstCuisine.save();
    Cuisine secondCuisine = new Cuisine("Canadian");
    secondCuisine.save();
    Restaurant firstRes = new Restaurant("Beef", firstCuisine.getId());
    firstRes.save();
    Restaurant secondRes = new Restaurant("Leef", secondCuisine.getId());
    secondRes.save();
    goTo("http://localhost:4567/allrestaurants");
    assertThat(pageSource()).contains("Beef");
    assertThat(pageSource()).contains("Leef");
  }
}
