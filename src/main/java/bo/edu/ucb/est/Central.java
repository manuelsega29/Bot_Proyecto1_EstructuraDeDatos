




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
    private Integer RespaldoDatosDeOpcionUsuario=0;      ////
    //// mi Y xD man
    
    
    
  ///     
    @Override
    public String getBotToken() {
        return "1939060923:AAEs9qeCDftGHL-YMx2gbOXCBmvw99M6ciU";
    }
    
    @Override
    public void onUpdateReceived(Update update) {
         Banco bancaAmiga = new Banco(" BOT BANCA ");             
        
        SendMessage message = new SendMessage();// Creo el objeto para enviar un mensaje arrba
        message.setChatId(update.getMessage().getChatId().toString());//Define a quien le vamos a enviar el mensaje
        System.out.println("Llego mensaje: " + update.toString());
        
        
        if(update.hasMessage()) { // Verificamos que tenga mensaje
            System.out.println("PuntO DE CONTROL");
            Long ID=update.getMessage().getChatId();
            Integer EstadoInicial;
            System.out.println("PuntO DE CONTROL");
            if((Integer)EstadoInicial1.get(ID)==null){
                EstadoInicial=0;
                EstadoInicial1.put(ID, 0);
            }else{
                EstadoInicial=(Integer)EstadoInicial1.get(ID);
            }
            System.out.println("PuntO DE CONTROL");
            String EsSocio;
            if((String)EsSocio1.get(ID)==null){
                EsSocio="";
                EsSocio1.put(ID,"");
            }else{
                EsSocio=(String)EsSocio1.get(ID);
            }
            //EstadoInicial=0;
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
                    }else{
                        message.setText("Como es! \nPon tu Pin de Seguirdad para asegurarnos que realmente eres nuestro Cliente");
                        try{
                            execute(message);
                        }catch(TelegramApiException e){
                            e.printStackTrace();
                        }
                        EstadoInicial1.put(ID,2);
                    }
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
                       EsSocio1.put(ID, "si");
                       EstadoInicial1.put(ID,2);
                    }else if(RespuestaUs.equals("B")||RespuestaUs.equals("b")||RespuestaUs.equals("NO")||RespuestaUs.equals("No")||RespuestaUs.equals("no")){
                       message.setText(IntroducirNombre);
                       try{
                           execute(message);
                       }catch(TelegramApiException e){ 
                           e.printStackTrace();
                       }
                       EsSocio1.put(ID, "no");
                       EstadoInicial1.put(ID,2);
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
                    NombreUsuario1.put(ID, update.getMessage().getText());
                    String NombreUsuario = (String)NombreUsuario1.get(ID);
                    message.setText(IntroducirPin);
                       try{
                            execute(message);
                       }catch(TelegramApiException e){ 
                           e.printStackTrace();
                       }
                       EstadoInicial1.put(ID,3);
                    break;
                 /////////////////   
                case 3:
                    Pin1.put(ID, update.getMessage().getText());
                    String Pin=(String)Pin1.get(ID);
                    if(EsSocio.equals("si")){
                        if(bancaAmiga.buscarClientePorPIN(((String)NombreUsuario1.get(ID)),(Pin))==null){   
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
                            EstadoInicial1.put(ID,1);
                         }else{
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            Cliente cliente1=new Cliente(id,pin,vombrw);bancaAmiga.buscarClientePorPIN(((String)NombreUsuario1.get(ID)),((String)Pin1.get(ID)));
                            banca.adddd(cliente1);
                            EstadoInicial1.put(ID,4);
                        } 
                    }else{
                        
                        //// agrgarlo al CLIENTE ???????UUJBIJBUCXC
                        message.setText(MenuInicio);
                        try{
                            execute(message);
                        }catch(TelegramApiException e){ 
                            e.printStackTrace();
                        }
                        EstadoInicial1.put(ID,4);
                    }
                    break;
                ///////////////
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
                    
                    cliente1=bancaAmiga.buscarClientePorPIN(((String)NombreUsuario1.get(ID)),((String)Pin1.get(ID)));
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
                            message.setText("Ok, cuanto dineroo extraerá de esa cuenta:  ");
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
                            message.setText("Ok, cuanto dineroo extraerá de esa cuenta:  ");
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
                        
                        
                        /////////////
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
                    
                    
                ///////////////  
             
            }
        }
        
        
        /*if(update.hasMessage()) { // Verificamos que tenga mensaje
            System.out.println("PuntO DE CONTROL");
            Long ID=update.getMessage().getChatId();
            Integer EstadoInicial;
            System.out.println("PuntO DE CONTROL");
            if((Integer)EstadoInicial1.get(ID)==null){
                EstadoInicial=0;
                EstadoInicial1.put(ID, 0);
            }else{
                EstadoInicial=(Integer)EstadoInicial1.get(ID);
            }
            System.out.println("PuntO DE CONTROL");
            String EsSocio;
            if((String)EsSocio1.get(ID)==null){
                EsSocio="";
                EsSocio1.put(ID,"");
            }else{
                EsSocio=(String)EsSocio1.get(ID);
            }
            //EstadoInicial=0;
            System.out.println("LLEGAMOS a esta parteE");
            switch (EstadoInicial){
                ////////////
                case 0:
                    message.setText(VerificacionSiTienenCuenta);
                    try{
                        execute(message);
                    }catch(TelegramApiException e){
                        e.printStackTrace();
                    }
                    EstadoInicial1.put(ID,1);
                    System.out.println("LLEGAMOS");
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
                       EsSocio1.put(ID, "si");
                       EstadoInicial1.put(ID,2);
                    }else if(RespuestaUs.equals("B")||RespuestaUs.equals("b")||RespuestaUs.equals("NO")||RespuestaUs.equals("No")||RespuestaUs.equals("no")){
                       message.setText(IntroducirNombre);
                       try{
                           execute(message);
                       }catch(TelegramApiException e){ 
                           e.printStackTrace();
                       }
                       EsSocio1.put(ID, "no");
                       EstadoInicial1.put(ID,2);
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
                    NombreUsuario1.put(ID, update.getMessage().getText());
                    String NombreUsuario = (String)NombreUsuario1.get(ID);
                    message.setText(IntroducirPin);
                       try{
                            execute(message);
                       }catch(TelegramApiException e){ 
                           e.printStackTrace();
                       }
                       EstadoInicial1.put(ID,3);
                    break;
                 /////////////////   
                case 3:
                    Pin1.put(ID, update.getMessage().getText());
                    String Pin=(String)Pin1.get(ID);
                    if(EsSocio.equals("si")){
                        if(bancaAmiga.buscarClientePorPIN(((String)NombreUsuario1.get(ID)),(Pin))==null){   
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
                            EstadoInicial1.put(ID,1);
                         }else{
                            message.setText(MenuInicio);
                            try{
                                execute(message);
                            }catch(TelegramApiException e){ 
                                e.printStackTrace();
                            }
                            Cliente cliente1=new Cliente(id,pin,vombrw);bancaAmiga.buscarClientePorPIN(((String)NombreUsuario1.get(ID)),((String)Pin1.get(ID)));
                            banca.adddd(cliente1);
                            EstadoInicial1.put(ID,4);
                        } 
                    }else{
                        
                        //// agrgarlo al CLIENTE ???????UUJBIJBUCXC
                        message.setText(MenuInicio);
                        try{
                            execute(message);
                        }catch(TelegramApiException e){ 
                            e.printStackTrace();
                        }
                        EstadoInicial1.put(ID,4);
                    }
                    break;
                ///////////////
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
                    
                    cliente1=bancaAmiga.buscarClientePorPIN(((String)NombreUsuario1.get(ID)),((String)Pin1.get(ID)));
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
                            message.setText("Ok, cuanto dineroo extraerá de esa cuenta:  ");
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
                            message.setText("Ok, cuanto dineroo extraerá de esa cuenta:  ");
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
                        
                        
                        /////////////
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
                    
                    
                ///////////////  
             
            }
        }*/
    }

    @Override
    public String getBotUsername() {
        return "estructus_manuel_bot";
    }
    
    
    
}