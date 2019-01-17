package pl.maniak.wikidiary.utils.events;

public class CommandEvent {

    public static final int START = 1;
    public static final int STOP = 3;

    public static final int CLEAR = 10;
    public static final int REFRESH = 11;

    public static final int SHOW_ERROR = 20;

    public static final int SHOW_HEALTH_RESULT = 30;

    public static final int SHOW_VOICE_NOTE = 32;
    public static final int SHOW_VOICE_RESULT = 33;

//    Command Function

    public static final int SHOW_VOICE = 50;
    public static final int SHOW_HEALTH = 51;
    public static final int SHOW_ADD_TAG = 52;


    private final int event;
    private final String message;

    public CommandEvent(int event) {
        this(event, "");
    }

    public CommandEvent(int event, String message) {
        this.event = event;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getEvent() {
        return event;
    }
}
