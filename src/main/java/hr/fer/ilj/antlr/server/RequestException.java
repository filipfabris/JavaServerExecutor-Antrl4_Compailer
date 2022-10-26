package hr.fer.ilj.antlr.server;

public class RequestException extends RuntimeException {

  private static final long serialVersionUID = 1L;

public RequestException(String error) {
    super(error);
  }

}
