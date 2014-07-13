package com.redhat.gss.wss;

import org.apache.cxf.interceptor.InInterceptors;
import org.jboss.ws.api.annotation.EndpointConfig;
import org.jboss.logging.Logger;
import org.apache.cxf.annotations.Logging;

@javax.jws.WebService(wsdlLocation="WEB-INF/wsdl/secureService.wsdl")
@EndpointConfig(configFile = "WEB-INF/jaxws-endpoint-config.xml", configName = "Custom WS-Security Endpoint")
@Logging(pretty=true)
public class SecureService {
  public String sayHello(String name) {
    return "Hello, " + name;
  }

  public String sayGoodbye(String name) {
    return "Goodbye, " + name;
  }
}
