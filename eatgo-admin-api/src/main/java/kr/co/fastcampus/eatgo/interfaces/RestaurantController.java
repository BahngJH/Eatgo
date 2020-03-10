package kr.co.fastcampus.eatgo.interfaces;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RestaurantController {

  @Autowired
  private RestaurantService restaurantService;

  @GetMapping("/restaurants")
  public List<Restaurant> list() {
    List<Restaurant> restaurants = restaurantService.getRestaurants();

    return restaurants;
  }

  @GetMapping("/restaurants/{id}")
  public Restaurant detail(@PathVariable("id") Long id) {
    Restaurant restaurant = restaurantService.getRestaurant(id);

    return restaurant;
  }

  @PostMapping("/restaurants")
  public ResponseEntity<?> create(@Valid @RequestBody Restaurant resource)
      throws URISyntaxException {

    Restaurant restaurant = Restaurant.builder()
        .id(1234L)
        .name(resource.getName())
        .address(resource.getAddress())
        .build();
    restaurantService.addRestaurant(restaurant);
    URI location = new URI("/restaurants/" + restaurant.getId());
    return ResponseEntity.created(location).body("{}");
  }

  @PatchMapping("/restaurants/{id}")
  public String update(@PathVariable("id") Long id,
      @Valid @RequestBody Restaurant resource) {
    String name = resource.getName();
    String address = resource.getAddress();
    restaurantService.updateRestaurant(id, name, address);
    return "{}";
  }
}
