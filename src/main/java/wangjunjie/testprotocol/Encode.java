package wangjunjie.testprotocol;

public enum Encode {

    GBK((byte) 0), UTF8((byte) 1);

    private byte value = 1;

    Encode(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }
}
