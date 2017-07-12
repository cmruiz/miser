package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Utils {

public static void startRegistry(int RMIPortNum) throws RemoteException {
     try {
          Registry registry = LocateRegistry.getRegistry(RMIPortNum);
          registry.list(); // This call throws a exception if the registry
                           // does not already exist
     } catch (RemoteException ex) {
          // No valid registry at that port.System.out.println("RMI registry cannot be located at port " + RMIPortNum);
          Registry registry = LocateRegistry.createRegistry(RMIPortNum);
          System.out.println("RMI registry created at port " + RMIPortNum);
     }
 }

}
