package org.slartibartfast;

public class UnknownEventException extends Exception {

  private final String badEvent;

  public UnknownEventException(String eventName) {
    super();
    badEvent = eventName;
  }

  @Override
  public String getMessage() {
    return "Unknown event with name of " + badEvent;
  }

}
