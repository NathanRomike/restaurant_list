import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class reviewTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Review.all().size(), 0);
  }

  @Test
  public void instantiateCorrectly() {
    Cuisine newCuisine = new Cuisine("Polish");
    newCuisine.save();
    Restaurant newRestaurant = new Restaurant("Klyacki", newCuisine.getId());
    newRestaurant.save();
    Review review = new Review("Cool", "Nathan", newRestaurant.getId());
    assertEquals("Cool", review.getReview());
    assertEquals("Nathan", review.getUserName());
  }

}
