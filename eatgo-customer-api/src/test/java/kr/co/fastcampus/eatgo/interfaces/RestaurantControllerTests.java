package kr.co.fastcampus.eatgo.interfaces;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.eatgo.application.RestaurantService;
import kr.co.fastcampus.eatgo.domain.Restaurant;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RestaurantController.class)
@AutoConfigureRestDocs()
public class RestaurantControllerTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private RestaurantService restaurantService;

  @Test
  public void list() throws Exception {
    List<Restaurant> restaurants = new ArrayList<>();
    restaurants.add(Restaurant.builder()
        .id(1004L)
        .name("JOKER House")
        .address("Seoul")
        .build());

    given(restaurantService.getRestaurants()).willReturn(restaurants);

    mvc.perform(get("/restaurants"))
        .andDo(print())
        .andExpect(status().isOk())
        .andDo(document("list-api"))
        .andExpect(content().string(containsString("\"id\":1004")))
        .andExpect(content().string(containsString("\"name\":\"JOKER House\"")));
  }

  @Test
  public void detail() throws Exception {
    Restaurant restaurant1 = Restaurant.builder()
        .id(1004L)
        .name("JOKER House")
        .address("Seoul")
        .build();

    given(restaurantService.getRestaurant(1004L)).willReturn(restaurant1);

    mvc.perform(get("/restaurants/1004"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("\"id\":1004")))
        .andExpect(content().string(containsString("\"name\":\"JOKER House\"")));

  }

  @Test
  public void detailWithNotExisted() throws Exception {
    given(restaurantService.getRestaurant(404L))
        .willThrow(new RestaurantNotFoundException(404L));
    mvc.perform(get("/restaurants/404"))
        .andExpect(status().isNotFound())
        .andExpect(content().string("{}"));
  }

  @Test
  public void createWithValidData() throws Exception {
    given(restaurantService.addRestaurant(any())).will(invocation -> {
      Restaurant restaurant = invocation.getArgument(0);
      return Restaurant.builder()
          .id(1234L)
          .name(restaurant.getName())
          .address(restaurant.getAddress())
          .build();
    });

    mvc.perform(post("/restaurants")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"BeRyong\",\"address\":\"Busan\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("location", "/restaurants/1234"))
        .andExpect(content().string("{}"));
    verify(restaurantService).addRestaurant(any());
  }

  @Test
  public void createWithInvalidData() throws Exception {
    mvc.perform(post("/restaurants")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"\",\"address\":\"\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateWithValidData() throws Exception {
    mvc.perform(patch("/restaurants/1004")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"JOKER Bar\", \"address\": \"Busan\"}"))
        .andExpect(status().isOk());

    verify(restaurantService).updateRestaurant(1004L, "JOKER Bar", "Busan");
  }

  @Test
  public void updateWithInvalidData() throws Exception {
    mvc.perform(patch("/restaurants/1004")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"\", \"address\": \"\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void updateWithoutName() throws Exception {
    mvc.perform(patch("/restaurants/1004")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"name\":\"\", \"address\": \"\"}"))
        .andExpect(status().isBadRequest());
  }
}