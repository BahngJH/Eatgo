package kr.co.fastcampus.eatgo.application;

import java.util.List;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService {

  private MenuItemRepository menuItemRepositoy;

  @Autowired
  public MenuItemService(MenuItemRepository menuItemRepositoy) {
    this.menuItemRepositoy = menuItemRepositoy;
  }

  public void bulkUpdate(Long restaurantId, List<MenuItem> menuItems) {
    for (MenuItem menuItem : menuItems) {
      update(restaurantId, menuItem);
    }
  }

  private void update(Long restaurantId, MenuItem menuItem) {
    if (menuItem.isDestroy()) {
      //TODO: delete
      menuItemRepositoy.deleteById(menuItem.getId());
      return;
    }
    menuItem.setRestaurantId(restaurantId);
    menuItemRepositoy.save(menuItem);
  }
}
