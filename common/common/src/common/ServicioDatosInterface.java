package common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
 
public interface ServicioDatosInterface extends Remote{
    public boolean ingresaCliente(String nombre, int id) throws RemoteException;
    public boolean ingresaRepositorio(String nombre, int id) throws RemoteException;
    public Collection<String> listaClientes() throws RemoteException;
    public Collection<String> listaRepositorios() throws RemoteException;
    public void listarRepositoriosClientes() throws RemoteException;
    public int buscaRepositorio(int cliente) throws RemoteException;
}