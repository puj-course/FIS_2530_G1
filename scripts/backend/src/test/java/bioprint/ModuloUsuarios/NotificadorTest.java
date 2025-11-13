package bioprint.modulousuarios;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class NotificadorTest {

    @Test
    void testEnviarMensajeEjecutaExecuteCorrectamente() throws Exception {
        Notificador notificador = spy(new Notificador());
        doReturn(null).when(notificador).execute(any(SendMessage.class));

        notificador.enviarMensaje("Hola Mundo");

        verify(notificador, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void testEnviarMensajeCreaElObjetoCorrectamente() throws Exception {
        Notificador notificador = spy(new Notificador());
        doReturn(null).when(notificador).execute(any(SendMessage.class));

        notificador.enviarMensaje("Mensaje de prueba");

        ArgumentCaptor<SendMessage> captor = ArgumentCaptor.forClass(SendMessage.class);
        verify(notificador).execute(captor.capture());

        SendMessage enviado = captor.getValue();
        assertEquals("-4944571794", enviado.getChatId());
        assertEquals("Mensaje de prueba", enviado.getText());
    }

    @Test
    void testEnviarMensajeLanzaExcepcionTelegramApiException() throws Exception {
        Notificador notificador = spy(new Notificador());
        doThrow(new TelegramApiException("Error simulado"))
                .when(notificador).execute(any(SendMessage.class));

        assertDoesNotThrow(() -> notificador.enviarMensaje("Prueba de error"));
    }

    //  Nuevo test: getBotUsername
    @Test
    void testGetBotUsername() {
        Notificador notificador = new Notificador();
        assertEquals("MiBotDeEjemplo", notificador.getBotUsername());
    }

    //  Nuevo test: getBotToken
    @Test
    void testGetBotToken() {
        Notificador notificador = new Notificador();
        assertNotNull(notificador.getBotToken());
        assertEquals("8431776515:AAHvy_hCU9ghIKs_Wn3LODq4jM1CKKjc994", notificador.getBotToken());
    }

    //  Nuevo test: onUpdateReceived con nuevo chatId (actualiza y ejecuta)
    @Test
    void testOnUpdateReceivedConNuevoChatId() throws Exception {
        Notificador notificador = spy(new Notificador());

        // Mock de objetos Update y Message
        Update update = mock(Update.class);
        Message mensaje = mock(Message.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(mensaje);
        when(mensaje.hasText()).thenReturn(true);
        when(mensaje.getText()).thenReturn("Hola bot");
        when(mensaje.getChatId()).thenReturn(12345L); // distinto del chatId actual

        doReturn(null).when(notificador).execute(any(SendMessage.class));

        notificador.onUpdateReceived(update);

        // Verifica que se haya ejecutado el envío de mensaje
        verify(notificador, times(1)).execute(any(SendMessage.class));
    }

    //  Nuevo test: onUpdateReceived con mismo chatId (no ejecuta mensaje)
    @Test
    void testOnUpdateReceivedConMismoChatId() throws Exception {
        Notificador notificador = spy(new Notificador());

        Update update = mock(Update.class);
        Message mensaje = mock(Message.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(mensaje);
        when(mensaje.hasText()).thenReturn(true);
        when(mensaje.getText()).thenReturn("Hola bot");
        when(mensaje.getChatId()).thenReturn(-4944571794L); // mismo chatId

        notificador.onUpdateReceived(update);

        // No debe ejecutar ningún envío
        verify(notificador, never()).execute(any(SendMessage.class));
    }

    //  Nuevo test: onUpdateReceived sin mensaje de texto 
    @Test
    void testOnUpdateReceivedSinTexto() {
        Notificador notificador = new Notificador();

        Update update = mock(Update.class);
        when(update.hasMessage()).thenReturn(false);

        // No debería lanzar excepciones
        assertDoesNotThrow(() -> notificador.onUpdateReceived(update));
    }
}

