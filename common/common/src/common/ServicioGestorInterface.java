package common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioGestorInterface extends Remote{
	public String obtenerServicioClienteOperador(int id) throws RemoteException, NotBoundException, MalformedURLException;
	public String obtenerServicioServidorOperador(int id) throws RemoteException, NotBoundException, MalformedURLException;
}
