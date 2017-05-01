package be.hogent.aem.core.repository.util;

import org.apache.commons.lang3.StringUtils;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NodeUtil {

    private NodeUtil() {

    }

    public static Long findLong(Node node, String propertyName) throws RepositoryException {
        Property property = findProperty(node, propertyName);
        if (property != null) {
            return property.getLong();
        }
        return null;
    }

    public static String findString(Node node, String propertyName) throws RepositoryException {
        Property property = findProperty(node, propertyName);
        if (property != null) {
            return property.getString();
        }
        return null;
    }

    public static boolean findBool(Node node, String propertyName) throws RepositoryException {
        Property property = findProperty(node, propertyName);
        if (property != null) {
            return property.getBoolean();
        }
        return false;
    }

    public static Property findProperty(Node node, String propertyName) throws RepositoryException {
        if (node.hasProperty(propertyName)) {
            return node.getProperty(propertyName);
        }
        return null;
    }

    public static Node findOrCreateChild(Node parentNode, String childName) throws RepositoryException {
        Node childNode = null;
        if (parentNode != null && StringUtils.isNotBlank(childName)) {
            childNode = parentNode.hasNode(childName) ? parentNode.getNode(childName) : parentNode.addNode(childName);
        }
        return childNode;
    }

    public static void setProperty(Node node, String property, Boolean value) throws RepositoryException {
        if (node != null && value != null) {
            node.setProperty(property, value);
        }
    }

    public static void setProperty(Node node, String property, String value) throws RepositoryException {
        if (node != null && value != null && !"null".equals(value) && StringUtils.isNotEmpty(value)) {
            node.setProperty(property, value);
        }
    }

    public static void setProperty(Node node, String property, Long value) throws RepositoryException {
        if (node != null && value != null) {
            node.setProperty(property, value);
        }
    }

    public static void setProperty(Node node, String property, Date value) throws RepositoryException {
        if (node != null && value != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            node.setProperty(property, sdf.format(value));
        }
    }
}
