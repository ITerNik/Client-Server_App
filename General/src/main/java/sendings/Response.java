package sendings;

import constants.Messages;

import java.io.*;

public class Response implements Serializable {
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

    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        return baos.toByteArray();
    }
}
