
package bo.edu.ucb.est;

import java.util.List;
import java.util.ArrayList;




public class Cliente {
    
    
    
    private String nombre;
    private String codigoCliente;
    private String pinSeguridad;
    private Long ID;
    private List<Cuenta> cuentas;
        
    public Cliente(String nombre, String codigoCliente, String pinSeguridad, Long ID) {
        this.nombre = nombre;
        this.codigoCliente = codigoCliente;
        this.pinSeguridad = pinSeguridad;
        this.ID= ID;
        this.cuentas = new ArrayList();
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Long getID() {
        return ID;
    }

    public void agregarCuenta(Cuenta cuenta) {
        this.cuentas.add(cuenta);
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getPinSeguridad() {
        return pinSeguridad;
    }

    public void setPinSeguridad(String pinSeguridad) {
        this.pinSeguridad = pinSeguridad;
    }

    public List<Cuenta> getCuentas() {
        return cuentas;
    }
        

    public void setCuentas(List<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }
    
    
    
}
