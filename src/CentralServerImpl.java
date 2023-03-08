import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CentralServerImpl extends UnicastRemoteObject implements CentralServer {
    private static final String[] opServersIp = {"localhost", "localhost"};

    protected CentralServerImpl() throws RemoteException, UnknownHostException {
        System.out.println("----------------------------------------------");
        System.out.println("Servidor central de cálculo en ejecución");
        System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());
        System.out.println("Puerto: 12345");
        System.out.println("----------------------------------------------");
    }

    @Override
    public float calculateVar(float[][] data) throws RemoteException {
        float var;
        try {
            // Busca el registro del CentralServer
            Registry registry = LocateRegistry.getRegistry(opServersIp[0], 22222);
            Registry registry2 = LocateRegistry.getRegistry(opServersIp[1], 22223);

            // Buscar el objeto timeServer en el registro y si lo encuentra, crear el objeto local
            OperationServer op1 = (OperationServer) registry.lookup("operationserver1");
            OperationServer op2 = (OperationServer) registry2.lookup("operationserver2");

            // Invocacion de metodo remoto
            var = op1.calculatePart1(data) + op2.calculatePart2(data);
            System.out.println("  > Respuesta enviada: " + var);
            return var;

        } catch (NotBoundException e) {
            System.out.println("OperationServer no se encontró en el registro");
            System.exit(0);
            return 0;
        } catch (RemoteException e) {
            System.out.println("Time error: " + e);
            System.exit(0);
            return 0;
        }
    }

    public static void main(String[] args) throws IOException {
        try {
            // Crear el objeto cuyos métodos el cliente podrá usar
            CentralServerImpl centralServer = new CentralServerImpl();

            // Incluir el objeto en el registro del RMI en el puerto 12345
            Registry registry = LocateRegistry.createRegistry(12345);
            registry.rebind("centralserver", centralServer);
            System.out.println("Objeto -centralserver- Registrado en el RMI");

        } catch (RemoteException e) {
            System.out.println("Error: " + e);
        }
    }
}
