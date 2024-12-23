package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker.criteria.composite.factory;

public class CompositeCriterionFactory {
//    public static Optional<CriterionGroup> createGroupCriterion(QueryTypes type, InsertedDocumentStorage documentStorage) {
//        switch (type) {
//            case CREATE_COLLECTION -> {
//                return Optional.of(new CreateCollectionGroupCriterion(documentStorage));
//            }
//            case INSERT -> {
//                return Optional.of(new InsertCriteriaGroup(documentStorage));
//            }
//            case REPLACE_ONE -> {
//                return Optional.of(new ReplaceOneCriteriaGroup(documentStorage));
//            }
//            case UPDATE -> {
//                return Optional.of(new UpdateCriteriaGroup(documentStorage));
//            }
//            case FIND -> {
//                return Optional.of(new FindGroupCriterion(documentStorage));
//            }
//            case AGGREGATE -> {
//                return Optional.of(new AggregateGroupCriterion(documentStorage));
//            }
//            case GENERAL -> {
//                return Optional.of(new GeneralCriterionGroup(documentStorage));
//            }
//            case UNKNOWN -> {
//                return Optional.of(new UnknownQueryCriterion());
//            }
//        }
//        return Optional.empty();
//    }

//    private static CriterionNode createCreateCollection(InsertedDocumentStorage documentStorage) {
//        return new CompositeCriterion(
//                new CreateOneCollectionCriterion(documentStorage)
//        );
//    }

//    private static CriterionNode createInsert(InsertedDocumentStorage documentStorage) {
//        return new CompositeCriterion(
//                new InsertTenDocumentsCriterion(documentStorage),
//                new InsertDocumentEmbeddedObjectsCriterion(documentStorage),
//                new InsertDocumentArraysCriterion(documentStorage),
//                new InsertInterlinkCriterion(documentStorage),
//                new InsertOneUsedCriterion(documentStorage),
//                new InsertManyUsedCriterion(documentStorage)
//        );
//    }

//    private static CriterionNode createReplace(InsertedDocumentStorage documentStorage) {
//        return new GroupCriterion(
//                new ReplaceValueCriterion(documentStorage)
//        );
//    }
//
//    private static CriterionNode createUpdate(InsertedDocumentStorage documentStorage) {
//        return new GroupCriterion(
//                new UpdateOneDocumentCriterion(documentStorage),
//                new UpdateIncreaseMultiplyCriterion(documentStorage),
//                new UpdateAddFieldCriterion(documentStorage),
//                new UpdateRemoveFieldCriterion(documentStorage),
//                new UpdateNestedDocumentCriterion(documentStorage),
//                new UpdateArrayReplaceCriterion(documentStorage),
//                new UpdateArrayAddCriterion(documentStorage),
//                new UpdateArrayRemoveCriterion(documentStorage),
//                new UpdateUpsertUsedCriterion(documentStorage)
//        );
//    }
//
//    private static CriterionNode createFind(InsertedDocumentStorage documentStorage) {
//        return new GroupCriterion(
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
//        );
//    }

//    private static CriterionNode createAggregate(InsertedDocumentStorage documentStorage) {
//        return new CompositeCriterion(
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
//        );
//    }

//    private static CriterionNode createGeneral(InsertedDocumentStorage documentStorage) {
//        return new GroupCriterion(
//                new CommentCriterion()
//        );
//    }
}
