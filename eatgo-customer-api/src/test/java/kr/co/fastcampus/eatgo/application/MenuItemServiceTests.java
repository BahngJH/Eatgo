package kr.co.fastcampus.eatgo.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import kr.co.fastcampus.eatgo.domain.MenuItem;
import kr.co.fastcampus.eatgo.domain.MenuItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MenuItemServiceTests {

  private MenuItemService menuItemService;

  @Mock
  private MenuItemRepository menuItemRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    menuItemService = new MenuItemService(menuItemRepository);
  }

  @Test
  public void bulkUpdate() {
    List<MenuItem> menuItems = new ArrayList<MenuItem>();

    menuItems.add(MenuItem.builder().name("Kimchi").build());
    menuItems.add(MenuItem.builder().name("Gukbob").build());
    menuItems.add(MenuItem.builder().id(1004L).destroy(true).build());

    menuItemService.bulkUpdate(1L, menuItems);

    verify(menuItemRepository, times(2)).save(any());
    verify(menuItemRepository, times(1)).deleteById(eq(1004L));
  }
}