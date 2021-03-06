package kr.co.fastcampus.eatgo.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

  @Id
  @GeneratedValue
  @Setter
  private Long id;

  @NotEmpty
  private String name;
  @NotEmpty
  private String address;

  @Transient
  //@JsonInclude(JsonInclude.Include.NON_NULL)
  private List<MenuItem> menuItems;

  @Transient
  private List<Review> reviews;

  public String getInformation() {
    return name + " in " + address;
  }


  public void setMenuItems(List<MenuItem> menuItems) {
    this.menuItems = new ArrayList<>(menuItems);
  }

  public void updateInformation(String name, String address) {
    this.name = name;
    this.address = address;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = new ArrayList<>(reviews);
  }
}
