package kr.co.fastcampus.eatgo.application;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantRepository;
import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RestaurantServiceTests {

  private RestaurantService restaurantService;

  @Mock
  private RestaurantRepository restaurantRepository;

  @Mock
  private MenuItemRepository menuItemRepository;

  @Mock
  private ReviewRepository reviewRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    mockRestaurantRepository();
    mockMenuItemRepository();
    mockReviewRepository();

    restaurantService = new RestaurantService(restaurantRepository, menuItemRepository,
        reviewRepository);
  }

  private void mockRestaurantRepository() {
    List<Restaurant> restaurants = new ArrayList<>();
    Restaurant restaurant = Restaurant.builder()
        .id(1004L)
        .name("Bob zip")
        .address("Seoul")
        .build();

    restaurants.add(restaurant);
    given(restaurantRepository.findAll()).willReturn(restaurants);
    given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));
  }

  private void mockMenuItemRepository() {
    List<MenuItem> menuItems = new ArrayList<>();
    menuItems.add(MenuItem.builder()
        .name("Kimchi")
        .build());
    given(menuItemRepository.findAllByRestaurantId(1004L)).willReturn(menuItems);
  }

  private void mockReviewRepository() {
    List<Review> reviews = new ArrayList<>();
    reviews.add(Review.builder()
        .name("BeRyong")
        .score(1)
        .description("Bad")
        .build());
  }

  @Test
  public void getRestaurants() {
    List<Restaurant> restaurants = restaurantService.getRestaurants();
    Restaurant restaurant = restaurants.get(0);
    assertThat(restaurant.getId(), is(1004L));
  }

  @Test
  public void getRestaurantWithExisted() {
    Restaurant restaurant = restaurantService.getRestaurant(1004L);

    assertThat(restaurant.getId(), is(1004L));
  }

  @Test
  public void getRestaurantWithNotExisted() {
    //Restaurant restaurant = restaurantService.getRestaurant(404L);
  }

  @Test
  public void addRestaurant() {
    given(restaurantRepository.save(any())).will(invocation -> {
      Restaurant restaurant = invocation.getArgument(0);
      restaurant.setId(1234L);
      return restaurant;
    });

    Restaurant restaurant = Restaurant.builder()
        .name("BeRyong")
        .address("Busan")
        .build();

    Restaurant created = restaurantService.addRestaurant(restaurant);

    assertThat(created.getId(), is(1234L));
  }

  @Test
  public void updateRestaurant() {
    Restaurant restaurant = Restaurant.builder()
        .id(1004L)
        .name("Bob zip")
        .address("Seoul")
        .build();

    given(restaurantRepository.findById(1004L)).willReturn(Optional.of(restaurant));

    restaurantService.updateRestaurant(1004L, "Sool zip", "Busan");

    assertThat(restaurant.getName(), is("Sool zip"));
  }
}