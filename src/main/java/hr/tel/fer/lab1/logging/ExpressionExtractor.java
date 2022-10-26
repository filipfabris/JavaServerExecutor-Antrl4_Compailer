package hr.tel.fer.lab1.logging;

import java.util.LinkedList;
import java.util.List;

import org.antlr.v4.runtime.tree.ErrorNode;

import hr.fer.ilj.antlr.RequestBaseListener;
import hr.fer.ilj.antlr.RequestParser;
import hr.fer.ilj.antlr.RequestParser.ExprContext;


public class ExpressionExtractor extends RequestBaseListener {
  private List<Expression> expressions = new LinkedList<>();
  private String error;
  private Integer count;

  public List<Expression> getExpressions() {
    return expressions;
  }
  
  public Integer getCount() {
	    return count;
	  }

  @Override
  public void exitExpr(ExprContext ctx) {
    Expression expr = new Expression();
    expr.setKey(ctx.KEY().getText());
    String value = ctx.value().getText();
    expr.setValue(value.substring(1, value.length() - 1));
    expr.setOperator(Operator.parse(ctx.OP().getText()));
    expressions.add(expr);
  }
  
  @Override
  public void exitExp(RequestParser.ExpContext ctx) {
	  String value = ctx.count().getText();
	  try {
		  this.count = Integer.parseInt(value.substring(1, value.length() - 1));
	  }catch(NumberFormatException e) {
		  throw new RuntimeException("RETURN VALUE MUST BE INTEGER");
	  }
  }

  @Override
  public void visitErrorNode(ErrorNode node) {
    error = "Request not in format!";
  }

  public String getError() {
    return error;
  }
}