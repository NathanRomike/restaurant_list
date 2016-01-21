import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.*;

public class RestaurantTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Restaurant.all().size(), 0);
  }

  @Test
  public void restaurant_createdSuccessfully(){
    Cuisine newCuisine = new Cuisine("French");
    newCuisine.save();
    Restaurant newRestaurant = new Restaurant("Chateau la mer", newCuisine.getId());
    newRestaurant.save();
    assertTrue(newRestaurant instanceof Restaurant);
    assertEquals("Chateau la mer", newRestaurant.getName());
  }

  @Test
  public void restaurant_findSucessfully() {
    Cuisine newCuisine = new Cuisine("Ukrainian");
    newCuisine.save();
    Restaurant newRestaurant = new Restaurant("Pravda", newCuisine.getId());
    newRestaurant.save();
    Restaurant savedRestaurant = Restaurant.find(newRestaurant.getId());
    assertTrue(newRestaurant.equals(savedRestaurant));
  }

  @Test
  public void restaurant_hasDifferntWayToUpdateRestaurant() {
    Cuisine firstCuisine = new Cuisine ("American");
    firstCuisine.save();
    Cuisine secondCuisine = new Cuisine ("Italian");
    secondCuisine.save();
    Restaurant firstRestaurant = new Restaurant("Le cordon blue", firstCuisine.getId());
    firstRestaurant.save();
    Restaurant secondRestaurant = new Restaurant("Le cordon blue", firstCuisine.getId());
    secondRestaurant.save();
    Restaurant thirdRestaurant = new Restaurant("Le cordon blue", firstCuisine.getId());
    thirdRestaurant.save();
    firstRestaurant.update("Macdonalds");
    secondRestaurant.update(secondCuisine.getId());
    thirdRestaurant.update("Danko", secondCuisine.getId());
    assertEquals("Macdonalds", firstRestaurant.getName());
    assertEquals("Italian", Cuisine.find(secondRestaurant.getCuisineId()).getType());
    assertEquals("Danko", thirdRestaurant.getName());
    assertEquals("Italian", Cuisine.find(thirdRestaurant.getCuisineId()).getType());
  }

  @Test
  public void resteruant_deletesSuccesfully() {
  Cuisine newCuisine = new Cuisine ("Indian");
  newCuisine.save();
  Restaurant newRestaurant = new Restaurant ("New Delhi", newCuisine.getId());
  newRestaurant.save();
  newRestaurant.delete();
  assertEquals(0, newRestaurant.all().size());
  }

  @Test
  public void restaurant_returnCuisinType(){
    Cuisine newCuisine = new Cuisine("German");
    newCuisine.save();
    Restaurant newRestaurant = new Restaurant("Schvainsvajger", newCuisine.getId());
    newRestaurant.save();
    assertEquals("German", newRestaurant.getCuisineType());
  }
}
