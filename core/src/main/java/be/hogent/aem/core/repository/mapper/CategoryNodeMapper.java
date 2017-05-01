package be.hogent.aem.core.repository.mapper;

import be.hogent.aem.core.models.Category;
import be.hogent.aem.core.repository.util.NodeUtil;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CategoryNodeMapper {
    public static final String TRANSLATIONS = "translations";
    public static final String NAME = "name";
    private static final String CATEGORY_ID = "categoryId";
    private static final String ACTIVE = "active";
    private static final String LAST_MODIFIED = "modified";

    public static Category nodeToModel(Node node) throws RepositoryException {
        Category category = null;
        if (node != null) {
            category = new Category();
            category.setId(NodeUtil.findLong(node, CATEGORY_ID));
            category.setActive(NodeUtil.findBool(node, ACTIVE));
            category.setName(getNames(node));
        }
        return category;
    }

    public static void modelToNode(Category model, Node node) throws RepositoryException {
        if (node != null) {
            NodeUtil.setProperty(node, CATEGORY_ID, model.getId());
            NodeUtil.setProperty(node, ACTIVE, model.isActive());
            NodeUtil.setProperty(node, LAST_MODIFIED, new Date());
            setNames(node, model.getName());
        }
    }

    private static Map<String, String> getNames(Node categoryNode) throws RepositoryException {
        Map<String, String> translations = null;
        Node translationsNode = categoryNode.hasNode(TRANSLATIONS) ? categoryNode.getNode(TRANSLATIONS) : null;
        if (translationsNode != null) {
            Iterator<Node> languageNodeIterator = translationsNode.getNodes();
            if (languageNodeIterator != null) {
                translations = new HashMap<String, String>();
                while (languageNodeIterator.hasNext()) {
                    Node languageNode = languageNodeIterator.next();
                    translations.put(languageNode.getName(), NodeUtil.findString(languageNode, NAME));
                }
            }
        }
        return translations;
    }

    private static void setNames(Node parentNode, Map<String, String> translations) throws RepositoryException {
        if (translations != null && translations.size() > 0) {
            Node translationsNode = NodeUtil.findOrCreateChild(parentNode, TRANSLATIONS);
            for (Map.Entry<String, String> entry : translations.entrySet()) {
                String language = entry.getKey().toUpperCase();
                Node languageNode = NodeUtil.findOrCreateChild(translationsNode, language);
                NodeUtil.setProperty(languageNode, NAME, entry.getValue());
            }
        }
    }
}
