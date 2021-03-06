package org.devgateway.fast.api.commons.services;

import org.devgateway.fast.api.commons.domain.categories.Category;
import org.devgateway.fast.api.commons.domain.categories.QCategory;
import org.devgateway.fast.api.commons.repositories.CategoryRepository;
import org.devgateway.fast.api.commons.domain.LocaleText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Sebastian Dimunzio
 */
@Service
public class CategoryService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public String codify(String label) {
        return label.trim().toUpperCase().replaceAll(" ", "_").replaceAll("[^a-zA-Z0-9_]", "");
    }

    private CategoryRepository categoryRepository;


    @Autowired
    public CategoryService(final CategoryRepository categoryRepository, final TranslationService translationService) {
        this.categoryRepository = categoryRepository;
    }


    private Category createNewCategory(final Class<Category> tClass) {
        try {
            Category t = tClass.getConstructor(null).newInstance();

            return t;
        } catch (InstantiationException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException | NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }


    public Category create(String value, String code, final Class type) {

        return create(value, code, null, type, null, null, true, null);

    }

    public Category create(String value, String code, final Class type, Integer position) {

        return create(value, code, null, type, position, null, true, null);

    }

    /**
     * Get without create
     */
    public Category get(String value, final Class type) {

        return create(value, codify(value), null, type, null, null, false, null);

    }

    public Category create(String value, final Class type) {

        return create(value, codify(value), null, type, null, null, true, null);

    }

    public Category create(final String value, final Class type, Integer position) {
        return create(value, codify(value), null, type, position, null, true, null);
    }


    private Category create(final String value, String code, List<LocaleText> labels, final Class type,
                            final Integer position, List<LocaleText> descriptions, final Boolean create,
                            Category parent) {
        try {

            if (value != null && value.isEmpty() && create) {
                return null; //null value will never create a new category
            }

            if ((value == null || value.trim().isEmpty()) && create == true) {
                throw new Exception("Value can't be null if create is true");
            }

            String typeName = type.getName().substring(type.getName().lastIndexOf(".") + 1);
            QCategory cat = QCategory.category;
            Iterable<Category> items = (code != null) ? categoryRepository.findAll(cat.code.equalsIgnoreCase(code).and(cat.type.equalsIgnoreCase(typeName))) : categoryRepository.findAll(cat.value.equalsIgnoreCase(value).and(cat.type.equalsIgnoreCase(typeName)));

            if (items.iterator().hasNext()) {
                Category existing = items.iterator().next();
                return existing;
            } else if (create) {
                final Category retValue = createNewCategory(type);
                retValue.setValue(value);
                retValue.setCode(code.toUpperCase());
                retValue.setPosition(position);
                retValue.setDescriptions(descriptions);
                retValue.setLabels(labels);
                retValue.setParent(parent);
                categoryRepository.saveAndFlush(retValue);

                return retValue;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
