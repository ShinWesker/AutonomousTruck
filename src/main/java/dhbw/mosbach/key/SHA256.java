package dhbw.mosbach.key;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class SHA256 implements IEncryption{
    @Override
    public String encrypt(String text) {
        text = Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
        System.out.println(text);
        return text;
    }
}
