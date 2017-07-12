/**
 * 
 */
package servidor;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import common.ServicioDatosInterface;
import common.Utils;

public class Servidor {
 
     // Variable para leer la entrada por teclado
     private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
 
     // Direccion del fichero que contiene el Log de esta sesion
     private static String nombreLog;
 
     // Variable que indica el puerto donde se levantara el registro
     private static int puertoRegistro = 1977;
 
     // Main de la Clase Servidor
     public static void main(String[] args) throws Exception {
 
     if (true) {
          System.out.println("Log creado correctamente.");
 
          // Iniciamos el registro en el puerto indicado
          startRegistry(puertoRegistro);
 
          // Levantamos el SERVICIO DE DATOS
          ServicioDatosImpl datos = new ServicioDatosImpl(nombreLog);
          Naming.rebind("rmi://localhost:" + puertoRegistro + "/datos", datos);
          System.out.println("Servicio Datos listo");
 
          // Levantamos el SERVICIO DE AUTENTICACION
          ServicioAutenticacionImpl autenticacion = new ServicioAutenticacionImpl();
          Naming.rebind("rmi://localhost:" + puertoRegistro + "/autenticacion", autenticacion);
          System.out.println("Servicio Autenticacion listo");
 
          // Levantamos el SERVICIO DE GESTION
 //         ServicioGestorImpl gestor = new ServicioGestorImpl(nombreLog);
 //         Naming.rebind("rmi://localhost:" + puertoRegistro + "/gestor", gestor);
 //         System.out.println("Servicio Gestor listo");
 
          // Imprimimos el MENU
          int option = 1;
          do {
               System.out.println("1.- Listar Clientes.");
               System.out.println("2.- Listar Repositorios.");
               System.out.println("3.- Listar Parejas Repositorio-Cliente.");
               System.out.println("4.- Salir.");
               option = Integer.parseInt(reader.readLine());
               switch (option) {
                    case 1:
                         Collection<String> clientes = datos.listaClientes();
                         if (!clientes.isEmpty()) {
                              System.out.println("==================================");
                              System.out.println("Clientes.");
                              System.out.println("==================================");
                              for (String nombre : clientes) {
                                   System.out.println(nombre);
                              }
                              System.out.println("==================================");
                         } else {
                              System.out.println("==================================");
                              System.out.println("No hay clientes registrados.");
                              System.out.println("==================================");
                         }
                    break;
                    case 2:
                         Collection<String> repositorios = datos.listaRepositorios();
                         if (!repositorios.isEmpty()) {
                              System.out.println("==================================");
                              System.out.println("Repositorios.");
                              System.out.println("==================================");
                              for (String nombre : repositorios) {
                                   System.out.println(nombre);
                              }
                              System.out.println("==================================");
                         } else {
                              System.out.println("==================================");
                              System.out.println("No hay repositorios registrados.");
                              System.out.println("==================================");
                         }
                    break;
                    case 3:
                         datos.listarRepositoriosClientes();
                    break;
                    case 4:
                         // Finalizamos el servicio de Autenticacion
                         Naming.unbind("rmi://localhost:" + puertoRegistro + "/autenticacion");
                         // Finalizamos el servicio de Datos
                         Naming.unbind("rmi://localhost:" + puertoRegistro + "/datos");
//                         // Finalizamos el servicio Gestor
//                         Naming.unbind("rmi://localhost:" + puertoRegistro + "/gestor");
                    break;
                    default:
                         System.out.println("Opción incorrecta.");
                    break;
               }
          } while (option != 4);
 
     } else {
          System.out.println("Error en la creación del log. Saliendo.");
     }
     }
     
     
     
     private static void startRegistry(int RMIPortNum) throws RemoteException {
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
     
