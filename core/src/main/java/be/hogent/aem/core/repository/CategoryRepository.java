package be.hogent.aem.core.repository;

import be.hogent.aem.core.models.Category;
import be.hogent.aem.core.repository.mapper.CategoryNodeMapper;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.jcr.api.SlingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.nodetype.NodeType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Service(CategoryRepository.class)
public class CategoryRepository {
    public static final String DATA_ROOT = "/etc/commerce/bp-aem-app";
    public static final String MODEL_PATH = DATA_ROOT + "/categories";
    private final Logger logger = LoggerFactory.getLogger("mike");
    @Reference
    protected SlingRepository repository;

    public void save(Category category) throws RepositoryException {
        Session session = null;
        try {
            session = getSession();
            Node node = getCategoryNode(session, category.getId());
            if (node != null) {
                logger.debug("removing category node");
                node.remove();
            }
            logger.debug("creating category node");
            node = JcrUtils.getOrCreateByPath(getPath(category.getId()), NodeType.NT_UNSTRUCTURED, session);
            logger.debug("mapping category to node");
            CategoryNodeMapper.modelToNode(category, node);
            logger.debug("result mapping: " + node);
            session.refresh(true);
            session.save();
        } finally {
            if (session != null) {
                session.logout();
            }
        }
    }

    public Category findOne(Long categoryId) throws RepositoryException {
        Category category;
        Session session = null;
        try {
            session = getSession();
            Node node = getCategoryNode(session, categoryId);
            logger.debug("mapping note to category");
            category = CategoryNodeMapper.nodeToModel(node);
            logger.debug("result mapping: " + category);
        } finally {
            if (session != null) {
                session.logout();
            }
        }
        return category;
    }

    public List<Category> findAll() throws RepositoryException {
        List<Category> categoryList = new ArrayList<>();
        Session session = null;
        try {
            session = getSession();
            Node node = session.getNode(MODEL_PATH);
            if (node != null) {
                Iterator<Node> categoryIterator = node.getNodes();
                while (categoryIterator.hasNext()) {
                    categoryList.add(CategoryNodeMapper.nodeToModel(categoryIterator.next()));
                }
            }
        } finally {
            if (session != null) {
                session.logout();
            }
        }
        return categoryList;
    }

    private Node getCategoryNode(Session session, Long categoryId) throws RepositoryException {
        logger.debug("getting category: " + getPath(categoryId));
        return JcrUtils.getNodeIfExists(getPath(categoryId), session);
    }

    private String getPath(Long categoryId) {
        return String.format("%s/%d", MODEL_PATH, categoryId);
    }

    private Session getSession() throws RepositoryException {
        logger.debug("logging in for seesion");
        return repository.login(new SimpleCredentials("admin", "admin".toCharArray()));
    }
}
