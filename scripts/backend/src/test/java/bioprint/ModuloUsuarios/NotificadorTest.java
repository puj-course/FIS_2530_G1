package bioprint.modulousuarios;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import bioprint.modulousuarios.Notificador;

public class NotificadorTest {

    @Test
    void testEnviarMensajeEjecutaExecuteCorrectamente() throws Exception {
        Notificador notificador = spy(new Notificador());
        doReturn(null).when(notificador).execute(any(SendMessage.class)); //  cambio aquí

        notificador.enviarMensaje("Hola Mundo");

        verify(notificador, times(1)).execute(any(SendMessage.class));
    }

    @Test
    void testEnviarMensajeCreaElObjetoCorrectamente() throws Exception {
        Notificador notificador = spy(new Notificador());
        doReturn(null).when(notificador).execute(any(SendMessage.class)); //  cambio aquí

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

}
