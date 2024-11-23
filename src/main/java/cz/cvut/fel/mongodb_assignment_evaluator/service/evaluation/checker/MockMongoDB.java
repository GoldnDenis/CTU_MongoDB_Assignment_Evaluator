package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

public class MockMongoDB {
    private final Map<String, Map<Integer, Document>> collections;

    public MockMongoDB() {
        this.collections = new HashMap<>();
    }

    public boolean createCollection(String collectionName) {
        if (collections.containsKey(collectionName)) {
            return false;
        }
        collections.put(collectionName, new HashMap<>());
        return true;
    }

    public boolean containsCollection(String collectionName) {
        return collections.containsKey(collectionName);
    }

    public boolean insertDocument(String collectionName, Document document) {
        if (!collections.containsKey(collectionName)) {
            return false;
        }
        int id = document.getInteger("id");
        Map<Integer, Document> documents = this.collections.get(collectionName);
        if (documents.containsKey(id)) {
            return false;
        }
        documents.put(id, document);
        collections.put(collectionName, documents);
        return true;
    }

    public Document findDocument(String collectionName, int id) {
        if (!collections.containsKey(collectionName)) {
            return null;
        }
        Map<Integer, Document> documents = this.collections.get(collectionName);
        if (!documents.containsKey(id)) {
            return null;
        }
        return documents.get(id);
    }
}
