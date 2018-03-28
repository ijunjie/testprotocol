package wangjunjie.testprotocol;

public class Request {
    private final byte encode;
    private final String command;
    private final int commandLength;

    public Request(byte encode, String command, int commandLength) {
        this.encode = encode;
        this.command = command;
        this.commandLength = commandLength;
    }

    public byte getEncode() {
        return encode;
    }


    public String getCommand() {
        return command;
    }


    public int getCommandLength() {
        return commandLength;
    }

}
