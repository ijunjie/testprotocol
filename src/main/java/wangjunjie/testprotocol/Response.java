package wangjunjie.testprotocol;

public class Response {
    private final byte encode;
    private final String response;
    private final int responseLength;

    private Response(Builder builder) {
        this.encode = builder.encode;
        this.response = builder.response;
        this.responseLength = builder.responseLength;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private byte encode;
        private String response;
        private int responseLength;

        Builder() {
        }

        Builder encode(byte encode) {
            this.encode = encode;
            return this;
        }

        Builder response(String response) {
            this.response = response;
            this.responseLength = response != null ? response.length() : 0;
            return this;
        }

        Builder responseLength(int responseLength) {
            this.responseLength = responseLength;
            return this;
        }


        Response build() {
            return new Response(this);
        }
    }

    public byte getEncode() {
        return encode;
    }


    public String getResponse() {
        return response;
    }


    public int getResponseLength() {
        return responseLength;
    }

}
