import java.rmi.Remote;

public interface CentralServer extends Remote {
    public float calculateVar(float[][] data) throws java.rmi.RemoteException;
}
