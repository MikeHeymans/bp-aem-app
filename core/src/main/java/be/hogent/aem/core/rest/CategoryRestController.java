package be.hogent.aem.core.rest;

import be.hogent.aem.core.models.Category;
import be.hogent.aem.core.service.CategoryService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Component
@Service(CategoryRestController.class)
@Path("/private/category")
public class CategoryRestController {
    @Reference
    private CategoryService categoryService;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void save(Category category) {
        categoryService.save(category);
    }
}
