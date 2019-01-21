import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class WrapperPacket <T extends Packet> {
    //private T data;
    private Type type;

    WrapperPacket(T data) {
        //this.data = data;
        type = new TypeToken<T>() {}.getType();
    }

//    public T getData() {
//        return data;
//    }

    public Type getType() {
        return type;
    }
}
