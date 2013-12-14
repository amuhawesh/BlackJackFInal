public interface IController {
    public String getInput(String message, Hand hand) throws Exception;

    public void sendOutput(String message);
}
