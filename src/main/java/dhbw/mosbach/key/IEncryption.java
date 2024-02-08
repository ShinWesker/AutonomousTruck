package dhbw.mosbach.key;

public interface IEncryption {
    public String encrypt(String text);
    public String decrypt(String text);
}
