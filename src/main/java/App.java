import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    /******************************************************
      Students: TODO: Display all restaurants on main page
    *******************************************************/
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    /******************************************************
    Students: TODO: Create page to add a new restaurant
    *******************************************************/
    get("/newrestaurant", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/newrestaurant.vtl");

      model.put("cuisines", Cuisine.all());

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/restaurants/:id", (request, reponse) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/restaurants.vtl");

      model.put("restaurants", Restaurant.getRestaurantsByCuisine(Integer.parseInt(request.params("id"))));
      


      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/newcuisine", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");

      Cuisine newCuisine = new Cuisine(request.queryParams("cuisineType"));
      newCuisine.save();

      model.put("cuisines", Cuisine.all());

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/new-restaurant", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");

      Restaurant newRestaurant = new Restaurant(request.queryParams("newRestaurant"), (Integer.parseInt(request.queryParams("cuisineSlection"))));
      newRestaurant.save();

      model.put("cuisines", Cuisine.all());

      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
    /******************************************************
    STUDENTS:
    TODO: Create page to display information about the selected restaurant
    TODO: Create page to display restaurants by cuisine type
    *******************************************************/

  }
}
