package kr.co.fastcampus.eatgo.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import kr.co.fastcampus.eatgo.domain.Review;
import kr.co.fastcampus.eatgo.domain.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ReviewServiceTests {

  private ReviewService reviewService;

  @Mock
  private ReviewRepository reviewRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    reviewService = new ReviewService(reviewRepository);
  }

  @Test
  public void addReview() {
    Review review = Review.builder()
        .name("JOKER")
        .score(3)
        .description("맛있다.")
        .build();
    reviewService.addReview(review);

    verify(reviewRepository).save(any());
  }
}