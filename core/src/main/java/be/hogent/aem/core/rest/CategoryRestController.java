package be.hogent.aem.core.rest;

import be.hogent.aem.core.models.Category;
import be.hogent.aem.core.service.CategoryService;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;

@Component
@Service(CategoryRestController.class)
@Path("/private/category")
public class CategoryRestController {
    private final Logger logger = LoggerFactory.getLogger("mike");
    @Reference
    private CategoryService categoryService;

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(Category category) {
        logger.debug("CategoryRestController.save");
        categoryService.save(category);
        return Response.status(200).build();
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Category get() {
        Category cat = new Category();
        cat.setId(666l);
        cat.setActive(true);
        cat.setName(new HashMap<>());
        cat.getName().put("nl", "broek");
        return cat;
    }
}
