package cz.cvut.fel.mongodb_assignment_evaluator.service.evaluation.checker;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FeedbackCollector {
    private final List<String> feedbackList;

    public FeedbackCollector() {
        this.feedbackList = new ArrayList<>();
    }

    public void addFeedback(String feedback) {
        this.feedbackList.add(
                "=".repeat(100) +
                        '\n' + feedback
        );
    }
}
