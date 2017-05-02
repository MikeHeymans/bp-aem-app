package be.hogent.aem.core.controller;

import be.hogent.aem.core.dto.CategoryDTO;
import be.hogent.aem.core.service.CategoryService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.Self;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

@Model(adaptables = {Resource.class})
public class CategoryController {
    @Self
    private Resource resource;
    @Inject
    @Named("amount")
    @Optional
    private String amount;
    @OSGiService
    private CategoryService categoryService;

    public List<CategoryDTO> getCategories() {
//        categoryService.findAll();
        List<CategoryDTO> categoryDTOs = Arrays.asList(
                new CategoryDTO(1l, "broeken"),
                new CategoryDTO(2l, "jassen"),
                new CategoryDTO(3l, "slaapzakken"),
                new CategoryDTO(4l, "sjaals"),
                new CategoryDTO(5l, "goPro or goHome")
        );
        return categoryDTOs;
    }
}
