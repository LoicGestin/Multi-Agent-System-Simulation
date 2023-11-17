public class MessageEvent extends Event {
    private final String message;

    public MessageEvent(long date, String message) {
        super(date);
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println(this.getDate() + this.message);
    }
}
