package tn.isfax.matrix;

import jakarta.jws.WebService;

/**
 * Implémentation du service web pour les opérations sur les matrices
 */
@WebService(endpointInterface = "tn.isfax.matrix.MatrixService")
public class MatrixServiceImpl implements MatrixService {

	@Override
	public double[][] addition(double[][] a, double[][] b) throws MatrixServiceException {
	    // Vérifier si les matrices sont nulles
	    if (a == null || b == null) {
	        throw new MatrixServiceException("Les matrices ne doivent pas être nulles.");
	    }
	    // Vérifier si les matrices sont vides
	    if (a.length == 0 || b.length == 0) {
	        throw new MatrixServiceException("Les matrices ne peuvent pas être vides.");
	    }
	    // Vérifier si les lignes sont vides
	    if (a[0] == null || b[0] == null || a[0].length == 0 || b[0].length == 0) {
	        throw new MatrixServiceException("Les lignes des matrices ne peuvent pas être vides.");
	    }
	    // Vérifier les dimensions
	    if (a.length != b.length || a[0].length != b[0].length) {
	        throw new MatrixServiceException("Les matrices doivent avoir les mêmes dimensions pour l'addition.");
	    }

	    // Ajouter un log pour déboguer
	    java.util.logging.Logger.getLogger(this.getClass().getName())
	        .info("Addition: a=" + java.util.Arrays.deepToString(a) + ", b=" + java.util.Arrays.deepToString(b));

	    double[][] resultat = new double[a.length][a[0].length];
	    for (int i = 0; i < a.length; i++) {
	        for (int j = 0; j < a[0].length; j++) {
	            resultat[i][j] = a[i][j] + b[i][j];
	        }
	    }
	    return resultat;
	}

    @Override
    public double[][] multiplication(double[][] a, double[][] b) throws MatrixServiceException {
        if (a == null || b == null)
            throw new MatrixServiceException("Les matrices ne doivent pas être nulles.");
        if (a[0].length != b.length)
            throw new MatrixServiceException("Le nombre de colonnes de A doit être égal au nombre de lignes de B.");

        double[][] resultat = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < b[0].length; j++)
                for (int k = 0; k < b.length; k++)
                    resultat[i][j] += a[i][k] * b[k][j];

        return resultat;
    }

    @Override
    public double[][] transposition(double[][] matrice) {
        double[][] transposee = new double[matrice[0].length][matrice.length];
        for (int i = 0; i < matrice.length; i++)
            for (int j = 0; j < matrice[0].length; j++)
            	transposee[j][i] = matrice[i][j];
        return transposee;
    }

    @Override
    public double[][] multiplicationScalaire(double[][] matrice, double scalaire) {
        double[][] resultat = new double[matrice.length][matrice[0].length];
        for (int i = 0; i < matrice.length; i++)
            for (int j = 0; j < matrice[0].length; j++)
                resultat[i][j] = matrice[i][j] * scalaire;
        return resultat;
    }

    @Override
    public double determinant(double[][] matrice) throws MatrixServiceException {
        if (matrice == null)
            throw new MatrixServiceException("La matrice ne doit pas être nulle.");
        if (matrice.length != matrice[0].length)
            throw new MatrixServiceException("La matrice doit être carrée.");

        return calculeDeterminant(matrice);
    }

    private double calculeDeterminant(double[][] matrice) {
        int n = matrice.length;
        if (n == 1) return matrice[0][0];
        if (n == 2) return matrice[0][0]*matrice[1][1] - matrice[0][1]*matrice[1][0];

        double det = 0;
        for (int p = 0; p < n; p++) {
            double[][] sub = new double[n - 1][n - 1];
            for (int i = 1; i < n; i++)
                for (int j = 0, col = 0; j < n; j++) {
                    if (j == p) continue;
                    sub[i - 1][col++] = matrice[i][j];
                }
            det += matrice[0][p] * calculeDeterminant(sub) * (p % 2 == 0 ? 1 : -1);
        }
        return det;
    }

    @Override
    public double[][] inverse(double[][] matrice) throws MatrixServiceException {
        if (matrice == null)
            throw new MatrixServiceException("La matrice ne doit pas être nulle.");
        if (matrice.length != matrice[0].length)
            throw new MatrixServiceException("La matrice doit être carrée.");

        double det = determinant(matrice);
        if (det == 0)
            throw new MatrixServiceException("Le déterminant est nul, la matrice n'est pas inversible.");

        int n = matrice.length;
        double[][] adjointe = new double[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                double[][] sub = new double[n - 1][n - 1];
                for (int r = 0, subR = 0; r < n; r++) {
                    if (r == i) continue;
                    for (int c = 0, subC = 0; c < n; c++) {
                        if (c == j) continue;
                        sub[subR][subC++] = matrice[r][c];
                    }
                    subR++;
                }
                adjointe[j][i] = calculeDeterminant(sub) * ((i + j) % 2 == 0 ? 1 : -1);
            }

        return multiplicationScalaire(adjointe, 1.0 / det);
    }

    @Override
    public double trace(double[][] matrice) throws MatrixServiceException {
        if (matrice == null)
            throw new MatrixServiceException("La matrice ne doit pas être nulle.");
        if (matrice.length != matrice[0].length)
            throw new MatrixServiceException("La matrice doit être carrée.");

        double somme = 0;
        for (int i = 0; i < matrice.length; i++)
            somme += matrice[i][i];
        return somme;
    }

    @Override
    public double[][] carre(double[][] matrice) throws MatrixServiceException {
        if (matrice == null)
            throw new MatrixServiceException("La matrice ne doit pas être nulle.");
        if (matrice.length != matrice[0].length)
            throw new MatrixServiceException("La matrice doit être carrée.");

        return multiplication(matrice, matrice);
    }
}