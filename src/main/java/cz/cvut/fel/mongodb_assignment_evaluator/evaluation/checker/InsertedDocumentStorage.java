package cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.evaluation.checker.bson.QueryMatcher;
import lombok.Getter;
import org.bson.BsonDocument;

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
}
