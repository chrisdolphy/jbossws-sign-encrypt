package com.redhat.gss.wss;

import org.jboss.logging.Logger;

import org.apache.cxf.message.Message;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.model.EndpointInfo;


public class PropertyReaderInterceptor extends AbstractPhaseInterceptor<Message> {
  private static Logger log = Logger.getLogger(PropertyReaderInterceptor.class);

  public PropertyReaderInterceptor() {
    super("receive");
  }

  public void handleMessage(Message m) throws Fault {
    final String key = "ws-security.signature.properties";
    String value = (String)m.getContextualProperty(key);
    log.info("key: " + key);
    log.info("value from message: " + value);
    value = (String)m.getExchange().get(key);
    log.info("value from exchange: " + value);
    value = (String)m.getExchange().getBus().getProperty(key);
    log.info("value from bus: " + value);
    value = (String)m.getExchange().getService().get(key);
    log.info("value from service: " + value);
    Endpoint endpoint = m.getExchange().getEndpoint();
    log.info("*** Endoint class: " + endpoint.getClass().getName());
    if(endpoint != null) {
      EndpointInfo ei = endpoint.getEndpointInfo();
      if(ei != null) {
        value = (String)ei.getProperty(key);
        log.info("value from endpointInfo: " + value);
      }
    }
  }
}
