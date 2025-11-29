package io.api.week09.controller;

import io.api.week09.domain.Review;
import io.api.week09.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        // Create 25 reviews for user 1
        IntStream.range(0, 25).forEach(i -> {
            reviewRepository.save(Review.builder()
                    .userId(1L)
                    .content("Review content " + i)
                    .build());
        });
    }

    @Test
    void getMyReviews_DefaultPage() throws Exception {
        // Default should be page 0, size 10
        mockMvc.perform(get("/reviews/me")
                        .header("X-User-Id", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)))
                .andExpect(jsonPath("$.pageable.pageNumber", is(0)))
                .andExpect(jsonPath("$.pageable.pageSize", is(10)));
    }

    @Test
    void getMyReviews_Page1() throws Exception {
        // Page 1 (second page)
        mockMvc.perform(get("/reviews/me")
                        .param("page", "1")
                        .header("X-User-Id", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(10)))
                .andExpect(jsonPath("$.pageable.pageNumber", is(1)))
                .andExpect(jsonPath("$.pageable.pageSize", is(10)));
    }

    @Test
    void getMyReviews_Page2() throws Exception {
        // Page 2 (third page), should have 5 items (25 total, 10+10+5)
        mockMvc.perform(get("/reviews/me")
                        .param("page", "2")
                        .header("X-User-Id", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(5)))
                .andExpect(jsonPath("$.pageable.pageNumber", is(2)));
    }

    @Test
    void getMyReviews_InvalidPage_ShouldDefaultTo0() throws Exception {
        mockMvc.perform(get("/reviews/me")
                        .param("page", "invalid")
                        .header("X-User-Id", 1L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageable.pageNumber", is(0)));
    }
}
