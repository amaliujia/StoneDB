package Hashing;

import Interface.HashFunction;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by amaliujia on 15-5-7.
 */
public class BuiltinHashFunction implements HashFunction {
    @Override
    public long hashString(String s) {

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(s.getBytes());
            String encryptedString = new String(messageDigest.digest());
            return encryptedString.hashCode();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Long.parseLong(null);
    }
}
