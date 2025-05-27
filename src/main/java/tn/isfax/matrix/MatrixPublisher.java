package tn.isfax.matrix;

import jakarta.xml.ws.Endpoint;

/**
 * Point de publication du service SOAP
 */
public class MatrixPublisher {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/matrix", new MatrixServiceImpl());
        System.out.println("Le service SOAP est disponible sur : http://localhost:8080/matrix?wsdl");
    }
}