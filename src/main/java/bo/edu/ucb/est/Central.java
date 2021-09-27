
package bo.edu.ucb.est;

import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Central extends TelegramLongPollingBot {
    private String VerificacionSiTienenCuenta="Hola, soy el bot de banca\nEres miembro de nuestra banca?\nA.Si, quiero ingresar\nB.No, quiero unirme";
    private String IntroducirNombre="Introduzca su nombre completo: ";
    private String IntroducirPin="Introduzca PIN de seguridad: ";
    private String MenuInicio="Bienvenido\nEste es tu menú de opciones, elije lo que desees:"
            + "\na. Ver saldo \nb. Retirar Dinero \nc. Abonar Dinero \nd. Crear Cuenta \ne. Salir";
    private String Despedida="Ok, que tengas un buen día!!!!";
    private String RespuestaUs;
    private String EsSocio;
    private int EstadoInicial=0;
    private String OpcionMenu;
    private String Pin;
    private String NombreUsuario;
    private int AuxMenu=0;
    private Cliente cliente1;
    private double Montos=0;
    private int auxi=0;
    private int DatosDeOpcionUsuario=0;
    private int RespaldoDatosDeOpcionUsuario=0;
    private List<Cuenta> cuentaUs;
       
    @Override
    public String getBotToken() {
        return "";
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        Banco bancaAmiga = new Banco("Banca Bot");
        ///
        Cliente jperez = new Cliente("JP", "jperez", "3333");
        Cuenta cta1Jperez = new Cuenta("BOB", "Caja Ahorros", 12000.0);
        jperez.agregarCuenta(cta1Jperez);
        Cuenta cta2Jperez = new Cuenta("USD", "Cuenta Corriente", 100.0);
        jperez.agregarCuenta(cta2Jperez);
        bancaAmiga.agregarCliente(jperez);
        ///
        Cliente mgomez = new Cliente("Maria Gomez", "mgomez", "4444");
        Cuenta cta1Mgomez = new Cuenta("BOB", "Caja Ahorros", 0.0);
        mgomez.agregarCuenta(cta1Mgomez);
        bancaAmiga.agregarCliente(mgomez);
        ///
        Cliente cgomez = new Cliente("Carlos Gomez", "cgomez", "3333");
        Cuenta cta1Cgomez = new Cuenta("BOB", "Caja Ahorros", 100.0);
        cgomez.agregarCuenta(cta1Cgomez);
        Cuenta cta2Cgomez = new Cuenta("USD", "Cuenta Corriente", 1000.0);
        cgomez.agregarCuenta(cta2Cgomez);
        Cuenta cta3Cgomez = new Cuenta("BOB", "Caja Ahorros", 100000.0);
        cgomez.agregarCuenta(cta3Cgomez);
        bancaAmiga.agregarCliente(cgomez);      
        /////////
        SendMessage message = new SendMessage();// Creo el objeto para enviar un mensaje arrba
        message.setChatId(update.getMessage().getChatId().toString());//Define a quien le vamos a enviar el mensaje
        System.out.println("Llego mensaje: " + update.toString());
        if(update.hasMessage()&& update.getMessage().hasText()) { // Verificamos que tenga mensaje
            switch (EstadoInicial){
                ////////////
                case 0:
                    message.setText(VerificacionSiTienenCuenta);
                    try{
                        execute(message);
                    }catch(TelegramApiException e){
                        e.printStackTrace();
                    }
                    EstadoInicial=1;
                    break;
                 ///////////
                case 1:
                    RespuestaUs=update.getMessage().getText();
                    if(RespuestaUs.equals("A")||RespuestaUs.equals("a")||RespuestaUs.equals("SI")||RespuestaUs.equals("Si")||RespuestaUs.equals("si")){
                       message.setText(IntroducirNombre);
                       try{
                           execute(message);
                       }catch(TelegramApiException e){ 
                           e.printStackTrace();
                       }
                       EsSocio="si";
                       EstadoInicial=2;
                    }else if(RespuestaUs.equals("B")||RespuestaUs.equals("b")||RespuestaUs.equals("NO")||RespuestaUs.equals("No")||RespuestaUs.equals("no")){
                       message.setText(IntroducirNombre);
                       try{
                           execute(message);
                       }catch(TelegramApiException e){ 
                           e.printStackTrace();
                       }
                       EsSocio="no";
                       EstadoInicial=2;
                    }else{
                       message.setText(VerificacionSiTienenCuenta);
                       try{
                            execute(message);
                       }catch(TelegramApiException e){ 
                           e.printStackTrace();
                       } 
                    }
                    break;
                /////////////
                case 2:
                    NombreUsuario=update.getMessage().getText();
                    message.setText(IntroducirPin);
                       try{
                            execute(message);
                       }catch(TelegramApiException e){ 
                           e.printStackTrace();
                       }
                       EstadoInicial=3;
                    break;
                 /////////////////   
                case 3:
                    Pin=update.getMessage().getText();
                    if(EsSocio.equals("si")){
                        if(bancaAmiga.buscarClientePorPIN(NombreUsuario, Pin)==null){   
                            message.setText("Nombre o Pin seleccionados de Forma Incorrecta");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            message.setText(VerificacionSiTienenCuenta);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            EstadoInicial=1;
                         }else{
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            cliente1=bancaAmiga.buscarClientePorPIN(NombreUsuario, Pin);
                            EstadoInicial=4;
                        } 
                    }else{
                        message.setText(MenuInicio);
                        try{
                            execute(message);
                        }catch(TelegramApiException e){ 
                            e.printStackTrace();
                        }
                        EstadoInicial=4;
                    }
                    break;
                ///////////////
                case 4:
                    
                    
                    OpcionMenu=update.getMessage().getText();
                    cuentaUs =cliente1.getCuentas();
                    int y = cuentaUs.size();
                    //////////////////////
                    if( auxi==1 || OpcionMenu.equals("A") || OpcionMenu.equals("a") ){
                        auxi=1;
                        if(AuxMenu==0){
                            message.setText("Tu tienes " + y + " cuentas, de cual quiere ver el saldo???");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            AuxMenu++;
                        }else{
                            RespuestaUs=update.getMessage().getText();
                            try{
                                DatosDeOpcionUsuario=Integer.parseInt(RespuestaUs);
                            }catch(Exception e){
                                DatosDeOpcionUsuario=-11;
                            }
                            
                            if(DatosDeOpcionUsuario<=y && DatosDeOpcionUsuario>=1){
                                DatosDeOpcionUsuario=DatosDeOpcionUsuario-1;
                                message.setText("El saldo de la cuenta es "  + cuentaUs.get(DatosDeOpcionUsuario).MostrarSaldo() + " en " +cuentaUs.get(DatosDeOpcionUsuario).getMoneda()+ " de tipo "+ cuentaUs.get(DatosDeOpcionUsuario).getTipo());
                                try{
                                   execute(message);
                                }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                                }
                                message.setText(MenuInicio);
                                try{
                                    execute(message);
                                }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                                }
                                auxi=0;
                                EstadoInicial=4;
                                AuxMenu=0;
                            }else{
                                message.setText("Hubo un error en el número de cuenta que escogiste");
                                try{
                                    execute(message);
                                }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                                }
                                message.setText(MenuInicio);
                                try{
                                    execute(message);
                                }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                                }
                                auxi=0;
                                EstadoInicial=4;
                                AuxMenu=0;
                            }
                        }
                    ///////////////////////////////
                    }else if(auxi==2||OpcionMenu.equals("b")||OpcionMenu.equals("B")){
                        auxi=2;
                        RespuestaUs=update.getMessage().getText();
                        try{
                            DatosDeOpcionUsuario=Integer.parseInt(RespuestaUs);
                        }catch(Exception e){
                            DatosDeOpcionUsuario=-10;
                        }
                        if(AuxMenu==0){
                            message.setText("Las Cuentas que tienes son las siguientes: \n");
                            try{
                                   execute(message);
                            }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                            }
                            for(int i=0; i<y; i++){
                                message.setText((i+1)+". Tipo " +cuentaUs.get(i).getTipo()+" con saldo de "+cuentaUs.get(i).MostrarSaldo()+" moneda "+cuentaUs.get(i).getMoneda() + "\n");
                                try{
                                    execute(message);
                                }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                                }
                            }
                            AuxMenu=1;
                        }else if(AuxMenu==1 && DatosDeOpcionUsuario<=y && DatosDeOpcionUsuario>0){
                            message.setText("Ok, cuanto dinere extraerá de esa cuenta:  ");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            RespaldoDatosDeOpcionUsuario=DatosDeOpcionUsuario;
                            AuxMenu=2;
                        }else if(AuxMenu==1 && (DatosDeOpcionUsuario>y || DatosDeOpcionUsuario<=0)){
                            message.setText("Hubo un error en el número de cuenta que escogiste");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            EstadoInicial=4;
                            AuxMenu=0;
                            auxi=0;
                        }else if(AuxMenu==2){
                            RespuestaUs=update.getMessage().getText();
                            try{
                                Montos=Double.parseDouble(RespuestaUs);
                            }catch(Exception e){
                                Montos=-10;
                            }
                            RespaldoDatosDeOpcionUsuario=RespaldoDatosDeOpcionUsuario-1;
                            message.setText(cuentaUs.get(RespaldoDatosDeOpcionUsuario).retirar(Montos));
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            auxi=0;
                            AuxMenu=0;
                            EstadoInicial=4;
                        }
                        
                        //////////////
                        
                    }else if(auxi==3||OpcionMenu.equals("C")||OpcionMenu.equals("c")){
                        
                        auxi=3;
                        RespuestaUs=update.getMessage().getText();
                        try{
                            DatosDeOpcionUsuario=Integer.parseInt(RespuestaUs);
                        }catch(Exception e){
                            DatosDeOpcionUsuario=-10;
                        }
                        if(AuxMenu==0){
                            message.setText("Las Cuentas que tienes son las siguientes: \n");
                            try{
                                   execute(message);
                            }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                            }
                            for(int i=0; i<y; i++){
                                message.setText((i+1)+". Tipo " +cuentaUs.get(i).getTipo()+" con saldo de "+cuentaUs.get(i).MostrarSaldo()+" moneda "+cuentaUs.get(i).getMoneda() + "\n");
                                try{
                                    execute(message);
                                }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                                }
                            }
                            AuxMenu=1;
                        }else if(AuxMenu==1 && DatosDeOpcionUsuario<=y && DatosDeOpcionUsuario>0){
                            message.setText("Ok, cuanto dinere agregará a esa cuenta:  ");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            RespaldoDatosDeOpcionUsuario=DatosDeOpcionUsuario;
                            AuxMenu=2;
                        }else if(AuxMenu==1 && (DatosDeOpcionUsuario>y || DatosDeOpcionUsuario<=0)){
                            message.setText("Hubo un error en el número de cuenta que escogiste");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            EstadoInicial=4;
                            AuxMenu=0;
                            auxi=0;
                        }else if(AuxMenu==2){
                            RespuestaUs=update.getMessage().getText();
                            try{
                                Montos=Double.parseDouble(RespuestaUs);
                            }catch(Exception e){
                                Montos=-10;
                            }
                            RespaldoDatosDeOpcionUsuario=RespaldoDatosDeOpcionUsuario-1;
                            message.setText(cuentaUs.get(RespaldoDatosDeOpcionUsuario).depositar(Montos));
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            auxi=0;
                            AuxMenu=0;
                            EstadoInicial=4;
                        }
                        /////////////
                    }else if(auxi==4){
                        
                        
                        //// Metodo Agregando Cuenta Falta
                        
                        
                    }else if(auxi==5 || OpcionMenu.equals("E") || OpcionMenu.equals("e")){
                        
                        message.setText(Despedida);
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                        EstadoInicial=0;
                    
                    }
                    
                    
                    break;
                    
                    
                ///////////////  
             
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "estructus_manuel_bot";
    }
    
    
    
}
