package org.slartibartfast;

import com.jme3.input.KeyInput;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of JME's KeyInput values.
 */
public enum Keys {
  KEY_ESCAPE (KeyInput.KEY_ESCAPE, "ESCAPE"),
  KEY_1 (KeyInput.KEY_1, "1"),
  KEY_2 (KeyInput.KEY_2, "2"),
  KEY_3 (KeyInput.KEY_3, "3"),
  KEY_4 (KeyInput.KEY_4, "4"),
  KEY_5 (KeyInput.KEY_5, "5"),
  KEY_6 (KeyInput.KEY_6, "6"),
  KEY_7 (KeyInput.KEY_7, "7"),
  KEY_8 (KeyInput.KEY_8, "8"),
  KEY_9 (KeyInput.KEY_9, "9"),
  KEY_0 (KeyInput.KEY_0, "0"),
  KEY_MINUS (KeyInput.KEY_MINUS, "MINUS"),
  KEY_EQUALS (KeyInput.KEY_EQUALS, "EQUALS"),
  KEY_BACK (KeyInput.KEY_BACK, "BACK"),
  KEY_TAB (KeyInput.KEY_TAB, "TAB"),
  KEY_Q (KeyInput.KEY_Q, "Q"),
  KEY_W (KeyInput.KEY_W, "W"),
  KEY_E (KeyInput.KEY_E, "E"),
  KEY_R (KeyInput.KEY_R, "R"),
  KEY_T (KeyInput.KEY_T, "T"),
  KEY_Y (KeyInput.KEY_Y, "Y"),
  KEY_U (KeyInput.KEY_U, "U"),
  KEY_I (KeyInput.KEY_I, "I"),
  KEY_O (KeyInput.KEY_O, "O"),
  KEY_P (KeyInput.KEY_P, "P"),
  KEY_LBRACKET (KeyInput.KEY_LBRACKET, "LBRACKET"),
  KEY_RBRACKET (KeyInput.KEY_RBRACKET, "RBRACKET"),
  KEY_RETURN (KeyInput.KEY_RETURN, "RETURN"),
  KEY_LCONTROL (KeyInput.KEY_LCONTROL, "LCONTROL"),
  KEY_A (KeyInput.KEY_A, "A"),
  KEY_S (KeyInput.KEY_S, "S"),
  KEY_D (KeyInput.KEY_D, "D"),
  KEY_F (KeyInput.KEY_F, "F"),
  KEY_G (KeyInput.KEY_G, "G"),
  KEY_H (KeyInput.KEY_H, "H"),
  KEY_J (KeyInput.KEY_J, "J"),
  KEY_K (KeyInput.KEY_K, "K"),
  KEY_L (KeyInput.KEY_L, "L"),
  KEY_SEMICOLON (KeyInput.KEY_SEMICOLON, "SEMICOLON"),
  KEY_APOSTROPHE (KeyInput.KEY_APOSTROPHE, "APOSTROPHE"),
  KEY_GRAVE (KeyInput.KEY_GRAVE, "GRAVE"),
  KEY_LSHIFT (KeyInput.KEY_LSHIFT, "LSHIFT"),
  KEY_BACKSLASH (KeyInput.KEY_BACKSLASH, "BACKSLASH"),
  KEY_Z (KeyInput.KEY_Z, "Z"),
  KEY_X (KeyInput.KEY_X, "X"),
  KEY_C (KeyInput.KEY_C, "C"),
  KEY_V (KeyInput.KEY_V, "V"),
  KEY_B (KeyInput.KEY_B, "B"),
  KEY_N (KeyInput.KEY_N, "N"),
  KEY_M (KeyInput.KEY_M, "M"),
  KEY_COMMA (KeyInput.KEY_COMMA, "COMMA"),
  KEY_PERIOD (KeyInput.KEY_PERIOD, "PERIOD"),
  KEY_SLASH (KeyInput.KEY_SLASH, "SLASH"),
  KEY_RSHIFT (KeyInput.KEY_RSHIFT, "RSHIFT"),
  KEY_MULTIPLY (KeyInput.KEY_MULTIPLY, "MULTIPLY"),
  KEY_LMENU (KeyInput.KEY_LMENU, "LMENU"),
  KEY_SPACE (KeyInput.KEY_SPACE, "SPACE"),
  KEY_CAPITAL (KeyInput.KEY_CAPITAL, "CAPITAL"),
  KEY_F1 (KeyInput.KEY_F1, "F1"),
  KEY_F2 (KeyInput.KEY_F2, "F2"),
  KEY_F3 (KeyInput.KEY_F3, "F3"),
  KEY_F4 (KeyInput.KEY_F4, "F4"),
  KEY_F5 (KeyInput.KEY_F5, "F5"),
  KEY_F6 (KeyInput.KEY_F6, "F6"),
  KEY_F7 (KeyInput.KEY_F7, "F7"),
  KEY_F8 (KeyInput.KEY_F8, "F8"),
  KEY_F9 (KeyInput.KEY_F9, "F9"),
  KEY_F10 (KeyInput.KEY_F10, "F10"),
  KEY_NUMLOCK (KeyInput.KEY_NUMLOCK, "NUMLOCK"),
  KEY_SCROLL (KeyInput.KEY_SCROLL, "SCROLL"),
  KEY_NUMPAD7 (KeyInput.KEY_NUMPAD7, "NUMPAD7"),
  KEY_NUMPAD8 (KeyInput.KEY_NUMPAD8, "NUMPAD8"),
  KEY_NUMPAD9 (KeyInput.KEY_NUMPAD9, "NUMPAD9"),
  KEY_SUBTRACT (KeyInput.KEY_SUBTRACT, "SUBTRACT"),
  KEY_NUMPAD4 (KeyInput.KEY_NUMPAD4, "NUMPAD4"),
  KEY_NUMPAD5 (KeyInput.KEY_NUMPAD5, "NUMPAD5"),
  KEY_NUMPAD6 (KeyInput.KEY_NUMPAD6, "NUMPAD6"),
  KEY_ADD (KeyInput.KEY_ADD, "ADD"),
  KEY_NUMPAD1 (KeyInput.KEY_NUMPAD1, "NUMPAD1"),
  KEY_NUMPAD2 (KeyInput.KEY_NUMPAD2, "NUMPAD2"),
  KEY_NUMPAD3 (KeyInput.KEY_NUMPAD3, "NUMPAD3"),
  KEY_NUMPAD0 (KeyInput.KEY_NUMPAD0, "NUMPAD0"),
  KEY_DECIMAL (KeyInput.KEY_DECIMAL, "DECIMAL"),
  KEY_F11 (KeyInput.KEY_F11, "F11"),
  KEY_F12 (KeyInput.KEY_F12, "F12"),
  KEY_F13 (KeyInput.KEY_F13, "F13"),
  KEY_F14 (KeyInput.KEY_F14, "F14"),
  KEY_F15 (KeyInput.KEY_F15, "F15"),
  KEY_KANA (KeyInput.KEY_KANA, "KANA"),
  KEY_CONVERT (KeyInput.KEY_CONVERT, "CONVERT"),
  KEY_NOCONVERT (KeyInput.KEY_NOCONVERT, "NOCONVERT"),
  KEY_YEN (KeyInput.KEY_YEN, "YEN"),
  KEY_NUMPADEQUALS (KeyInput.KEY_NUMPADEQUALS, "NUMPADEQUALS"),
  KEY_CIRCUMFLEX (KeyInput.KEY_CIRCUMFLEX, "CIRCUMFLEX"),
  KEY_AT (KeyInput.KEY_AT, "AT"),
  KEY_COLON (KeyInput.KEY_COLON, "COLON"),
  KEY_UNDERLINE (KeyInput.KEY_UNDERLINE, "UNDERLINE"),
  KEY_KANJI (KeyInput.KEY_KANJI, "KANJI"),
  KEY_STOP (KeyInput.KEY_STOP, "STOP"),
  KEY_AX (KeyInput.KEY_AX, "AX"),
  KEY_UNLABELED (KeyInput.KEY_UNLABELED, "UNLABELED"),
  KEY_NUMPADENTER (KeyInput.KEY_NUMPADENTER, "NUMPADENTER"),
  KEY_RCONTROL (KeyInput.KEY_RCONTROL, "RCONTROL"),
  KEY_NUMPADCOMMA (KeyInput.KEY_NUMPADCOMMA, "NUMPADCOMMA"),
  KEY_DIVIDE (KeyInput.KEY_DIVIDE, "DIVIDE"),
  KEY_SYSRQ (KeyInput.KEY_SYSRQ, "SYSRQ"),
  KEY_RMENU (KeyInput.KEY_RMENU, "RMENU"),
  KEY_PAUSE (KeyInput.KEY_PAUSE, "PAUSE"),
  KEY_HOME (KeyInput.KEY_HOME, "HOME"),
  KEY_UP (KeyInput.KEY_UP, "UP"),
  KEY_PRIOR (KeyInput.KEY_PRIOR, "PRIOR"),
  KEY_PGUP (KeyInput.KEY_PGUP, "PGUP"),
  KEY_LEFT (KeyInput.KEY_LEFT, "LEFT"),
  KEY_RIGHT (KeyInput.KEY_RIGHT, "RIGHT"),
  KEY_END (KeyInput.KEY_END, "END"),
  KEY_DOWN (KeyInput.KEY_DOWN, "DOWN"),
  KEY_NEXT (KeyInput.KEY_NEXT, "NEXT"),
  KEY_PGDN (KeyInput.KEY_PGDN, "PGDN"),
  KEY_INSERT (KeyInput.KEY_INSERT, "INSERT"),
  KEY_DELETE (KeyInput.KEY_DELETE, "DELETE"),
  KEY_LMETA (KeyInput.KEY_LMETA, "LMETA"),
  KEY_RMETA (KeyInput.KEY_RMETA, "RMETA"),
  KEY_APPS (KeyInput.KEY_APPS, "APPS"),
  KEY_POWER (KeyInput.KEY_POWER, "POWER"),
  KEY_SLEEP (KeyInput.KEY_SLEEP, "SLEEP");

  public int code;
  public String keyName;

  Keys(int code, String keyName) {
    this.code = code;
    this.keyName = keyName;
  }

  private static final Map<String, Integer> lookup =
          new HashMap<String, Integer>();

  static {
    for(Keys k : EnumSet.allOf(Keys.class)) {
      lookup.put(k.keyName, k.code);
    }
  }

  /**
   * Given a name of the Key, probably from the user database,
   * find out the JME KeyInput value for that key.
   * @param keyName Name of the key, as defined above
   * @return Value saved by the appropriate KeyInput.KEY_VALUE
   */
  public static int get(String keyName) {
    return lookup.get(keyName);
  }
}
