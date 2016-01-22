import org.sql2o.*;
import java.util.List;

public class Review {
  private int mId;
  private String mReview;
  private String mUserName;
  private int mRestaurantId;

  public Review (String review, String username, int restaurantId) {
    this.mReview = review;
    this.mUserName = username;
    this.mRestaurantId = restaurantId;
  }

  public int getId() {
    return mId;
  }

  public String getReview() {
    return mReview;
  }

  public String getUserName() {
    return mUserName;
  }

  public int getRestaurantById() {
    return mRestaurantId;
  }

  public static List<Review> all() {
    String sql = "SELECT id AS mId, review AS mReview, restaurant_id AS mRestaurantId, user_name AS mUserName FROM reviews";
    try (Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .executeAndFetch(Review.class);
    }
  }


}
