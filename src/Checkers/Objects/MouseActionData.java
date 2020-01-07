package Checkers.Objects;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.nio.ByteBuffer;

public class MouseActionData {
    public byte type;
    public Vector2f mousePos;
    public Vector2f mouseLastPos;

    public Vector2i from;
    public Vector2i to;

    public MouseActionData(byte type, Vector2f mousePos, Vector2f mouseLastPos, Vector2i from, Vector2i to){
        this.mousePos = mousePos;
        this.mouseLastPos = mouseLastPos;
        this.type = type;
        this.from = from == null ? new Vector2i(-1, -1): from;
        this.to = to == null ? new Vector2i(-1, -1): to;
    }

    public MouseActionData(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        type = byteBuffer.get(0);
        mousePos = new Vector2f(byteBuffer.getFloat(1), byteBuffer.getFloat(5));
        mouseLastPos = new Vector2f(byteBuffer.getFloat(9), byteBuffer.getFloat(13));
        from = new Vector2i(byteBuffer.getInt(17), byteBuffer.getInt(21));
        to = new Vector2i(byteBuffer.getInt(25), byteBuffer.getInt(29));
    }

    public byte[] to_bytes(){
        return ByteBuffer.allocate(64).put(type).putFloat(mousePos.x).putFloat(mousePos.y).putFloat(mouseLastPos.x).putFloat(mouseLastPos.y).putInt(from.x).putInt(from.y).putInt(to.x).putInt(to.y).array();
    }

}
