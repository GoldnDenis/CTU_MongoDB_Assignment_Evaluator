package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.BsonDocumentChecker;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.bson.QueryMatcher;
import lombok.Getter;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.*;

@Getter
public class InsertedDocumentStorage {
    private final Map<String, List<BsonDocument>> collections;

    public InsertedDocumentStorage() {
        this.collections = new HashMap<>();
    }

    public List<BsonDocument> getCollectionDocuments(String collection) {
        if (!containsCollection(collection)) {
            return Collections.emptyList();
        }
        return collections.get(collection);
    }

    public boolean containsCollection(String collection) {
        return collection != null && collections.containsKey(collection);
    }

    public boolean createCollection(String collectionName) {
        if (containsCollection(collectionName) ||
            collectionName.isBlank()) {
            return false;
        }
        collections.put(collectionName, new ArrayList<>());
        return true;
    }

    public boolean insertDocument(String collection, BsonDocument document) {
        if (!containsCollection(collection)) {
            return false;
        }
        List<BsonDocument> collectionDocuments = collections.get(collection);
        if (document == null || document.isEmpty() ||
            collectionDocuments.contains(document)) {
            return false;
        }
        collectionDocuments.add(document);
        collections.put(collection, collectionDocuments);
        return true;
    }

    public Optional<BsonDocument> findDocument(String collection, BsonDocument query) {
        if (!containsCollection(collection)) {
            return Optional.empty();
        }
        List<BsonDocument> collectionDocuments = collections.get(collection);
        if (collectionDocuments.isEmpty()) {
            return Optional.empty();
        }
        if (query == null || query.isEmpty()) {
            return Optional.of(collectionDocuments.getFirst());
        }
        for (BsonDocument document : collectionDocuments) {
            if (QueryMatcher.matches(document, query)) {
                return Optional.of(document);
            }
        }
        return Optional.empty();
    }

    // NOT REVIEWED

//    public List<BsonDocument> findDocumentByFilter(String collection, BsonDocument filterDocument) {
//        if (collection == null || filterDocument == null) {
//            return Collections.emptyList();
//        }
//        if (!containsCollection(collection)) {
//            return Collections.emptyList();
//        }
//        Map<String, BsonDocument> insertedDocuments = collections.get(collection);
//        if (insertedDocuments.isEmpty()) {
//            return Collections.emptyList();
//        }
//        if (filterDocument.isEmpty()) {
//            // todo possibly redo to the first one only
////            return List.of(insertedDocuments.values().stream().findFirst().get());
//            return insertedDocuments.values().stream().toList();
//        }
//
//        BsonDocumentMatcher matcher = new BsonDocumentMatcher();
//        return insertedDocuments.values().stream()
//                .filter(document -> matcher.matches(filterDocument, document))
//                .toList();
//    }

//    private boolean compare(BsonDocument filter, BsonDocument document) {
//        for (Map.Entry<String, BsonValue> entry: filter.entrySet()) {
//            List<BsonDocument> foundDocuments = insertedDocuments.values().stream()
//                    .filter(d -> d.containsKey(entry.getKey()))
//                    .filter(d -> d.get(entry.getKey()).equals(entry.getValue()))
//                    .toList();
//            if (!foundDocuments.isEmpty()) {
//                return foundDocuments;
//            }
//        }
//    }

//    public List<BsonDocument> getCollectionDocuments(String collectionName) {
//        if (!collections.containsKey(collectionName)) {
//            return Collections.emptyList();
//        }
//        return collections.get(collectionName).values().stream().toList();
//    }
//
//    public BsonDocument findDocument(String collectionName, String id) {
//        if (!containsCollection(collectionName)) {
//            return null;
//        }
//        Map<String, BsonDocument> documents = collections.get(collectionName);
//        if (!documents.containsKey(id)) {
//            return null;
//        }
//        return documents.get(id);
//    }
//
//    public Set<String> findAllFieldsOfType(String collectionName, Class<?> clazz) {
//        Set<String> fields = new HashSet<>();
//        for (BsonDocument document : getCollectionDocuments(collectionName)) {
//            fields.addAll(BsonDocumentChecker.findAllFieldsOfType(document, clazz));
//        }
//        return fields;
//    }
//
//    public boolean containsFieldFirstDocument(String collectionName, Set<String> fields) {
//        if (fields.isEmpty()) {
//            return false;
//        }
//        if (!containsCollection(collectionName)) {
//            return false;
//        }
//        List<BsonDocument> collectionDocuments = getCollectionDocuments(collectionName);
//        if (collectionDocuments.isEmpty()) {
//            return false;
//        }
//        return documentContainsFields(collectionDocuments.get(0), fields);
//    }
//
//    public boolean containsFieldsAllDocuments(String collectionName, Set<String> fields) {
//        if (fields.isEmpty()) {
//            return false;
//        }
//        if (!containsCollection(collectionName)) {
//            return false;
//        }
//        for (BsonDocument document : getCollectionDocuments(collectionName)) {
//            if (documentContainsFields(document, fields)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean containsFieldsById(String collectionName, String id, Set<String> fields) {
//        if (fields.isEmpty()) {
//            return false;
//        }
//        if (!containsCollection(collectionName)) {
//            return false;
//        }
//        BsonDocument found = findDocument(collectionName, id);
//        if (found == null) {
//            return false;
//        }
//        return documentContainsFields(found, fields);
//    }
//
//    private boolean documentContainsFields(BsonDocument document, Set<String> fields) {
//        for (String key : fields) {
//            key = key.split("\\.")[0];
//            if (!document.containsKey(key)) {
//                return false;
//            }
//        }
//        return true;
//    }
}
