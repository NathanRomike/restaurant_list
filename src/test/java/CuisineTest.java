import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
      assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void cuisine_succesfullyCreated(){
    Cuisine newCuisine = new Cuisine("Chinese");
    newCuisine.save();
    //assertTrue(newCuisine.instanceof(Cuisine));
    assertEquals("Chinese", newCuisine.getType());
  }

  @Test
  public void cuisine_seccesfulUpdate() {
    Cuisine newCuisine = new Cuisine("Chinese");
    newCuisine.save();
    newCuisine.update("Japanese");
    assertEquals("Japanese", newCuisine.all().get(0).getType());
  }

  @Test
  public void cuisine_successfulDelete(){
    Cuisine newCuisine = new Cuisine("Ukrainian");
    newCuisine.save();
    newCuisine.delete();
    assertEquals(0, newCuisine.all().size());
  }

  @Test
  public void cuisine_seccessfulFind() {
    Cuisine newCuisine = new Cuisine("Ukrainian");
    newCuisine.save();
    Cuisine savedCuisine = Cuisine.find(newCuisine.getId());
    assertTrue(newCuisine.equals(savedCuisine));
  }
}
