import org.sql2o.*;
import java.util.List;

public class Restaurant {
  private int mId;
  private String mName;
  private int cuisineId;

  public Restaurant (String name, int cuisine_id) {
    this.mName = name;
    this.cuisineId = cuisine_id;
  }

  public int getId() {
    return mId;
  }

  public String getName() {
    return mName;
  }

  public int getCuisineId() {
    return cuisineId;
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      return this.getName().equals(newRestaurant.getName()) &&
        this.getId() == newRestaurant.getId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants(name, cuisine_id) VALUES (:name, :cuisineId)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mName)
        .addParameter("cuisineId", this.cuisineId)
        .executeUpdate()
        .getKey();
    }
  }

  // READ
   public static List<Restaurant> all() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT id AS mId, name AS mName, cuisine_id AS cuisineId FROM restaurants";
      return con.createQuery(sql)
        .executeAndFetch(Restaurant.class);
    }
  }

  //UPDATE
  public void update(String newName, int newCuisineId) {
    this.cuisineId = newCuisineId;
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET cuisine_id = :cuisineId, name = :newName";
      con.createQuery(sql)
        .addParameter("cuisineId", newCuisineId)
        .addParameter("newName", newName)
        .executeUpdate();
    }
  }

  public void update(int newCuisineId) {
    this.cuisineId = newCuisineId;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET cuisine_id = :cuisineId";
      con.createQuery(sql)
        .addParameter("cuisineId", newCuisineId)
        .executeUpdate();
    }
  }

  public void update(String newName) {
    this.mName = newName;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET name = :name";
      con.createQuery(sql)
        .addParameter("name", newName)
        .executeUpdate();
      }
  }

  //DELETE
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM restaurants WHERE id = :id";
    con.createQuery(sql)
      .addParameter("id", mId)
      .executeUpdate();
    }
  }

  public static Restaurant find(int id){
    String sql = "SELECT id AS mId, name AS mName, cuisine_id AS cuisineId FROM restaurants WHERE id = :id";
    try (Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Restaurant.class);
    }
  }

  public String getCuisineType() {
    return Cuisine.find(cuisineId).getType();
  }


}
