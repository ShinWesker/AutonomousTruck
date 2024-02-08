package dhbw.mosbach.key;

public class SHA256 implements IEncryption{
    @Override
    public String encrypt(String text) {
        return "X"+ text + "X";
    }

    @Override
    public String decrypt(String text) {
        return  text.replaceAll("X","");
    }
}
