package wangjunjie.testprotocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(4567);
        while (true) {
            Socket client = server.accept();

            InputStream inputStream = client.getInputStream();
            Request request = readRequest(inputStream);

            OutputStream output = client.getOutputStream();
            Response response = Response.builder()
                    .encode(Encode.UTF8.getValue())
                    .response(request.getCommand().equals("HELLO")
                            ? "hello!"
                            : "bye bye!")
                    .build();
            writeResponse(output, response);
        }
    }

    private static Request readRequest(InputStream input) throws IOException {
        byte[] encodeByte = new byte[1];
        input.read(encodeByte);
        byte encode = encodeByte[0];

        byte[] commandLengthBytes = new byte[4];
        input.read(commandLengthBytes);
        int commandLength = ByteUtil.bytes2Int(commandLengthBytes);

        byte[] commandBytes = new byte[commandLength];
        input.read(commandBytes);

        String command = new String(commandBytes, encode == Encode.GBK.getValue()
                ? "GBK"
                : "UTF-8");

        return new Request(encode, command, commandLength);
    }

    public static void writeResponse(OutputStream output, Response response) throws IOException {
        output.write(response.getEncode());
        //output.write(response.getResponseLength()); 会截取低8位传输，丢弃高24位
        output.write(ByteUtil.int2ByteArray(response.getResponseLength()));
        output.write(response.getResponse().getBytes(Encode.GBK.getValue() == response.getEncode()
                ? "GBK"
                : "UTF-8"));

        output.flush();

    }

}
