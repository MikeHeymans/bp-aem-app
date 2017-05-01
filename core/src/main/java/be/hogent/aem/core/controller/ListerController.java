package be.hogent.aem.core.controller;

import be.hogent.aem.core.dto.ProductDTO;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import java.util.List;

@Model(adaptables = {SlingHttpServletRequest.class})
public class ListerController {

    @Self
    private SlingHttpServletRequest request;

    public List<ProductDTO> getProducts() {
        return null;
    }
}
