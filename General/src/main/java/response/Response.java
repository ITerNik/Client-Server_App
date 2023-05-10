package response;

import constants.Messages;

public class Response {
    private Response(String message) {
        this.report = message;
    }
    private final String report;

    public static Response ok(String message) {
        return new Response(Messages.getMessage("response.ok") + ": " + message);
    }
    public static Response error(String message) {
        return new Response(Messages.getMessage("response.error") + ": " + message);
    }
    public String getReport() {
        return report;
    }
}
