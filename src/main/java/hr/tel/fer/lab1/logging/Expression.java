package hr.tel.fer.lab1.logging;

public class Expression {
  private String key;
  private Operator operator;
  private String value;

  public Expression() {
  }

  public Expression(String key, Operator operator, String value) {
    this.key = key;
    this.operator = operator;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean checkCondition(LogEntry entry) {
    int comparison = compare(entry);

    switch (operator) {
    case EQ:
      return comparison == 0;
    case NEQ:
      return comparison != 0;
    }

    return false;
  }

  private int compare(LogEntry entry) {
	  //u Value je usporedba
	  String entryValue = getStringValueFromEntry(entry);
	  int comparison;
	  
	  if(this.key.equalsIgnoreCase("IP")) {
		  comparison = compareIp(entryValue);
	  }else {		  
		  comparison = entryValue.compareTo(value);
	  }
	  
    return comparison;
  }
  
  private int compareIp(String otherIp) {
	  String thisIp = value;
	  
	  for(int i = 0; i<thisIp.length(); i++) {
		  if(thisIp.charAt(i) == 'X' || thisIp.charAt(i) == 'x') {
			  break;
		  }else if(thisIp.charAt(i) != otherIp.charAt(i)) {
			  return -1;
		  }
	  }
	  return 0;
  }

  private String getStringValueFromEntry(LogEntry entry) {
    switch (key.toUpperCase()) {
    case "IP":
      return entry.getIp();
    case "DATETIME":
      return entry.getDateTime();
    case "METHOD":
      return entry.getMethod();
    case "VERSION":
      return entry.getVersion();
    case "STATUS":
      return entry.getStatus();
    }
    return null;
  }
}