package org.devgateway.fast.api.commons.controllers;

import org.devgateway.fast.api.commons.domain.categories.Category;
import org.devgateway.fast.api.commons.services.CategoryService;
import org.devgateway.fast.api.commons.pojo.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<Collection<CategoryResponse>> getCategories() {

      List<Category> categories = categoryService.getAllCategories();

        HashMap<String, CategoryResponse> categoriesMap = new HashMap<>();

        categories.stream().forEach((Category o) -> {
            if (categoriesMap.get(o.getType()) == null) {
                categoriesMap.put(o.getType(), new CategoryResponse(o.getType()));
            }

            categoriesMap.get(o.getType()).getItems().add(o);
        });
        return new ResponseEntity<Collection<CategoryResponse>>(categoriesMap.values(), HttpStatus.OK);
    }


}
