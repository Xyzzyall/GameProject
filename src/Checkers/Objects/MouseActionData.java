package Checkers.Objects;

import org.joml.Vector2f;

import java.nio.ByteBuffer;

public class MouseActionData {
    public byte type;
    public Vector2f mousePos;
    public Vector2f mouseLastPos;

    public MouseActionData(byte type, Vector2f mousePos, Vector2f mouseLastPos){
        this.mousePos = mousePos;
        this.mouseLastPos = mouseLastPos;
        this.type = type;
    }

    public MouseActionData(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        type = byteBuffer.get(0);
        mousePos = new Vector2f(byteBuffer.getFloat(1), byteBuffer.getFloat(5));
        mouseLastPos = new Vector2f(byteBuffer.getFloat(9), byteBuffer.getFloat(13));
    }

    public byte[] to_bytes(){
        return ByteBuffer.allocate(1 + 4*2 + 4*2).put(type).putFloat(mousePos.x).putFloat(mousePos.y).putFloat(mouseLastPos.x).putFloat(mouseLastPos.y).array();
    }

}
