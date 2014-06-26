#JBossWS WS-Security Sign and Encrypt Example

An example of the recommended way to configure JBossWS/CXF such that exchanged
SOAP messages are signed and encrypted.

Build and deploy: `mvn clean install jboss-as:deploy`
Test: `mvn exec:exec`

Requires use of JBoss EAP 6 BOM (only available in 6.2+).
