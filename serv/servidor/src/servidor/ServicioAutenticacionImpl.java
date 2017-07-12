/**
 * 
 */
package servidor;

/**
 * @author CRuiz
 *
 */
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
 
import common.ServicioAutenticacionInterface;
import common.ServicioDatosInterface;
import common.Utils;
 
public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {
 
	private static final long serialVersionUID = 593676645816200166L;
    // Numero aleatorio para obtener los numeros de sesión de los Clientes y Repositorios
    private static int sesion = new Random().nextInt();
    private static int miSesion = 0;
    // Objeto para el acceso al servicio Datos.
    private static ServicioDatosInterface datos;
    private String nombreLog;
 
    protected ServicioAutenticacionImpl() throws RemoteException {
        super();
    }
 
    // Función que autentica un cliente y devuelve el número de sesión que le ha
    // sido asignado o 0 en caso de que no se haya podido llevar a cabo la autenticación.
    @Override
    public int autenticarCliente(String nombre) throws RemoteException {
        System.out.println("Cliente " + nombre + " esta intentando autenticarse");
        int sesionUsuario = getSesion();
        try {
            datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:1492/datos");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
 
        if (datos.ingresaCliente(nombre, sesionUsuario)) {
            return sesionUsuario;
        } else {
            return 0;
        }
    }
 
    // Función que autentica un repositorio y devuelve el número de sesión que le ha sido asignado
    //  o 0 en caso de que no se haya podido llevar a cabo la autenticación.
    public int autenticarRepositorio(String nombre) throws RemoteException {
 
    	System.out.println("Repo " + nombre + " esta intentando autenticarse");
        int sesionUsuario = getSesion();
 
        try {
            datos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:1492/datos");
        } catch (NotBoundException e) {
        // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
 
        if (datos.ingresaRepositorio(nombre, sesionUsuario)) {
            return sesionUsuario;
        } else {
            return 0;
        }
    }
 
    public static int getSesion() {
        return ++sesion;
    }
 
}
