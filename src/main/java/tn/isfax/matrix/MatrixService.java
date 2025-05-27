package tn.isfax.matrix;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface MatrixService {

	double[][] addition(@WebParam(name = "a") double[][] a, @WebParam(name = "b") double[][] b) throws MatrixServiceException;
    @WebMethod double[][] multiplication(double[][] a, double[][] b) throws MatrixServiceException;

    @WebMethod double[][] transposition(double[][] matrice);

    @WebMethod double[][] multiplicationScalaire(double[][] matrice, double scalaire);

    @WebMethod double determinant(double[][] matrice) throws MatrixServiceException;

    @WebMethod double[][] inverse(double[][] matrice) throws MatrixServiceException;

    @WebMethod double trace(double[][] matrice) throws MatrixServiceException;

    @WebMethod double[][] carre(double[][] matrice) throws MatrixServiceException;
}  


