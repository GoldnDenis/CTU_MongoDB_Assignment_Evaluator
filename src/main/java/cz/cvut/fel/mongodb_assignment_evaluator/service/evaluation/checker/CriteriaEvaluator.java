package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import cz.cvut.fel.mongodb_assignment_evaluator.service.enums.QueryTypes;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.CriterionNode;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.EvaluationResult;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.group.*;
import cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.specific.unknown.UnknownQueryCriterion;
import cz.cvut.fel.mongodb_assignment_evaluator.service.model.query.type.*;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log
public class CriteriaEvaluator {
//public class RootCompositeCriterion implements QueryVisitor {
//    private final Map<QueryTypes, CriterionNode<? extends Query>> children;
    private final InsertedDocumentStorage documentStorage;
    private final Map<QueryTypes, CriterionNode> children;
    private final GeneralCriterionGroup generalCriterionGroup;

    public CriteriaEvaluator() {
        documentStorage = new InsertedDocumentStorage();
        children = new LinkedHashMap<>(initCriteriaGroup());
        generalCriterionGroup = new GeneralCriterionGroup(documentStorage);
    }

    public List<EvaluationResult> check(List<Query> queries) {
        queries.forEach(this::checkQuery);
        return collectAllResults();
    }

    private void checkQuery(Query query) {
        QueryTypes type = query.getType();
        if (!children.containsKey(type)) {
            log.severe("Criteria for Query type " + type + " are not defined.");
            return;
        }
        if (type.isInCollectionScope()
            && !documentStorage.containsCollection(query.getCollection())) {
            log.severe(query.getQuery() + " is working on a non-existent collection='" + query.getCollection() + "'.");
            return;
        }
        if (type != QueryTypes.UNKNOWN) {
            generalCriterionGroup.check(query);
//            ((CriterionNode<Query>) children.get(QueryTypes.GENERAL)).check(query);
        }

//        query.accept(this);
        children.get(type).check(query);
    }

    private List<EvaluationResult> collectAllResults() {
        List<EvaluationResult> results = new ArrayList<>(
                children.values().stream()
                        .flatMap(child -> child.evaluate().stream())
                        .toList()
        );
        results.addAll(generalCriterionGroup.evaluate());
        return results;
    }

//    private Map<QueryTypes, CriterionNode<? extends Query>> initCriteriaGroup() {
    private Map<QueryTypes, CriterionNode> initCriteriaGroup() {
        return Map.of(
                QueryTypes.CREATE_COLLECTION, new CreateCollectionGroupCriterion(documentStorage),
                QueryTypes.INSERT, new InsertCriteriaGroup(documentStorage),
                QueryTypes.REPLACE_ONE, new ReplaceOneCriteriaGroup(documentStorage),
                QueryTypes.UPDATE, new UpdateCriteriaGroup(documentStorage),
                QueryTypes.FIND, new FindGroupCriterion(documentStorage),
                QueryTypes.AGGREGATE, new AggregateGroupCriterion(documentStorage),
                QueryTypes.UNKNOWN, new UnknownQueryCriterion(documentStorage)
        );
//        children.put(QueryTypes.CREATE_COLLECTION, new CreateCollectionGroupCriterion(documentStorage));
//        children.put(QueryTypes.INSERT, new InsertCriteriaGroup(documentStorage));
//        children.put(QueryTypes.REPLACE_ONE, new ReplaceOneCriteriaGroup(documentStorage));
//        children.put(QueryTypes.UPDATE, new UpdateCriteriaGroup(documentStorage));
//        children.put(QueryTypes.AGGREGATE, new AggregateGroupCriterion(documentStorage));
//        children.put(QueryTypes.FIND, new FindGroupCriterion(documentStorage));
//        children.put(QueryTypes.UNKNOWN, new UnknownQueryCriterion(documentStorage));
//        children.put(QueryTypes.GENERAL, new GeneralCriterionGroup(documentStorage));

//        for (QueryTypes type : QueryTypes.values()) {
//            if (children.containsKey(type)) {
//                log.severe("Criteria for Query type: '" + type + "' is already initialized.");
//                continue;
//            }
//            CompositeCriterionFactory.createGroupCriterion(type, documentStorage).ifPresent(
//                    group -> children.put(type, group)
//            );
//        }
    }

//    @Override
//    public void visitCreateCollection(CreateCollectionQuery createCollectionQuery) {
//        ((CriterionNode<CreateCollectionQuery>) children.get(QueryTypes.CREATE_COLLECTION)).check(createCollectionQuery);
//    }
//
//    @Override
//    public void visitInsertQuery(InsertQuery insertQuery) {
//        ((CriterionNode<InsertQuery>) children.get(QueryTypes.INSERT)).check(insertQuery);
//    }
//
//    @Override
//    public void visitReplaceOneQuery(ReplaceOneQuery replaceOneQuery) {
//        ((CriterionNode<ReplaceOneQuery>) children.get(QueryTypes.REPLACE_ONE)).check(replaceOneQuery);
//    }
//
//    @Override
//    public void visitUpdateQuery(UpdateQuery updateQuery) {
//        ((CriterionNode<UpdateQuery>) children.get(QueryTypes.UPDATE)).check(updateQuery);
//    }
//
//    @Override
//    public void visitAggregateQuery(AggregateQuery aggregateQuery) {
//        ((CriterionNode<AggregateQuery>) children.get(QueryTypes.AGGREGATE)).check(aggregateQuery);
//    }
//
//    @Override
//    public void visitFindQuery(FindQuery findQuery) {
//        ((CriterionNode<FindQuery>) children.get(QueryTypes.FIND)).check(findQuery);
//    }
//
//    @Override
//    public void visitUnknownQuery(UnknownQuery unknownQuery) {
//        ((CriterionNode<UnknownQuery>) children.get(QueryTypes.UNKNOWN)).check(unknownQuery);
//    }

//    private void initCriteriaGroups(InsertedDocumentStorage documentStorage) {
//        children.put(QueryTypes.CREATE_COLLECTION, new CompositeCriterion(
//                new CreateOneCollectionCriterion(documentStorage)
//        ));
//        children.put(QueryTypes.INSERT, new CompositeCriterion(
//                new InsertTenDocumentsCriterion(documentStorage),
//                new InsertDocumentEmbeddedObjectsCriterion(documentStorage),
//                new InsertDocumentArraysCriterion(documentStorage),
//                new InsertInterlinkCriterion(documentStorage),
//                new InsertOneUsedCriterion(documentStorage),
//                new InsertManyUsedCriterion(documentStorage)
//        ));
//        children.put(QueryTypes.REPLACE_ONE, new CompositeCriterion(
//                new ReplaceValueCriterion(documentStorage)
//        ));
//        children.put(QueryTypes.UPDATE, new CompositeCriterion(
//                new UpdateOneDocumentCriterion(documentStorage),
//                new UpdateIncreaseMultiplyCriterion(documentStorage),
//                new UpdateAddFieldCriterion(documentStorage),
//                new UpdateRemoveFieldCriterion(documentStorage),
//                new UpdateNestedDocumentCriterion(documentStorage),
//                new UpdateArrayReplaceCriterion(documentStorage),
//                new UpdateArrayAddCriterion(documentStorage),
//                new UpdateArrayRemoveCriterion(documentStorage),
//                new UpdateUpsertUsedCriterion(documentStorage)
//        ));
//        children.put(QueryTypes.FIND, new CompositeCriterion(
//                new FindFiveQueriesCriterion(documentStorage),
//                new FindLogicalOperatorCriterion(documentStorage),
//                new FindElemMatchUsedCriterion(documentStorage),
//                new FindPositiveProjectionCriterion(documentStorage),
//                new FindNegativeProjectionCriterion(documentStorage),
//                new FindSortModifierCriterion(documentStorage),
//                new FindValueCriterion(documentStorage),
//                new FindConditionCriterion(documentStorage),
//                new FindDateComparisonCriterion(documentStorage),
//                new FindArrayCriterion(documentStorage),
//                new FindArrayValueCriterion(documentStorage)
//        ));
//        children.put(QueryTypes.AGGREGATE, new CompositeCriterion(
//                new AggregateFiveCriterion(documentStorage),
//                new AggregateMatchStageCriterion(documentStorage),
//                new AggregateGroupStageCriterion(documentStorage),
//                new AggregateSortStageCriterion(documentStorage),
//                new AggregateProjectAddFieldsStageCriterion(documentStorage),
//                new AggregateSkipStageCriterion(documentStorage),
//                new AggregateLimitStageCriterion(documentStorage),
//                new AggregateSumAvgAggregatorCriterion(documentStorage),
//                new AggregateCountAggregatorCriterion(documentStorage),
//                new AggregateMinMaxAggregatorCriterion(documentStorage),
//                new AggregateFirstLastAggregatorCriterion(documentStorage),
//                new AggregateLookupUsedCriterion(documentStorage)
//        ));
//        children.put(QueryTypes.GENERAL, new CompositeCriterion(
//                new CommentCriterion()
//        ));
//        children.put(QueryTypes.UNKNOWN, new UnknownQueryCriterion());
//    }
}
