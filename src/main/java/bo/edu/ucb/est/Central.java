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
    private String MenuInicio="Bienvenido\nEste es tu menú de opciones, elije lo que desees:"
            + "\na. Ver saldo \nb. Retirar Dinero \nc. Abonar Dinero \nd. Crear Cuenta \ne. Salir";
    private String Despedida="Ok, que tengas un buen día!!!!";
    private String RespuestaUs;
    private String OpcionMenu;
    private Map Pin1 = new HashMap();
    private Map NombreUsuario1 = new HashMap();
    private Map cliente11 = new HashMap();
    private Cliente cliente1;
    private Map cuentaUs1 = new HashMap();
    //private List<Cuenta> cuentaUs;
    private Map EstadoInicial1 = new HashMap();
    private Integer EstadoInicial;
    private Map AuxMenu1=new HashMap();
    private double Montos=0;
    private Map auxi1= new HashMap();         //
    private Map DatosDeOpcionUsuario1= new HashMap();
    private Integer DatosDeOpcionUsuario=0;          //////
    private Map RespaldoDatosDeOpcionUsuario1= new HashMap();
    private Integer RespaldoDatosDeOpcionUsuario=0;
    private Map VerificacionSiEsSocio1=new HashMap();
    private Long ID;
    private Cliente clientenuevo;
    private Integer numeroCuentas;
    private Integer VerificacionSiEsSocio;
    private Integer auxi;
    private Integer AuxMenu;
    
    @Override
    public String getBotToken() {
        return "1939060923:AAEs9qeCDftGHL-YMx2gbOXCBmvw99M6ciU";
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        ID=update.getMessage().getChatId();
        SendMessage message = new SendMessage();
        message.setChatId(update.getMessage().getChatId().toString());
        System.out.println("Llego mensaje: " + update.toString());
        
        if(update.hasMessage()) {           
            EstadoInicial=(Integer)EstadoInicial1.get(ID);
            if(EstadoInicial==(null)){
                EstadoInicial=0;
                EstadoInicial1.put(ID,0);
            }
            
            VerificacionSiEsSocio=(Integer)VerificacionSiEsSocio1.get(ID);
            if(VerificacionSiEsSocio==(null)){
                VerificacionSiEsSocio=0;
                VerificacionSiEsSocio1.put(ID,0);
            }
                        
            switch (EstadoInicial){
                case 0:     
                    if(((Cliente)cliente11.get(ID))==(null)){                      
                        EstadoInicial1.put(ID,1);
                        VerificacionSiEsSocio1.put(ID,1);
                        message.setText("Oye no recuerdo tu ID, parece que no eres un cliente. Danos tu nombre!");
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                    }else{
                        EstadoInicial1.put(ID,2);
                        VerificacionSiEsSocio1.put(ID,2);
                        message.setText("Como estás \nPon tu Pin de Seguirdad para asegurarnos \nque realmente eres nuestro Cliente");
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                    }
                    break;
                 ///////////
                case 1:
                    RespuestaUs=update.getMessage().getText();
                    NombreUsuario1.put(ID,RespuestaUs);
                    EstadoInicial1.put(ID,2);
                    message.setText("Listo, ahora danos un PIN ahora, es por seguridad");
                    try{
                        execute(message);
                    }catch(TelegramApiException e){
                        e.printStackTrace();
                    }
                    break;
                /////////////
                case 2:
                    RespuestaUs=update.getMessage().getText();
                    if(VerificacionSiEsSocio==1){
                        clientenuevo = new Cliente((String)NombreUsuario1.get(ID), "ElNuevo", RespuestaUs, ID);
                        cliente11.put(ID,clientenuevo);
                        Pin1.put(ID,RespuestaUs);
                        EstadoInicial1.put(ID,3);
                        VerificacionSiEsSocio1.put(ID,2);
                        message.setText(MenuInicio);
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                    }else{
                        RespuestaUs=update.getMessage().getText();
                        if(((String)Pin1.get(ID)).equals(RespuestaUs)){
                            EstadoInicial1.put(ID,3);
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){
                                e.printStackTrace();
                            }
                        }else{
                            EstadoInicial1.put(ID,2);
                            message.setText("El PIN que nos diste fue errado");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){
                                e.printStackTrace();
                            }
                            message.setText("Como te ha ido!! \nDanos tu Pin de seguridad para ingresar:   \n");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                 /////////////////   
                case 3:
                    auxi=(Integer)auxi1.get(ID);
                    if(auxi==(null)){
                        auxi=0;
                        auxi1.put(ID,0);
                    }
                    AuxMenu=(Integer)AuxMenu1.get(ID);
                    if(AuxMenu==(null)){
                        AuxMenu=0;
                        AuxMenu1.put(ID,0);
                    }
                    cliente1=(Cliente)cliente11.get(ID); 
                    List<Cuenta> cuentaUs=cliente1.getCuentas();
                    Integer y = cuentaUs.size();
                    if(y==null){
                        y=0;
                    }
                    System.out.println("El Usuario es " + cliente1.getNombre() + " con el PIN " + cliente1.getPinSeguridad());
                    
                    OpcionMenu=update.getMessage().getText();
                    //////////////////// CHEQUEAR TUS CUENTAS ES AQUISITOS
                    System.out.println(OpcionMenu);
                    if( auxi==1 || OpcionMenu.equals("a") || OpcionMenu==("A") ){
                        auxi1.put(ID,1);
                        if(AuxMenu==0){
                            if(y>0){
                                AuxMenu1.put(ID,1);
                                message.setText("Tu tienes " + y + " cuentas, de cual quiere ver el saldo???");
                                try{
                                    execute(message);
                                }catch(TelegramApiException e){ 
                                    e.printStackTrace();
                                }  
                            }else{
                                AuxMenu1.put(ID, 0);
                                EstadoInicial1.put(ID,3);
                                auxi1.put(ID,0);
                                message.setText("No tienes cuentas, crea un cuenta!!");
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
                                EstadoInicial1.put(ID,3);
                            }                                            
                        }else{
                            RespuestaUs=update.getMessage().getText();
                            try{
                                DatosDeOpcionUsuario=Integer.parseInt(RespuestaUs);
                            }catch(Exception e){
                                DatosDeOpcionUsuario=-10;
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
                                EstadoInicial1.put(ID,3);
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
                                EstadoInicial1.put(ID,3);
                                AuxMenu1.put(ID,0);
                            }
                        }
                    ///////////////////// DESEAS DEPSiTAR SALE AQUíI
                    }else if(auxi==2||OpcionMenu.equals("b")||OpcionMenu.equals("B")){
                        auxi1.put(ID,2);
                        RespuestaUs=update.getMessage().getText();
                        try{
                            DatosDeOpcionUsuario=Integer.parseInt(RespuestaUs);
                        }catch(Exception e){
                            DatosDeOpcionUsuario=-10;
                        }                        
                        if(AuxMenu==0){
                            if(y==0){
                                AuxMenu1.put(ID, 0);
                                auxi1.put(ID,0);
                                EstadoInicial1.put(ID,3);
                                message.setText("No tienes cuentas, crea un cuenta!!");
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
                            }else{
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
                                EstadoInicial1.put(ID,3);
                                AuxMenu1.put(ID,1);
                            }
                        }else if(AuxMenu==1 && DatosDeOpcionUsuario<=y && DatosDeOpcionUsuario>0){
                            message.setText("Ok, cuanto dinero extraerá de esa cuenta:  ");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            RespaldoDatosDeOpcionUsuario1.put(ID,DatosDeOpcionUsuario);
                            AuxMenu1.put(ID,2);
                            EstadoInicial1.put(ID,3);
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
                            EstadoInicial1.put(ID,3);
                            AuxMenu1.put(ID,0);
                            auxi1.put(ID,0);
                        }else if(AuxMenu==2){
                            RespuestaUs=update.getMessage().getText();
                            try{
                                Montos=Double.parseDouble(RespuestaUs);
                            }catch(Exception e){
                                Montos=-10;
                            }
                            RespaldoDatosDeOpcionUsuario=((Integer)RespaldoDatosDeOpcionUsuario1.get(ID));
                            message.setText(cuentaUs.get(RespaldoDatosDeOpcionUsuario-1).retirar(Montos));
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
                            EstadoInicial1.put(ID,3);
                            AuxMenu1.put(ID,0);
                        }                 
                    ///////////////// QUIERES DEPOSITAR SALE ESTA    
                    }else if(auxi==3||OpcionMenu.equals("C")||OpcionMenu.equals("c")){
                        auxi1.put(ID,3);
                        RespuestaUs=update.getMessage().getText();
                        try{
                            DatosDeOpcionUsuario=Integer.parseInt(RespuestaUs);
                        }catch(Exception e){
                            DatosDeOpcionUsuario=-10;
                        }                        
                        if(AuxMenu==0){
                            if(y==0){
                                auxi1.put(ID,0);
                                AuxMenu1.put(ID, 0);
                                EstadoInicial1.put(ID,3);
                                message.setText("No tienes cuentas, crea un cuenta!!");
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
                            }else{
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
                                EstadoInicial1.put(ID,3);
                                AuxMenu1.put(ID,1);
                            }
                        }else if(AuxMenu==1 && DatosDeOpcionUsuario<=y && DatosDeOpcionUsuario>0){
                            message.setText("Ok, cuanto dinero abonará a la cuenta:");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            RespaldoDatosDeOpcionUsuario1.put(ID,DatosDeOpcionUsuario);
                            AuxMenu1.put(ID,2);
                            EstadoInicial1.put(ID,3);
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
                            EstadoInicial1.put(ID,3);
                            AuxMenu1.put(ID,0);
                            auxi1.put(ID,0);
                        }else if(AuxMenu==2){
                            RespuestaUs=update.getMessage().getText();
                            try{
                                Montos=Double.parseDouble(RespuestaUs);
                            }catch(Exception e){
                                Montos=-10;
                            }
                            RespaldoDatosDeOpcionUsuario=((Integer)RespaldoDatosDeOpcionUsuario1.get(ID));
                            message.setText(cuentaUs.get(RespaldoDatosDeOpcionUsuario-1).depositar(Montos));
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
                            EstadoInicial1.put(ID,3);
                            AuxMenu1.put(ID,0);
                        }
                    /////////// DESEAS AGREGAR UNA CUENTA? SALE ESTA VAIN    
                    }else if(auxi==4||OpcionMenu.equals("d")||OpcionMenu.equals("D")){
                        auxi1.put(ID,4);
                        if(AuxMenu==0){
                            message.setText("Qué tipo de cuenta deseas: \n1.Caja de Ahorros \n2.Cuenta Corriente");
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            EstadoInicial1.put(ID,3);
                            AuxMenu1.put(ID,1);
                        }else if(AuxMenu==1){
                            RespuestaUs=update.getMessage().getText();
                            switch(RespuestaUs){
                                case "1":
                                    RespaldoDatosDeOpcionUsuario1.put(ID,"Caja de ahorros");
                                    EstadoInicial1.put(ID,3);
                                    AuxMenu1.put(ID,2);
                                    message.setText("Okay, que moneda desea: \n1.BOLIVIANOS \n2.DOLARES");
                                    try{
                                        execute(message);
                                    }catch(TelegramApiException e){
                                        e.printStackTrace();
                                    }
                                    break;
                                case "2":
                                    RespaldoDatosDeOpcionUsuario1.put(ID,"Cuenta corriente");
                                    EstadoInicial1.put(ID,3);
                                    AuxMenu1.put(ID,2);
                                    message.setText("Está bien, que tipo de moneda deseas \n1.BOLIVIANOS \n2.DOLARES");
                                    try{
                                        execute(message);
                                    }catch(TelegramApiException e){
                                        e.printStackTrace();
                                    }
                                    break;
                                default:
                                    EstadoInicial1.put(ID,3);
                                    AuxMenu1.put(ID,0);
                                    auxi1.put(ID,0);
                                    message.setText("Opcion inválida");
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
                                    break;
                            }
                        }else{
                            RespuestaUs=update.getMessage().getText();
                            Cuenta cuentanueva;
                            switch(RespuestaUs){
                                case "1":
                                    EstadoInicial1.put(ID,3);
                                    AuxMenu1.put(ID,0);
                                    auxi1.put(ID,0);
                                    cuentanueva=new Cuenta("BOB",((String)RespaldoDatosDeOpcionUsuario1.get(ID)),0.0);
                                    cliente1.agregarCuenta(cuentanueva);
                                    message.setText("Cuenta creada!");
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
                                    break;
                                case "2":
                                    EstadoInicial1.put(ID,3);
                                    AuxMenu1.put(ID,0);
                                    auxi1.put(ID,0);
                                    cuentanueva=new Cuenta("USD",((String)RespaldoDatosDeOpcionUsuario1.get(ID)),0.0);
                                    cliente1.agregarCuenta(cuentanueva);
                                    message.setText("Cuenta creada MarcA!");
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
                                    break;
                                default:
                                    EstadoInicial1.put(ID,3);
                                    AuxMenu1.put(ID,0);
                                    auxi1.put(ID,0);
                                    message.setText("Opcion inválida");
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
                                    break;
                            }
                        }
                    ///////////////////////// CUANDO TE QUIERES IR SALE ESTOS    
                    }else if(OpcionMenu.equals("E") || OpcionMenu.equals("e")){
                        message.setText("Ok, que tengas un buen día");
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                        EstadoInicial1.put(ID,0);
                    //////////////SI LA CAGAS EN ESCOGER SALE ESTOS
                    }else{
                        EstadoInicial1.put(ID,3);                                                
                        message.setText("Opción Incorrecta");
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