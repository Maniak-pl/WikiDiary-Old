package pl.maniak.wikidiary.events;

/**
 * Created by Sony on 2015-11-02.
 */
public class CommandEvent {

    public static final int START = 1;
    public static final int STOP = 3;

    public static final int CLEAR = 10;

    public static final int SHOW_ERROR = 20;

    public static final int SHOW_HEALTH_RESULT = 30;

    public static final int SHOW_VOICE_NOTE = 32;
    public static final int SHOW_VOICE_RESULT = 33;

//    Command Function

    public static final int SHOW_VOICE = 50;
    public static final int SHOW_HEALTH = 51;


    private final String message;
    private final int status;


    public CommandEvent(int status, String message) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

}
