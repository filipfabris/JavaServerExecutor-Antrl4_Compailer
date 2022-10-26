package hr.tel.fer.lab1.logging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class LogLoader {

  public List<LogEntry> load(Reader reader) {
    int row = 0;
    BufferedReader bufferedReader = new BufferedReader(reader);
    List<LogEntry> logs = new LinkedList<>();

    try {
      String line = bufferedReader.readLine();
      row++;

      LogLexer parser = new LogLexer();
      LogEntry entry = null;
      StringBuilder sb = new StringBuilder();
      while (line != null) {
        Optional<LogEntry> optional = parser.parse(line);

        if (optional.isPresent()) {
          if (sb.length() != 0) {
            entry.setWholeEntry(sb.toString());;
            sb.setLength(0);
          }
          entry = optional.get();
          logs.add(entry);
        } else {
          if (sb.length() == 0) {
            sb.append(entry.getWholeEntry());
          }
          sb.append("\n");
          sb.append(line);
        }
        line = bufferedReader.readLine();
        row++;
      }
    } catch (IOException e) {
      throw new RuntimeException("Can not read log entry in row " + row + ".", e);
    }

    return logs;
  }

}
