package com.usktea.plainold.applications;

import com.usktea.plainold.models.Category;
import com.usktea.plainold.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class GetCategoryServiceTest {
    private GetCategoryService getCategoryService;
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setup() {
        categoryRepository = mock(CategoryRepository.class);
        getCategoryService = new GetCategoryService(categoryRepository);
    }

    @Test
    void list() {
        given(categoryRepository.findAll())
                .willReturn(List.of(Category.fake()));

        GetCategoryService getCategoryService = new GetCategoryService(categoryRepository);

        List<Category> categories = getCategoryService.list();

        assertThat(categories.size()).isEqualTo(1);
    }
}
