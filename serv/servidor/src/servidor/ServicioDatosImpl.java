package servidor;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import common.ServicioDatosInterface;
import common.Utils;
 
public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {
 
     private static final long serialVersionUID = 1526643248021674437L;
     // Conjunto de Maps para almacenar y relacionar los clientes, los
     // repositorios y los numeros de sesion
     private Map<Integer, String> sesion_cliente = new HashMap<Integer, String>();
     private Map<String, Integer> cliente_sesion = new HashMap<String, Integer>();
     private Map<Integer, String> sesion_repositorio = new HashMap<Integer, String>();
     private Map<String, Integer> repositorio_sesion = new HashMap<String, Integer>();
     private Map<Integer, Integer> cliente_repositorio = new HashMap<Integer, Integer>();
 
     // Nombre del archivo que mantiene el Log del Servidor.
     private String nombreLog;
 
     protected ServicioDatosImpl() throws RemoteException {
          super();
     }
 
     protected ServicioDatosImpl(String nombreLog) throws RemoteException {
          super();
          this.nombreLog = nombreLog;
     }
 
     // Meetodo que añade un Cliente al sistema recibiendo como parámetro un
     // nombre y una id. No se permiten nombres de clientes repetidos. Tambien
     // asocia al Cliente con un repositorio existente. En el caso de no haber
     // repositorios no se ingresa el Cliente.
     @Override
     public boolean ingresaCliente(String nombre, int id) throws RemoteException {
          // Comprobamos que el cliente no exista
          if (cliente_sesion.containsKey(nombre.toUpperCase())) {
          //Utils.escribeLog(nombreLog,
          //"El cliente ya se encuentra dado de alta.");
        	  System.out.println("El cliente ya se encuentra dado de alta");
          return false;
          } else {
               // Recogemos la lista de repositorios disponibles
               List<Integer> repositorios = new ArrayList<Integer>(
               repositorio_sesion.values());
               if (repositorios.isEmpty()) {
                    //Utils.escribeLog(nombreLog, "No hay repositorios disponibles.");
            	   System.out.println("No hay repositorios disponibles.");
                    return false;
               } else {
                    // Añadimos el cliente a la "base de datos"
                    sesion_cliente.put(id, nombre.toUpperCase());
                    cliente_sesion.put(nombre.toUpperCase(), id);
                    // Asociamos al cliente con un repositorio al azar
                    int n = (int) (Math.random() * repositorios.size());
                    cliente_repositorio.put(id, repositorios.get(n));
                    //Utils.escribeLog(nombreLog, "Autenticando a " + nombre + " con la id " + id);
                    System.out.println("Autenticando a " + nombre + " con la id " + id);
                    return true;
                }
          }
     }
 
     // Método que añade un Repositorio al sistema recibiendo como parámetros un
     // nombre y una id. No se permiten nombres de Repositorios repetidos.
     @Override
     public boolean ingresaRepositorio(String nombre, int id) throws RemoteException {
 
          if (repositorio_sesion.containsKey(nombre.toUpperCase())) {
               //Utils.escribeLog(nombreLog, "El repositorio ya se encuentra dado de alta.");
        	  System.out.println("El repositorio ya se encuentra dado de alta.");
               return false;
          }
          // De ser así lo añadimos a la "base de datos"
          else {
               sesion_repositorio.put(id, nombre.toUpperCase());
               repositorio_sesion.put(nombre.toUpperCase(), id);
               //Utils.escribeLog(nombreLog, "Autenticando a " + nombre + " con la id " + id);
               System.out.println("Autenticando a " + nombre + " con la id " + id);
               return true;
          }
     }
 
     // Metodo que devuelve una colección con los nombres de los Clientes.
     @Override
     public Collection<String> listaClientes() throws RemoteException {
          Collection<String> clientes = (Collection<String>) sesion_cliente.values();
          return clientes;
     }
 
     // Metodo que devuelve una colección con los nombres de los Repositorios.
     @Override
     public Collection<String> listaRepositorios() throws RemoteException {
          Collection<String> repositorios = (Collection<String>) sesion_repositorio.values();
          return repositorios;
     }
 
     // Metodo que devuelve la id del repositorio que le corresponde a un cliente
     // cuya id que se pasa como parámetro.
     @Override
     public int buscaRepositorio(int cliente) throws RemoteException {
 
          int repositorio = 0;
          try {
               repositorio = cliente_repositorio.get(cliente);
          } catch (Exception e) {
               System.out.println("ServicioDatosImpl.buscaRepositorio: " + e.getMessage());
               System.out.println(cliente_repositorio.get(cliente));
          }
          return repositorio;
     }
 
     // Método que imprime por pantalla la lista de repositorios junto con los
     // clientes asociados a los mismos.
     @Override
     public void listarRepositoriosClientes() throws RemoteException {
          ArrayList<Integer> repositorios = new ArrayList<Integer>(repositorio_sesion.values());
          ArrayList<Integer> clientes = new ArrayList<Integer>(
          cliente_repositorio.keySet());
               if (repositorios.isEmpty()) {
                    //Utils.escribeLog(nombreLog, "No hay repositorios registrados actualmente.");
            	   System.out.println("No hay repositorios registrados actualmente.");
               } else if (clientes.isEmpty()) {
                    //Utils.escribeLog(nombreLog, "No hay clientes registrados actualmente.");
            	   System.out.println("No hay clientes registrados actualmente.");
               } else {
                    for (int n : repositorios) {
                         System.out.println("REPOSITORIO " + sesion_repositorio.get(n));
                         System.out.println("===========================================");
                         System.out.println("CLIENTES");
                         System.out.println("===========================================");
                         for (int c : clientes) {
                              if (cliente_repositorio.get(c) == n) {
                                   System.out.println(sesion_cliente.get(c));
                         }
                    }
               System.out.println("===========================================");
               }
          }
     }
}