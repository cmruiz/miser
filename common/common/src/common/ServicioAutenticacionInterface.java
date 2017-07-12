package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterface extends Remote {
	public int autenticarCliente(String nombre) throws RemoteException;
	public int autenticarRepositorio(String nombre) throws RemoteException;
}