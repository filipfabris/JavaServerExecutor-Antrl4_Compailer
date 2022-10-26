package hr.tel.fer.lab1.logging;

public class LogEntry {
	private String ip;
	private String dateTime;
	private String method;
	private String version;
	private String status;
	private String wholeEntry;

	public LogEntry(String ip, String dateTime, String method, String version, String status, String wholeEntry) {
		super();
		this.ip = ip;
		this.dateTime = dateTime;
		this.method = method;
		this.version = version;
		this.status = status;
		this.wholeEntry = wholeEntry;
	}

	public LogEntry() {

	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String string) {
		this.status = string;
	}

	public String getWholeEntry() {
		return wholeEntry;
	}

	public void setWholeEntry(String wholeEntry) {
		this.wholeEntry = wholeEntry;
	}
}
