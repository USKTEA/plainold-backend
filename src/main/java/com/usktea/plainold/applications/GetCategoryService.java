package com.usktea.plainold.applications;

import com.usktea.plainold.models.Category;
import com.usktea.plainold.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class GetCategoryService {
    private final CategoryRepository categoryRepository;

    public GetCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> list() {
        List<Category> categories = categoryRepository.findAll();

        return categories;
    }
}
