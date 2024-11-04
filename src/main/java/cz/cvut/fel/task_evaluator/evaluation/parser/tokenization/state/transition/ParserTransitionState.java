package cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.transition;

import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.comment.MultiLineCommentState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.comment.SingleLineCommentState;
import cz.cvut.fel.task_evaluator.evaluation.parser.iterator.LineIterator;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.ParserStateMachine;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.ParserState;
import cz.cvut.fel.task_evaluator.evaluation.parser.tokenization.state.query.QueryState;

public class ParserTransitionState extends ParserState {

    public ParserTransitionState(ParserStateMachine context) {
        super(context);
    }

    @Override
    public void process(LineIterator iterator) {
        if (!iterator.contains("//")
                && !iterator.contains("/*")
                && !iterator.contains("db.")) {
            iterator.nextAll();
        } else if (iterator.startsWith("//")) {
            context.setState(new SingleLineCommentState(context));
        } else if (iterator.startsWith("/*")) {
            context.setState(new MultiLineCommentState(context));
        } else if (iterator.startsWith("db.")) {
            context.resetQueryBuilder();
            setQueryPosition(iterator);
            iterator.consumeMatch("db");
            context.setState(new QueryState(context, false));
        } else {
            iterator.next();
        }
    }
}
