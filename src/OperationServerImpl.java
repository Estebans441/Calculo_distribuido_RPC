import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.lang.Math.pow;

public class OperationServerImpl extends UnicastRemoteObject implements OperationServer {
    protected OperationServerImpl(int serverNum) throws RemoteException, UnknownHostException {
        System.out.println("----------------------------------------------");
        System.out.println("Servidor de operación " + (serverNum + 1) + " en ejecución");
        System.out.println("IP: " + InetAddress.getLocalHost().getHostAddress());
        System.out.println("Puerto: " + (22222 + serverNum));
        System.out.println("----------------------------------------------");
    }

    public static void main(String[] args) throws IOException {
        // Obtener el número de servidor de operación
        int serverNum = Integer.parseInt(args[0]);

        // Crear el objeto cuyos métodos el cliente podrá usar
        OperationServerImpl operationServer = new OperationServerImpl(serverNum);

        // Incluir el objeto en el registro del RMI en el puerto 22222/3 para que el cliente lo pueda encontrar.
        Registry registry = LocateRegistry.createRegistry(22222 + serverNum);
        String name = "operationserver" + (serverNum + 1);
        registry.rebind(name, operationServer);
        System.out.println("Objeto -" + name + "- Registrado en el RMI");
    }

    //Parte 1 de la operacion de varianza en la que se calcula el valor esperado de x al cuadrado
    @Override
    public float calculatePart1(float[][] data) throws RemoteException {
        float res = 0;
        for (float[] datum : data)
            res += pow(datum[0], 2) * datum[1];
        System.out.println("  > Respuesta enviada: " + res);
        return res;
    }

    //Parte 2 de la operacion de varianza en la que se calcula el valor esperado al cuadrado
    @Override
    public float calculatePart2(float[][] data) throws RemoteException {
        float res = 0;
        for (float[] datum : data)
            res += datum[0] * datum[1];
        res = (float) (pow(res, 2) * -1);
        System.out.println("  > Respuesta enviada: " + res);
        return res;
    }
}
