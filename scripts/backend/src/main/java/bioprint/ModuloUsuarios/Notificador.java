package bioprint.ModuloUsuarios;

import java.util.Objects;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Notificador extends TelegramLongPollingBot {
    Long chatId=-4944571794L;
    private final String token;;

    public Notificador() {
        this.token= "8431776515:AAHvy_hCU9ghIKs_Wn3LODq4jM1CKKjc994";
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        /* 
        // Solo responde si recibe texto
        if (update.hasMessage() && update.getMessage().hasText()) {
            String mensaje = update.getMessage().getText();
            Long chatIdNuevo = update.getMessage().getChatId();
            if(!Objects.equals(chatIdNuevo, chatId)){
                chatId=chatIdNuevo;

                SendMessage reply = new SendMessage(chatId.toString(),"Las notificaciones ahora se mandaran a este numero");

                try {
                    execute(reply);
                    System.out.println("Respondí a " + chatId + ": " + mensaje);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
        */
    }

    @Override
    public String getBotUsername() {
        return "MiBotDeEjemplo";
    }

    @Override
    public String getBotToken() {
        return token;
    }
    public void enviarMensaje(String texto) {
    SendMessage mensaje = new SendMessage(chatId.toString(), texto);
    try {
        execute(mensaje);
        System.out.println("✅ Mensaje enviado a " + chatId);
    } catch (TelegramApiException e) {
        System.out.println("no se pudo enviar la notificacion");
    }
}

}
