package Hashing;

import Interface.HashFunction;

/**
 * Created by amaliujia on 15-5-7.
 */
public class BuiltinHashFunction implements HashFunction {
    @Override
    public long hashString(String i) {
        return i.hashCode();
    }
}
