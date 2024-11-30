package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import lombok.Getter;
import org.bson.BSONCallback;
import org.bson.Document;

import java.util.*;

@Getter
public class MockMongoDB {
    private final Map<String, Map<String, Document>> collections;

    public MockMongoDB() {
        this.collections = new HashMap<>();
    }

    public boolean createCollection(String collectionName) {
        if (collectionName.isBlank() ||
                containsCollection(collectionName)) {
            return false;
        }
        collections.put(collectionName, new HashMap<>());
        return true;
    }

    public boolean containsCollection(String collectionName) {
        return collections.containsKey(collectionName);
    }

    public List<Document> getCollectionDocuments(String collectionName) {
        if (!collections.containsKey(collectionName)) {
            return Collections.emptyList();
        }
        return collections.get(collectionName).values().stream().toList();
    }

    public boolean insertDocument(String collectionName, Document document) {
        if (!containsCollection(collectionName)) {
            return false;
        }
        Map<String, Document> insertedDocuments = this.collections.get(collectionName);
        Object idObject = document.get("_id");
        String id = (idObject != null)
                ? idObject.toString()
                : String.valueOf(insertedDocuments.size() + 1);
        if (insertedDocuments.containsKey(id)) {
            return false;
        }
        insertedDocuments.put(id, document);
        collections.put(collectionName, insertedDocuments);
        return true;
    }

    public Document findDocument(String collectionName, String id) {
        if (!containsCollection(collectionName)) {
            return null;
        }
        Map<String, Document> documents = collections.get(collectionName);
        if (!documents.containsKey(id)) {
            return null;
        }
        return documents.get(id);
    }

    public Set<String> findAllFieldsOfType(String collectionName, Class<?> clazz) {
        Set<String> fields = new HashSet<>();
        for (Document document : getCollectionDocuments(collectionName)) {
            fields.addAll(BsonChecker.findAllFieldsOfType(document, clazz));
        }
        return fields;
    }

    public boolean containsFieldFirstDocument(String collectionName, Set<String> fields) {
        if (fields.isEmpty()) {
            return false;
        }
        if (!containsCollection(collectionName)) {
            return false;
        }
        List<Document> collectionDocuments = getCollectionDocuments(collectionName);
        if (collectionDocuments.isEmpty()) {
            return false;
        }
        return documentContainsFields(collectionDocuments.get(0), fields);
    }

    public boolean containsFieldsAllDocuments(String collectionName, Set<String> fields) {
        if (fields.isEmpty()) {
            return false;
        }
        if (!containsCollection(collectionName)) {
            return false;
        }
        for (Document document : getCollectionDocuments(collectionName)) {
            if (documentContainsFields(document, fields)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsFieldsById(String collectionName, String id, Set<String> fields) {
        if (fields.isEmpty()) {
            return false;
        }
        if (!containsCollection(collectionName)) {
            return false;
        }
        Document found = findDocument(collectionName, id);
        if (found == null) {
            return false;
        }
        return documentContainsFields(found, fields);
    }

    private boolean documentContainsFields(Document document, Set<String> fields) {
        for (String key : fields) {
            key = key.split("\\.")[0];
            if (!document.containsKey(key)) {
                return false;
            }
        }
        return true;
    }
//    public boolean containsDocument(String collectionName, String id) {
//        return findDocument(collectionName, id) != null;
//    }
}
