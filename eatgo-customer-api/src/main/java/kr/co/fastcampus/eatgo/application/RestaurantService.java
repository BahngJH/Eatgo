package kr.co.fastcampus.eatgo.application;

import java.util.List;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantService {


  private RestaurantRepository restaurantRepository;
  private MenuItemRepository menuItemRepository;
  private ReviewRepository reviewRepository;

  public RestaurantService(RestaurantRepository restaurantRepository,
      MenuItemRepository menuItemRepository, ReviewRepository reviewRepository) {
    this.restaurantRepository = restaurantRepository;
    this.menuItemRepository = menuItemRepository;
    this.reviewRepository = reviewRepository;
  }

  public List<Restaurant> getRestaurants() {
    List<Restaurant> restaurants = restaurantRepository.findAll();
    return restaurants;
  }

  public Restaurant getRestaurant(Long id) {
    Restaurant restaurant = restaurantRepository.findById(id)
        .orElseThrow(() -> new RestaurantNotFoundException(id));
    List<MenuItem> menuItems = menuItemRepository.findAllByRestaurantId(id);
    restaurant.setMenuItems(menuItems);

    List<Review> reviews = reviewRepository.findAllByRestaurantId(id);
    restaurant.setReviews(reviews);

    return restaurant;
  }

  public Restaurant addRestaurant(Restaurant restaurant) {
    return restaurantRepository.save(restaurant);
  }

  @Transactional
  public Restaurant updateRestaurant(Long id, String name, String address) {

    Restaurant restaurant = restaurantRepository.findById(id).orElse(null);

    restaurant.updateInformation(name, address);

    return restaurant;
  }
}
