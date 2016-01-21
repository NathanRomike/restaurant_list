import org.sql2o.*;
import java.util.List;

public class Cuisine {
  private int mId;
  private String mType;

  public Cuisine (String type) {
    this.mType = type;
  }

  public int getId() {
    return mId;
  }

  public String getType() {
    return mType;
  }

  @Override
  public boolean equals(Object otherCuisine){
    if (!(otherCuisine instanceof Cuisine)) {
      return false;
    } else {
      Cuisine newCuisine = (Cuisine) otherCuisine;
      return this.getType().equals(newCuisine.getType()) &&
        this.getId() == newCuisine.getId();
    }
  }

  //CREATE
  public void save() {
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO cuisine(type) VALUES (:name)";
      this.mId = (int) con.createQuery(sql, true)
        .addParameter("name", this.mType)
        .executeUpdate()
        .getKey();
    }
  }

  //READ
  public static List<Cuisine> all() {
    String sql = "SELECT cuisine_id AS mId, type As mType FROM cuisine";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Cuisine.class);
    }
  }

  //UPDATE
  public void update(String newType) {
    this.mType = newType;
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE cuisine SET type = :type";
      con.createQuery(sql)
        .addParameter("type", newType)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM cuisine WHERE cuisine_id = :id";
      con.createQuery(sql)
        .addParameter("id", mId)
        .executeUpdate();
    }
  }

  public static Cuisine find(int id){
    String sql = "SELECT cuisine_id AS mId, type AS mType FROM cuisine WHERE cuisine_id = :id ";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Cuisine.class);
    }
  }

  /******************************************************
    Students:
    TODO: Create find method
    TODO: Create method to get restaurants
  *******************************************************/

}
