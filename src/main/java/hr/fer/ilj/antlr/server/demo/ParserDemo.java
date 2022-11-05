package hr.fer.ilj.antlr.server.demo;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import hr.fer.ilj.antlr.RequestLexer;
import hr.fer.ilj.antlr.RequestParser;
import hr.fer.ilj.antlr.server.RequestException;
import hr.tel.fer.lab1.logging.Expression;
import hr.tel.fer.lab1.logging.ExpressionExtractor;
import hr.tel.fer.lab1.logging.LogEntry;
import hr.tel.fer.lab1.logging.LogEntryFilter;
import hr.tel.fer.lab1.logging.LogLoader;

public class ParserDemo {
	String request;
	List<LogEntry> logs;
	Integer count;
	
	ParserDemo(String request, List<LogEntry> logs) throws IOException{
		this.request = request;
		this.logs = logs;
		this.count = 0;
		this.parse();
	}
	
	private void parse() throws IOException {
		List<LogEntry> logs = processInput(request);
		processOutput(logs, System.out);
		
	}
	
	private List<LogEntry> processInput(String request) throws IOException {
		List<Expression> expressions = parseExpressions(request);
		List<LogEntry> filteredLogs = filterLogs(expressions);
		
		List<LogEntry> output = filteredLogs.stream().limit(this.count).collect(Collectors.toList());
		return output;
	}
	
	private List<LogEntry> filterLogs(List<Expression> expressions) {
		LogEntryFilter filter = new LogEntryFilter();
		expressions.forEach(e -> filter.add(e));

		List<LogEntry> filteredLogs = filter.filter(logs);
		return filteredLogs;
	}

	private List<Expression> parseExpressions(String request) throws IOException {
		CharStream input = CharStreams.fromString(request);
		// ANTLRInputStream input = new ANTLRInputStream(request);

		RequestLexer lexer = new RequestLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);

		RequestParser parser = new RequestParser(tokens);
		ParseTree tree = parser.request();

		ParseTreeWalker walker = new ParseTreeWalker();
		ExpressionExtractor listener = new ExpressionExtractor();
		walker.walk(listener, tree);

		if (listener.getError() != null)
			throw new RequestException(listener.getError());

		List<Expression> expressions = listener.getExpressions();
		this.count = listener.getCount();
		return expressions;
	}
	
	private void processOutput(List<LogEntry> logs, PrintStream out) {
		logs.forEach(log -> {
			out.print(log.getWholeEntry());
			out.println();
		});
	}

			
	public static void main(String[] args) throws IOException {
		LogLoader loader = new LogLoader();
		List<LogEntry> logs = loader.load(new FileReader("logs.txt"));
		
		// FILTER METHOD!="GET" STATUS=="200" RETURN"5"
		
		Scanner sc = new Scanner(System.in);
		String input = sc.nextLine();
		
		
		@SuppressWarnings("unused")
		ParserDemo pd = new ParserDemo(input, logs);
		
		
		sc.close();
	}
	

}
