#JBossWS WS-Security Sign and Encrypt Example

An example of the recommended way to configure JBossWS/CXF such that exchanged
SOAP messages are signed and encrypted.

Build and deploy: `mvn clean install jboss-as:deploy`
Test: `mvn exec:exec`
SoapUI: load up `signature-soapui-project.xml` and update the keystore path to use `src/main/resources/client.keystore`.

Requires use of JBoss EAP 6 BOM (only available in 6.2+).
