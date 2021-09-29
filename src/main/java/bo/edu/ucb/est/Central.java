package bo.edu.ucb.est;

import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Central extends TelegramLongPollingBot {
    private String VerificacionSiTienenCuenta="Hola, soy el bot de banca\nEres miembro de nuestra banca?\nA.Si, quiero ingresar\nB.No, quiero unirme";
    private String IntroducirNombre="Introduzca su nombre completo: ";
    private String IntroducirPin="Introduzca PIN de seguridad: ";
    private String MenuInicio="Bienvenido\nEste es tu menú de opciones, elije lo que desees:"
            + "\na. Ver saldo \nb. Retirar Dinero \nc. Abonar Dinero \nd. Crear Cuenta \ne. Salir";
    private String Despedida="Ok, que tengas un buen día!!!!";
    private Map RespuestaUs1 = new HashMap();
    private String RespuestaUs;
    private Map EsSocio1 = new HashMap();
    private String EsSocio;
    private Map OpcionMenu1 = new HashMap();
    private String OpcionMenu;
    private Map Pin1 = new HashMap();
    //private String Pin;
    private Map NombreUsuario1 = new HashMap();
    //private String NombreUsuario;
    private Map cliente11 = new HashMap();
    private Cliente cliente1;
    private Map cuentaUs1 = new HashMap();
    private List<Cuenta> cuentaUs;
    private Map EstadoInicial1 = new HashMap();
    //private Integer EstadoInicial;  //
    private Map AuxMenu1=new HashMap();
    //private Integer AuxMenu=0;        //
    //private Map Montos1 =new HashMap();
    private double Montos=0;
    private Map auxi1= new HashMap();
    //private Integer auxi=0;          //
    private Map DatosDeOpcionUsuario1= new HashMap();
    private Integer DatosDeOpcionUsuario=0;          //////
    private Map RespaldoDatosDeOpcionUsuario1= new HashMap();
    private Integer RespaldoDatosDeOpcionUsuario=0;
    
    //private Integer VerificacionSiEsSocio=0;
    private Map VerificacionSiEsSocio1=new HashMap();

    
    
    
  ///     
    @Override
    public String getBotToken() {
        return "";
    }
    
    @Override
    public void onUpdateReceived(Update update) {
         Banco bancaAmiga = new Banco(" BOT BANCA ");             
        
        SendMessage message = new SendMessage();// Creo el objeto para enviar un mensaje arrba
        message.setChatId(update.getMessage().getChatId().toString());//Define a quien le vamos a enviar el mensaje
        System.out.println("Llego mensaje: " + update.toString());
        
        
        if(update.hasMessage()) { // Verificamos que tenga mensaje
            Long ID=update.getMessage().getChatId();
            
            Integer EstadoInicial;
            if((Integer)EstadoInicial1.get(ID)==null){
                EstadoInicial=0;
                EstadoInicial1.put(ID, 0);
            }else{
                EstadoInicial=(Integer)EstadoInicial1.get(ID);
            }
            
            Integer VerificacionSiEsSocio;
            if((Integer)VerificacionSiEsSocio1.get(ID)==null){
                VerificacionSiEsSocio=0;
                VerificacionSiEsSocio1.put(ID,0);
            }else{
                VerificacionSiEsSocio=(Integer)VerificacionSiEsSocio1.get(ID);
            }
            
            System.out.println("LLEGAMOS a esta parteE");
            switch (EstadoInicial){
                ////////////
                case 0:
                    if(bancaAmiga.buscarIDClienteUsuario(ID).equals(null)){
                        message.setText("Oye no recuerdo tu ID, parece que no eres un cliente. Danos tu nombre!");
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                        EstadoInicial1.put(ID,1);
                        VerificacionSiEsSocio1.put(ID,1);
                    }else{
                        message.setText("Como es! \nPon tu Pin de Seguirdad para asegurarnos que realmente eres nuestro Cliente");
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                        EstadoInicial1.put(ID,2);
                        VerificacionSiEsSocio1.put(ID,2);
                    }
                    break;
                 ///////////
                case 1:
                    RespuestaUs=update.getMessage().getText();
                    NombreUsuario1.put(ID,RespuestaUs);
                    message.setText("Listo, ahora danos un PIN ahora, es por seguridad");
                    try{
                        execute(message);
                    }catch(TelegramApiException e){
                        e.printStackTrace();
                    }
                    EstadoInicial1.put(ID,2);
                    break;
                /////////////
                case 2:
                    RespuestaUs=update.getMessage().getText();
                    
                    if(VerificacionSiEsSocio==1){
                        Cliente clientenuevo = new Cliente((String)NombreUsuario1.get(ID), "cnuevo", RespuestaUs, ID);
                        bancaAmiga.agregarCliente(clientenuevo);
                        Pin1.put(ID,RespuestaUs);
                        EstadoInicial1.put(ID,3);
                        message.setText(MenuInicio);
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                    }else{
                        if(bancaAmiga.buscarAlClienteUsuario(ID,RespuestaUs).equals(null)){
                            message.setText("El PIN que nos diste fue errado");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){
                                e.printStackTrace();
                            }
                            message.setText("Como te ha ido!! \nDanos tu Pind de seguridad para ingresar:   \n");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){
                                e.printStackTrace();
                            }
                            EstadoInicial1.put(ID,3);
                        }else{
                            Pin1.put(ID,RespuestaUs);
                            EstadoInicial1.put(ID,3);
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    
                    break;
                 /////////////////   
                case 4:
                    Integer auxi;    ///////////////////
                    if((Integer)auxi1.get(ID)==null){
                        auxi=0;
                        auxi1.put(ID,0);
                    }else{
                        auxi=(Integer)auxi1.get(ID);
                    }
                    
                    Integer AuxMenu; ///////////////
                    if((Integer)AuxMenu1.get(ID)==null){
                        AuxMenu=0;
                        AuxMenu1.put(ID, 0);
                    }else{
                        AuxMenu=(Integer)AuxMenu1.get(ID);
                    }                    
                    
                    try{
                        OpcionMenu=update.getMessage().getText();
                    }catch(Exception e){
                        OpcionMenu="HUBO ErroR";
                    }
                    
                    cliente1=bancaAmiga.buscarIDClienteUsuario(((String)NombreUsuario1.get(ID)));
                    cuentaUs = cliente1.getCuentas();
                    int y = cuentaUs.size();
                    
                    //////////////////////
                    if( auxi==1 || OpcionMenu.equals("A") || OpcionMenu.equals("a") ){
                        auxi1.put(ID,1);
                        if(AuxMenu==0){
                            message.setText("Tu tienes " + y + " cuentas, de cual quiere ver el saldo???");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            AuxMenu1.put(ID, 1);        ///cambio de estados dentRO de Mi MENÓúuuu                    
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
                                auxi1.put(ID,0);
                                EstadoInicial1.put(ID,4);
                                AuxMenu1.put(ID,0);
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
                                auxi1.put(ID,0);
                                EstadoInicial1.put(ID,4);
                                AuxMenu1.put(ID,0);
                            }
                        }
                    ///////////////////////////////
                    }else if(auxi==2||OpcionMenu.equals("b")||OpcionMenu.equals("B")){
                        auxi1.put(ID,2);
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
                            AuxMenu1.put(ID,1);
                        }else if(AuxMenu==1 && DatosDeOpcionUsuario<=y && DatosDeOpcionUsuario>0){
                            message.setText("Ok, cuanto dinero extraerá de esa cuenta:  ");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            RespaldoDatosDeOpcionUsuario1.put(ID,DatosDeOpcionUsuario);
                            AuxMenu1.put(ID,2);
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
                            EstadoInicial1.put(ID,4);
                            AuxMenu1.put(ID,0);
                            auxi1.put(ID,0);
                        }else if(AuxMenu==2){
                            RespuestaUs=update.getMessage().getText();
                            try{
                                Montos=Double.parseDouble(RespuestaUs);
                            }catch(Exception e){
                                Montos=-10;
                            }
                            RespaldoDatosDeOpcionUsuario=((Integer)RespaldoDatosDeOpcionUsuario1.get(ID))-1;
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
                            auxi1.put(ID,0);
                            EstadoInicial1.put(ID,4);
                            AuxMenu1.put(ID,0);
                        }                 
                        //////////////
                        
                    }else if(auxi==3||OpcionMenu.equals("C")||OpcionMenu.equals("c")){
                        
                        auxi1.put(ID,2);
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
                            AuxMenu1.put(ID,1);
                        }else if(AuxMenu==1 && DatosDeOpcionUsuario<=y && DatosDeOpcionUsuario>0){
                            message.setText("Ok, cuanto dinero agregará a esta cuenta:  ");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            RespaldoDatosDeOpcionUsuario1.put(ID,DatosDeOpcionUsuario);
                            AuxMenu1.put(ID,2);
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
                            EstadoInicial1.put(ID,4);
                            AuxMenu1.put(ID,0);
                            auxi1.put(ID,0);
                        }else if(AuxMenu==2){
                            RespuestaUs=update.getMessage().getText();
                            try{
                                Montos=Double.parseDouble(RespuestaUs);
                            }catch(Exception e){
                                Montos=-10;
                            }
                            RespaldoDatosDeOpcionUsuario=((Integer)RespaldoDatosDeOpcionUsuario1.get(ID))-1;
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
                            auxi1.put(ID,0);
                            EstadoInicial1.put(ID,4);
                            AuxMenu1.put(ID,0);
                        }     
                        
                    }else if(auxi==4){
                        
                        
                        //// Metodo Agregando Cuenta Falta
                        
                        
                    }else if(OpcionMenu.equals("E") || OpcionMenu.equals("e")){
                        
                        message.setText(Despedida);
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                        EstadoInicial1.put(ID,0);
                    
                    }else{
                        message.setText("De las opciones que escogiste pasó algo extraño, intenta nuevamente");
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                        
                        EstadoInicial1.put(ID,4);
                        message.setText(MenuInicio);
                        try{
                            execute(message);
                        }catch(TelegramApiException e){ 
                            e.printStackTrace();
                        }
                    }                    
                    break;             
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "estructus_manuel_bot";
    }
    
    
    
}