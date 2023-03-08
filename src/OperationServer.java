import java.rmi.Remote;

public interface OperationServer extends Remote {
    public float calculatePart1(float[][] data) throws java.rmi.RemoteException;
    public float calculatePart2(float[][] data) throws java.rmi.RemoteException;
}
