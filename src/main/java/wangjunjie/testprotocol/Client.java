package wangjunjie.testprotocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        String command = "HELLO2";
        Request request = new Request(Encode.UTF8.getValue(), command, command.length());

        System.out.println("commandlenght: " + request.getCommandLength());
        System.out.println("command: " + request.getCommand());

        Socket client = new Socket("127.0.0.1", 4567);
        OutputStream output = client.getOutputStream();
        writeRequest(output, request);

        InputStream input = client.getInputStream();
        Response response = readResponse(input);
        client.shutdownOutput();

        System.out.println("responselength : " + response.getResponseLength());
        System.out.println("response : " + response.getResponse());

    }

    private static void writeRequest(OutputStream output, Request request) throws IOException {
        output.write(request.getEncode());
        output.write(ByteUtil.int2ByteArray(request.getCommandLength()));

        output.write(request.getCommand().getBytes(Encode.GBK.getValue() == request.getEncode()
                ? "GBK"
                : "UTF-8"));

        output.flush();
    }

    private static Response readResponse(InputStream input) throws IOException {
        byte[] encodeByte = new byte[1];
        input.read(encodeByte);
        byte encode = encodeByte[0];

        byte[] responseLengthByte = new byte[4];
        input.read(responseLengthByte);
        int responseLength = ByteUtil.bytes2Int(responseLengthByte);

        byte[] responseBytes = new byte[responseLength];
        input.read(responseBytes);
        String responseStr = new String(responseBytes, Encode.GBK.getValue() == encode ? "GBK" : "UTF-8");

        Response response = Response.builder()
                .encode(encode)
                .response(responseStr)
                .responseLength(responseLength)
                .build();

        return response;
    }
}
