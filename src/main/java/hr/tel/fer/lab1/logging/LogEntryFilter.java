package hr.tel.fer.lab1.logging;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class LogEntryFilter {
  private List<Expression> expressions;

  public LogEntryFilter() {
    expressions = new LinkedList<>();
  }

  public void add(Expression expression) {
    expressions.add(expression);
  }

  public List<LogEntry> filter(List<LogEntry> logs) {
    return logs.stream()
      .filter(log -> {
        return expressions.stream()
          .mapToInt(expr -> expr.checkCondition(log) ? 1 : 0)
          .sum() == expressions.size();
      })
      .collect(Collectors.toList());
  }
}
