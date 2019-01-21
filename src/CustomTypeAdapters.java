import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public final class CustomTypeAdapters {
    public static final class WrapperPacketAdapter extends TypeAdapter<WrapperPacket> {
        @Override
        public void write(JsonWriter jsonWriter, WrapperPacket wrapperPacket) throws IOException {

        }

        @Override
        public WrapperPacket read(JsonReader reader) throws IOException {
//            WrapperPacket wrapperPacket = new WrapperPacket();
//            reader.beginObject();
//            String fieldname = null;
//
//            while (reader.hasNext()) {
//                JsonToken token = reader.peek();
//
//                if (token.equals(JsonToken.NAME)) {
//                    //get the current token
//                    fieldname = reader.nextName();
//                }
//
//                if ("name".equals(fieldname)) {
//                    //move to next token
//                    token = reader.peek();
//                    wrapperPacket.setName(reader.nextString());
//                }
//
//                if("rollNo".equals(fieldname)) {
//                    //move to next token
//                    token = reader.peek();
//                    student.setRollNo(reader.nextInt());
//                }
//            }
            return null;
        }
    }
}
