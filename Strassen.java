import java.util.Scanner;

public class Strassen {
	
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		// ORDEN DE LA 1 Y 2 MATRIZ
		final int FILAS1, COLUMNAS1;
		final int FILAS2, COLUMNAS2;
		
        System.out.println("***** ALGORITMO STRASSEN *****");
        
        // INGRESANDO DATOS
        
        // Matriz 1
        System.out.print("(PRIMERA MATRIZ)Ingrese el numero de filas: ");
        FILAS1 = sc.nextInt();
        System.out.print("(PRIMERA MATRIZ)Ingrese el numero de columnas: ");
        COLUMNAS1 = sc.nextInt();
        
        System.out.println();
        // Matriz 2
        System.out.print("(SEGUNDA MATRIZ)Ingrese el numero de filas: ");
        FILAS2 = sc.nextInt();
        System.out.print("(SEGUNDA MATRIZ)Ingrese el numero de columnas: ");
        COLUMNAS2 = sc.nextInt();

        int mayorA = Math.max(COLUMNAS1, FILAS1);
        int mayorB = Math.max(COLUMNAS2, FILAS2);
        int mayor = Math.max(mayorA, mayorB);
        
        int potenciaDeDos = 1;
        int k = 1;
    	// Determina el orden final de nuestra matriz (nxn)
        while(mayor > potenciaDeDos) {
        	potenciaDeDos = (int) Math.pow(2, k);
        	k++;
        }

        int[][] A = new int[potenciaDeDos][potenciaDeDos];
        int[][] B = new int[potenciaDeDos][potenciaDeDos];

        System.out.print("\nMATRIZ A");
        llenarMatriz(A,FILAS1,COLUMNAS1);
        System.out.print("\nMATRIZ B");
        llenarMatriz(B,FILAS2,COLUMNAS2);

        System.out.println("\nMATRIZ A");
        mostrarMatriz(A);
        System.out.println("\nMATRIZ B");
        mostrarMatriz(B);
        
        int[][] C = multiplicar(A,B);
        
        System.out.println("\nMATRIZ AxB");
        mostrarMatriz(C);
    }
	

    
    public static int[][] multiplicar(int[][] A, int[][] B)
    {
        // Orden de la matriz
        int n = A.length;
 
        // La matriz R almacenará la respuesta
        int[][] R = new int[n][n];
 
        // Si la matriz tiene un solo elemento (EL CASO BASE)
        if (n == 1)
            R[0][0] = A[0][0] * B[0][0];
 
        // Si no
        else {
            // Paso 1: Crear secotres que dividirá la matriz en 8 partes
            int[][] A11 = new int[n / 2][n / 2];
            int[][] A12 = new int[n / 2][n / 2];
            int[][] A21 = new int[n / 2][n / 2];
            int[][] A22 = new int[n / 2][n / 2];
            int[][] B11 = new int[n / 2][n / 2];
            int[][] B12 = new int[n / 2][n / 2];
            int[][] B21 = new int[n / 2][n / 2];
            int[][] B22 = new int[n / 2][n / 2];
 
            // Paso 2-a: Dividimos la matriz 1 en 4 sectores
            separar(A, A11, 0, 0);
            separar(A, A12, 0, n / 2);
            separar(A, A21, n / 2, 0);
            separar(A, A22, n / 2, n / 2);
 
            // Paso 2-b: Dividimos la matriz 2 en 4 sectores
            separar(B, B11, 0, 0);
            separar(B, B12, 0, n / 2);
            separar(B, B21, n / 2, 0);
            separar(B, B22, n / 2, n / 2);
 
            // Mostrando los sectores resultantes
            System.out.print("\nMATRIZ DIVIDIDA EN SECTORES\n");
            System.out.println("[A11]");
            mostrarMatriz(A11);
            System.out.println("[A12]");
            mostrarMatriz(A12);
            System.out.println("[A21]");
            mostrarMatriz(A21);
            System.out.println("[A22]");
            mostrarMatriz(A22);
            System.out.println("[B11]");
            mostrarMatriz(B11);
            System.out.println("[B12]");
            mostrarMatriz(B12);
            System.out.println("[B21]");
            mostrarMatriz(B21);
            System.out.println("[B22]");
            mostrarMatriz(B22);
 
            // M1:=(A11+A22)×(B11+B22)
            int[][] M1 = multiplicar(sumar(A11, A22), sumar(B11, B22));
           
            // M2:=(A21+A22)×(B11)
            int[][] M2 = multiplicar(sumar(A21, A22), B11);
           
            // M3:=(A11)×(B12-B22)
            int[][] M3 = multiplicar(A11, restar(B12, B22));
           
            // M4:=A2×(B21−B11)
            int[][] M4 = multiplicar(A22, restar(B21, B11));
           
            // M5:=(A11+A12)×(B22)
            int[][] M5 = multiplicar(sumar(A11, A12), B22);
           
            // M6:=(A21-A11)×(B11+B12)
            int[][] M6 = multiplicar(restar(A21, A11), sumar(B11, B12));
           
            // M7:=(A12-A22)×(B11−B22)
            int[][] M7 = multiplicar(restar(A12, A22), sumar(B21, B22));
            
            // Aplicando las fórmulas del algoritmo
            System.out.println("\nAplicando las fórmulas del algoritmo");
            System.out.println("M1:=(A11+A22)×(B11+B22)");
            System.out.println("M2:=(A21+A22)×(B11)");
            System.out.println("M3:=(A11)×(B12-B22)");
            System.out.println("M4:=A2×(B21−B11)");
            System.out.println("M5:=(A11+A12)×(B22)");
            System.out.println("M6:=(A21-A11)×(B11+B12)");
            System.out.println("M7:=(A12-A22)×(B11−B22)");
            
            System.out.println("------------------------");
            // C11:=M1+M4−M5+M7
            int[][] C11 = sumar(restar(sumar(M1, M4), M5), M7);
           
            // C12:=M3+M5
            int[][] C12 = sumar(M3, M5);
           
            // C21:=M2+M4
            int[][] C21 = sumar(M2, M4);
           
            // C22:=M1+M3−M2+M6
            int[][] C22 = sumar(restar(sumar(M1, M3), M2), M6);
            
            System.out.println("C11:=M1+M4−M5+M7");
            System.out.println("C12:=M3+M5");
            System.out.println("C21:=M2+M4");
            System.out.println("C22:=M1+M3−M2+M6");
 
            // Paso 3: Juntar las partes
            juntar(C11, R, 0, 0);
            juntar(C12, R, 0, n / 2);
            juntar(C21, R, n / 2, 0);
            juntar(C22, R, n / 2, n / 2);
        }
        return R;
    }
 
    
    // MÉTODOS AUXILIARES
    
    // Método 1: Función que imprime la matriz en consola
    public static void mostrarMatriz(int[][] a) {
    	// Mostramos la matriz
        int n = a.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(a[i][j] + "\t");//Un tab de espacios
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Método 2: Función que llena la matriz con datos del usuario
    public static void llenarMatriz(int[][] a, int filas, int columnas) {
        //Se introducen por teclado los valores de la matriz
        System.out.println("\nLectura de elementos de la matriz: ");
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                    System.out.print("Matriz[" + i + "][" + j + "]= ");
                    a[i][j] = sc.nextInt();	
            }
        }

    }
    
    // Método 3: Función que resta dos matrices
    public static int[][] restar(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n]; // Matriz resultado
        
        // Resta los elementos de las matrices
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] - B[i][j];
        return C;
    }
 
    // Método 4: Función que suma dos matrices
    public static int[][] sumar(int[][] A, int[][] B) {
    	int n = A.length;
        int[][] C = new int[n][n]; // Matriz resultado
 
        // Suma los elementos de las matrices
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                C[i][j] = A[i][j] + B[i][j];
        return C;
    }
 
    // Método 5: Función que crea una matriz y la llena de un secor de otras
    public static void separar(int[][] matrizGrande, int[][] matrizSector, int filas, int columnas) {
        // Para las filas
        for (int i1 = 0, i2 = filas; i1 < matrizSector.length; i1++, i2++)
        	// Para las columnas
            for (int j1 = 0, j2 = columnas; j1 < matrizSector.length; j1++, j2++)
            	matrizSector[i1][j1] = matrizGrande[i2][j2];
    }
 
    // Método 6: Función que junta un sector de la matriz a la matriz grande
    public static void juntar(int[][] matrizSector, int[][] matrizResultante, int filas, int columnas) {
        // Para las filas
        for (int i1 = 0, i2 = filas; i1 < matrizSector.length; i1++, i2++)
        	// Para las columnas
            for (int j1 = 0, j2 = columnas; j1 < matrizSector.length;j1++, j2++)
            	matrizResultante[i2][j2] = matrizSector[i1][j1];
    }
    
}


