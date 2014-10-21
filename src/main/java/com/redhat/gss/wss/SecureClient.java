package com.redhat.gss.wss;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.SecurityConstants;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.ws.security.components.crypto.Merlin;

import org.jboss.logging.Logger;

public class SecureClient {
  private Logger log = Logger.getLogger(this.getClass().getName());

  public static void main(String[] args) throws Exception {
    SecureClient client = new SecureClient();
    client.invoke();
  }

  public void invoke() throws Exception {
    //Create JAX-WS client
    URL wsdl = new URL("http://localhost:8080/jbossws-sign-encrypt/SecureService?wsdl");
    QName serviceNS = new QName("http://wss.gss.redhat.com/", "SecureServiceService");
    QName portNS = new QName("http://wss.gss.redhat.com/", "SecureServicePort");
    Service service = Service.create(wsdl, serviceNS);
    WsIntfc port = service.getPort(portNS, WsIntfc.class);

    Map<String, Object> ctx = ((BindingProvider)port).getRequestContext();

    ctx.put(SecurityConstants.CALLBACK_HANDLER, new KeystorePasswordCallback());
    
    //Signature/encrypt properties file defines the keystore to use for incoming and outgoing messages
    ClassLoader tccl = Thread.currentThread().getContextClassLoader();

    Properties p = new Properties();
    p.setProperty("org.apache.ws.security.crypto.provider","org.apache.ws.security.components.crypto.Merlin");
    p.setProperty("org.apache.ws.security.crypto.merlin.keystore.type","jks");
    p.setProperty("org.apache.ws.security.crypto.merlin.keystore.password","password");
    p.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias","client");
    p.setProperty("org.apache.ws.security.crypto.merlin.keystore.file","client.keystore");

    log.warn("Setting merlin!!");
    ctx.put(SecurityConstants.SIGNATURE_CRYPTO, new Merlin(p, tccl));
    ctx.put(SecurityConstants.ENCRYPT_PROPERTIES, getClass().getResource("/security-client.properties"));
    
    //Signautre/encrypt username defines which keystore alias to use on outgoing messages
    ctx.put(SecurityConstants.SIGNATURE_USERNAME, "client");
    ctx.put(SecurityConstants.ENCRYPT_USERNAME, "server");

    //Invoke client
    log.info("Output of sayHello operation: " + port.sayHello("Kyle"));
  }

  public static void getClientProps() {
  }
}
