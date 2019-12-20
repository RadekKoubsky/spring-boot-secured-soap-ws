# Spring Boot Secured Soap WS with various client configurations
This example shows different ways to configure Spring `WebServiceTemplate` and
 its underlying Http Client to connect to a secured web service.
 
The `WebServiceTemplate` bean uses the `HttpComponentsMessageSender` class to connect and send
SOAP messages to the web service. The following configurations of `HttpComponentsMessageSender`
are implemented:

* `HttpComponentsMessageSender` created as a Spring Bean with default Http Client 
* `HttpComponentsMessageSender` created as a Spring Bean with a custom Http Client provided
by constructor
* `HttpComponentsMessageSender` created as a standard java object with default Http Client
* `HttpComponentsMessageSender` created as a standard java object with a custom Http Client
provided by constructor

To demonstrate all of the configurations above, there is a separate test for each configuration that
executes a request to the running web service within the Spring test environment.

Run `mvn clean test` to see the different clients and how they authenticate against the secured
web service.
