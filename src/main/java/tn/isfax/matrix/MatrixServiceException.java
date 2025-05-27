package tn.isfax.matrix;

import jakarta.xml.ws.WebFault;

import java.io.Serial;

/**
 * Exception personnalisée pour erreurs de calcul matriciel
 */
@WebFault(name = "MatrixServiceFault")
public class MatrixServiceException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public MatrixServiceException(String message) {
        super(message);
    }

    public String getFaultInfo() {
        return this.getMessage();
    }
}