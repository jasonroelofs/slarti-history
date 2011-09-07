package org.slartibartfast.events;

/**
 * Error thrown when reading from user_settings. If an event name
 * is found that isn't defined in the code, this error is thrown.
 *
 * Should this be an Exception, caught and logged out? For now it
 * will shut the client down with a clear error message.
 */
public class UnknownEventError extends Error {

  private final String badEvent;

  public UnknownEventError(String eventName) {
    super();
    badEvent = eventName;
  }

  @Override
  public String getMessage() {
    return "Unknown event with name of " + badEvent;
  }

}
