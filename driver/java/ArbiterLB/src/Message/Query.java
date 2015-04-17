package Message;

import Util.Operations;

/**
 * Created by amaliujia on 15-4-15.
 */
public class Query extends Message {
    public Query(Operations e, String Key, String Record) {
        super(e, Key, Record);
    }

    public Query(Operations e, String Key) {
        super(e, Key);
    }
}
