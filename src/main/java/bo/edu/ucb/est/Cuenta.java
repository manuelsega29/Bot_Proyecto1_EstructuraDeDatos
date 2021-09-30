
package bo.edu.ucb.est;


public class Cuenta {
    
    private String moneda;
    private String tipo;
    private double saldo;
    
    public Cuenta(String moneda, String tipo, double saldoInicial) {
        this.moneda = moneda;
        this.tipo = tipo;
        this.saldo = saldoInicial;
    }
    
   public double MostrarSaldo(){
        return saldo;
    } 
    
    public String retirar(double monto) {
        String resultado = "No se pudo retirar dinero, hubo un error";
        if (monto > 0 && monto <= saldo) { // verifica que no sea negativo, cero o exceda su saldo
            saldo = saldo - monto;
            resultado = "Retiro exitoso!!!";
        }
        return resultado;
    }
    
    public String depositar(double monto) {
        String resultado ="No se pudo depositar, hubo un error";
        if (monto > 0 ) { // verifica que no sea negativo, cero o exceda su saldo
            saldo = saldo + monto;
            resultado = "Depósito exitoso";
        }
        return resultado;
    }
         
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
}
