package com.redhat.gss.wss;
 
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.ws.security.WSPasswordCallback;
import org.jboss.logging.Logger;
 
public class KeystorePasswordCallback implements CallbackHandler {
  private static Logger log = Logger.getLogger(KeystorePasswordCallback.class);
  private Map<String, String> passwords = new HashMap<String, String>();
  private static Map<Integer, String> usageMap = new HashMap<Integer, String>();

  static {
    usageMap.put(0, "UNKNOWN");
    usageMap.put(1, "DECRYPT");
    usageMap.put(2, "USERNAME_TOKEN");
    usageMap.put(3, "SIGNATURE");
    usageMap.put(4, "KEY_NAME");
    usageMap.put(5, "USERNAME_TOKEN_UNKNOWN");
    usageMap.put(6, "SECURITY_CONTEXT_TOKEN");
    usageMap.put(7, "CUSTOM_TOKEN");
    usageMap.put(8, "ENCRYPTED_KEY_TOKEN");
    usageMap.put(9, "SECRET_KEY");
  }

  public KeystorePasswordCallback() {
    passwords.put("server", "password");
    passwords.put("client", "password");
  }

  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (int i = 0; i < callbacks.length; i++) {
      WSPasswordCallback pc = (WSPasswordCallback)callbacks[i];

      String pass = passwords.get(pc.getIdentifier());
      log.info("Usage type: " + usageMap.get(pc.getUsage()));
      if (pass != null) {
        pc.setPassword(pass);
        return;
      }
    }
  }

  public void setAliasPassword(String alias, String password) {
    passwords.put(alias, password);
  }
}
