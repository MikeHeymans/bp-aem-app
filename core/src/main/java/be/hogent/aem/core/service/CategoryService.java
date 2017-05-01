package be.hogent.aem.core.service;

import be.hogent.aem.core.manager.CategoryPageManager;
import be.hogent.aem.core.models.Category;
import be.hogent.aem.core.repository.CategoryRepository;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;

import javax.jcr.RepositoryException;
import java.util.List;

@Component
@Service(CategoryService.class)
public class CategoryService {
    @Reference
    private CategoryRepository repository;
    @Reference
    private CategoryPageManager categoryPageManager;

    public void save(Category category) {
        try {
            repository.save(category);
            categoryPageManager.handle(category);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> findAll() {
        try {
            return repository.findAll();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    public Category findOne(Long categoryId) {
        try {
            return repository.findOne(categoryId);
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }
}
