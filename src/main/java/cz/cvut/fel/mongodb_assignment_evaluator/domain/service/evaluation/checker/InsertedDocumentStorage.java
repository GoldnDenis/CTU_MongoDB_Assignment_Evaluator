package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.checker.bson.QueryMatcher;
import lombok.Getter;
import org.bson.BsonDocument;

import java.util.*;

@Getter
public class InsertedDocumentStorage {
    private final Map<String, List<BsonDocument>> collectionDocumentsMap;

    public InsertedDocumentStorage() {
        this.collectionDocumentsMap = new HashMap<>();
    }

    public List<BsonDocument> getCollectionDocuments(String collection) {
        if (!containsCollection(collection)) {
            return Collections.emptyList();
        }
        return collectionDocumentsMap.get(collection);
    }

    public boolean containsCollection(String collection) {
        return collection != null && collectionDocumentsMap.containsKey(collection);
    }

    public boolean createCollection(String collectionName) {
        if (containsCollection(collectionName) ||
            collectionName.isBlank()) {
            return false;
        }
        collectionDocumentsMap.put(collectionName, new ArrayList<>());
        return true;
    }

    public boolean insertDocument(String collection, BsonDocument document) {
        if (!containsCollection(collection)) {
            return false;
        }
        List<BsonDocument> collectionDocuments = collectionDocumentsMap.get(collection);
        if (document == null || document.isEmpty() ||
            collectionDocuments.contains(document)) {
            return false;
        }
        collectionDocuments.add(document);
        collectionDocumentsMap.put(collection, collectionDocuments);
        return true;
    }

    public Optional<BsonDocument> findDocument(String collection, BsonDocument query) {
        if (!containsCollection(collection)) {
            return Optional.empty();
        }
        List<BsonDocument> collectionDocuments = collectionDocumentsMap.get(collection);
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
}
