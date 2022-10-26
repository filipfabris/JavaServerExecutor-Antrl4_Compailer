package hr.fer.ilj.antlr.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import hr.fer.ilj.antlr.RequestLexer;
import hr.fer.ilj.antlr.RequestParser;
import hr.tel.fer.lab1.logging.Expression;
import hr.tel.fer.lab1.logging.ExpressionExtractor;
import hr.tel.fer.lab1.logging.LogEntry;
import hr.tel.fer.lab1.logging.LogEntryFilter;


public class ServerExecutor implements Runnable {
	Socket socket;
	List<LogEntry> logs;
	Integer count;

	public ServerExecutor(Socket socket, List<LogEntry> logs) {
		this.socket = socket;
		this.logs = logs;
		this.count = 0;
	}

	@Override
	public void run() {
		try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

			String request;
			out.println("Start");
			
			try {
				while ((request = in.readLine()) != null) {
					if(request.equalsIgnoreCase("EXIT")) {
						out.println("End");
						break;
					}
					List<LogEntry> logs = processInput(request);
					processOutput(logs, out);
				}
			} catch (RequestException e) {
				out.println(e.getMessage());
			}
		} catch (IOException ioe) {
			// ako pukne veza nemamo što napraviti
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				// ako ne možemo zatvoriti socket ne možemo ništa napraviti
			}
		}
	}

	private void processOutput(List<LogEntry> logs, PrintWriter out) {
		logs.forEach(log -> {
			out.print(log.getWholeEntry());
			out.println();
		});
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

		this.count = listener.getCount();
		List<Expression> expressions = listener.getExpressions();
		return expressions;
	}
}
