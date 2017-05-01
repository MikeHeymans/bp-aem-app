package be.hogent.aem.core.manager;

import be.hogent.aem.core.models.Category;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.PageManagerFactory;
import com.day.cq.wcm.api.WCMException;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.Calendar;
import java.util.Iterator;

@Component
@Service(CategoryPageManager.class)
public class CategoryPageManager {
    private static final String ROOT_PATH = "/content/aem-test-content/";
    private static final String ROOT_TEMPLATE = "/apps/aem-test-app/templates/category-root-page";
    private static final String CATEGORY_TEMPLATE = "/apps/aem-test-app/templates/category-page";
    private static final String ROOT_NODE_NAME = "categories";
    private static final String ROOT_TITLE = "categories";
    private static final String CATEGORY_TITLE = "Category";
    @Reference
    private ResourceResolverFactory resourceResolverFactory;
    @Reference
    private PageManagerFactory pageManagerFactory;

    public void handle(Category category) throws Exception {
        ResourceResolver resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
        PageManager pageManager = pageManagerFactory.getPageManager(resourceResolver);
        Page rootPage = pageManager.getPage(ROOT_PATH);
        Session session = resourceResolver.adaptTo(Session.class);
        handlePages(category, rootPage.listChildren(), resourceResolver, pageManager);
        session.refresh(true);
        session.save();
    }

    private void handlePages(Category category, Iterator<Page> languagePages, ResourceResolver resourceResolver, PageManager pageManager) throws Exception {
        while (languagePages.hasNext()) {
            handlePage(category, languagePages.next(), resourceResolver, pageManager);
        }
    }

    private void handlePage(Category category, Page languagePage, ResourceResolver resourceResolver, PageManager pageManager) throws Exception {
        Page categoryRootPage = getCategoryRootPage(languagePage, pageManager);
        Page categoryPage = pageManager.getPage(categoryRootPage.getPath() + "/" + category.getId());
        if (category.isActive()) {
            savePage(category, languagePage, resourceResolver, pageManager, categoryRootPage);
        } else if (categoryPage != null) {
            pageManager.delete(categoryPage, false);
        }
    }

    private void savePage(Category category, Page languagePage, ResourceResolver resourceResolver, PageManager pageManager, Page categoryRootPage) throws Exception {
        Page categoryPage = pageManager.getPage(categoryRootPage.getPath() + "/" + category.getId());
        if (categoryPage == null) {
            categoryPage = pageManager.create(categoryRootPage.getPath(), String.valueOf(category.getId()), CATEGORY_TEMPLATE, CATEGORY_TITLE);
            injectComponents(category, categoryPage, "nl");
        }
    }

    private void injectComponents(Category category, Page categoryPage, String language) throws RepositoryException {
        Resource contentResource = categoryPage.getContentResource();
        Node node = contentResource.adaptTo(Node.class);
        node.setProperty("jcr:name", category.getName(language));
        node.setProperty("jcr:id", category.getId());
        node.setProperty("jcr:modified", Calendar.getInstance());

    }

    private Page getCategoryRootPage(Page languagePage, PageManager pageManager) throws WCMException {
        String categoryPath = languagePage.getPath() + "/" + ROOT_NODE_NAME;
        Page categoryRootPage = pageManager.getPage(categoryPath);
        if (categoryRootPage == null) {
            categoryRootPage = pageManager.create(languagePage.getPath(), ROOT_NODE_NAME, ROOT_TEMPLATE, ROOT_TITLE);
        }
        return categoryRootPage;
    }
}
