package cz.cvut.fel.mongodb_assignment_evaluator.domain.service.evaluation.newcri;

import cz.cvut.fel.mongodb_assignment_evaluator.domain.model.query.type.Query;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class EvaluationCriterion<Q extends Query>  {
   private final Class<Q> queryType;
   private int maxScore;
   private List<Query> fulfilledQueries;
   protected int currentScore;

   public EvaluationCriterion(Class<Q> queryType) {
      this.queryType = queryType;
      this.maxScore = 0;
      this.fulfilledQueries = new ArrayList<>();
      this.currentScore = 0;
   }

   public void check(Query query) {
      currentScore = 0;
      evaluate(queryType.cast(query));
      if (currentScore > 0) {
         maxScore += currentScore;
         fulfilledQueries.add(query);
      }
//      if (queryType.isInstance(query)) {
//         currentScore = 0;
//         evaluate(queryType.cast(query));
//         if (currentScore > 0) {
//            maxScore += currentScore;
//            fulfilledQueries.add(query);
//         }
////         evaluationResult.saveQuery(query, currentScore);
//      }
   }

   protected abstract void evaluate(Q query);

   public void reset() {
      maxScore = 0;
      currentScore = 0;
      fulfilledQueries.clear();
   }

//   protected Criteria criterion;
//   //    protected final InsertedDocumentStorage documentStorage;
//   protected int currentScore;
//   protected CriterionEvaluationResult evaluationResult;
//
//   public CheckerLeaf(Criteria criterion, Class<Q> queryType) {
//      this.evaluationResult = new CriterionEvaluationResult(criterion);
//      this.queryType = queryType;
//      this.currentScore = 0;
//   }
//
////    public CheckerLeaf(Criteria criterion, Class<Q> queryType, InsertedDocumentStorage documentStorage) {
////        this.evaluationResult = new CriterionEvaluationResult(criterion);
////        this.queryType = queryType;
////        this.documentStorage = documentStorage;
////        this.currentScore = 0;
////    }
//
//   @Override
//   public void check(Query query) {
//      if (queryType.isInstance(query)) {
//         Q q = queryType.cast(query);
//         currentScore = 0;
//         concreteCheck(q);
//         evaluationResult.saveQuery(query, currentScore);
//      }
//   }
//
//   protected abstract void concreteCheck(Q query);
//
//   @Override
//   public List<CriterionEvaluationResult> evaluate() {
//      return List.of(evaluationResult);
//   }
}
