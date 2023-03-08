import java.util.Scanner;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private static final String centralServerIp = "localhost";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        float var;

        // Pedir al usuario que introduzca los datos para realizar la operacion
        System.out.print("Introduce el tamaño del rango del problema: ");
        int size = scanner.nextInt();
        float[][] data = new float[size][2];
        for (int i = 0; i < size; i++) {
            System.out.print("Variable aleatoria " + (i + 1) + ": ");
            data[i][0] = scanner.nextFloat();
            System.out.print("f(x): ");
            data[i][1] = scanner.nextFloat();
        }

        try {
            // Busca el registro del CentralServer
            Registry registry = LocateRegistry.getRegistry(centralServerIp, 12345);

            // Buscar el objeto timeServer en el registro y si lo encuentra, crear el objeto local
            CentralServer CS = (CentralServer) registry.lookup("centralserver");

            // Invocar el metodo remoto
            var = CS.calculateVar(data);

            // Mostrar la respuesta al usuario
            System.out.println("La varianza es: " + var);

        } catch (NotBoundException e) {
            System.out.println("CentralServer no se encontró en el registro");
            System.exit(0);
        } catch (RemoteException e) {
            System.out.println("Time error: " + e);
            System.exit(0);
        }
    }
}
