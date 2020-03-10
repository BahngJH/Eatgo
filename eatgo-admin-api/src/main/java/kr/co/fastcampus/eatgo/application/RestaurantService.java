package kr.co.fastcampus.eatgo.application;

import java.util.List;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantService {

  @Autowired
  RestaurantRepository restaurantRepository;

  @Autowired
  MenuItemRepository menuItemRepository;

  public RestaurantService(RestaurantRepository restaurantRepository,
      MenuItemRepository menuItemRepository) {
    this.restaurantRepository = restaurantRepository;
    this.menuItemRepository = menuItemRepository;
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
