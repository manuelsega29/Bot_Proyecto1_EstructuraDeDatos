
package bo.edu.ucb.est;


import java.util.ArrayList;
import java.util.List;

public class Banco {
    
    
    private String nombre;
    private List<Cliente>clientes;
    
    public Banco(String nombre) {
        this.nombre = nombre;
        this.clientes = new ArrayList();
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public List<Cliente> getClientes() {
        return this.clientes;
    }
    
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }
    
    public Cliente buscarAlClienteUsuario(Long ID,String pin) {
        
        for ( int i = 0; i < clientes.size(); i++) {
            Cliente cli = clientes.get(i); // Sacando elemento por elemento
            if (cli.getNombre().equals(ID) && cli.getPinSeguridad().equals(pin)) {
                return cli;
            }
        }
        return null;
    }
    
    public Cliente buscarIDClienteUsuario(Long ID) {
        
        for ( int i = 0; i < clientes.size(); i++) {
            Cliente cli = clientes.get(i); // Sacando elemento por elemento
            if ( cli.getNombre().equals(ID) ) {
                return cli;
            }
        }
        return null;
    }
    
    
}
