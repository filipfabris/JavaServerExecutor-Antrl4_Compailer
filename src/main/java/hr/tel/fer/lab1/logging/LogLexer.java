package hr.tel.fer.lab1.logging;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogLexer {
	// ^(\d{1,3}.\d{1,3}.\d{1,3}.\d{1,3})\s*\[(\d{2}\/\w+\/\d{4}:\d{2}:\d{2}:\d{2})]\s*(\w+)[\s]?\*?\s*.+HTTP\/(\d.\d)\s*(\d{3})\s".+"$
	private static final String regex = "^(\\d{1,3}.\\d{1,3}.\\d{1,3}.\\d{1,3})\\s*\\[(\\d{2}\\/\\w+\\/\\d{4}:\\d{2}:\\d{2}:\\d{2})]\\s*(\\w+)[\\s]?\\*?\\s*.+HTTP\\/(\\d.\\d)\\s*(\\d{3})\\s\".+\"$";
	private static final Pattern pattern = Pattern.compile(regex);

	public Optional<LogEntry> parse(String line) {
		Matcher matcher = pattern.matcher(line);

		if (matcher.find()) {
			if (matcher.groupCount() == 5) {
				LogEntry entry = new LogEntry();
				entry.setIp(matcher.group(1));
				entry.setDateTime(matcher.group(2));
				entry.setMethod(matcher.group(3));
				entry.setVersion(matcher.group(4));
				entry.setStatus(matcher.group(5));
				
				entry.setWholeEntry(matcher.group(0));
				return Optional.of(entry);
			}
		}
		return Optional.empty();
	}
	
}
