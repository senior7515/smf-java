// Copyright 2019 SMF Authors
//

// automatically generated by the FlatBuffers compiler, do not modify

package example.demo;

import com.google.flatbuffers.*;
import java.lang.*;
import java.nio.*;

@SuppressWarnings("unused")
public final class Request extends Table {
  public static Request
  getRootAsRequest(ByteBuffer _bb) {
    return getRootAsRequest(_bb, new Request());
  }
  public static Request
  getRootAsRequest(ByteBuffer _bb, Request obj) {
    _bb.order(ByteOrder.LITTLE_ENDIAN);
    return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb));
  }
  public void
  __init(int _i, ByteBuffer _bb) {
    bb_pos = _i;
    bb = _bb;
  }
  public Request
  __assign(int _i, ByteBuffer _bb) {
    __init(_i, _bb);
    return this;
  }

  public String
  name() {
    int o = __offset(4);
    return o != 0 ? __string(o + bb_pos) : null;
  }
  public ByteBuffer
  nameAsByteBuffer() {
    return __vector_as_bytebuffer(4, 1);
  }
  public ByteBuffer
  nameInByteBuffer(ByteBuffer _bb) {
    return __vector_in_bytebuffer(_bb, 4, 1);
  }

  public static int
  createRequest(FlatBufferBuilder builder, int nameOffset) {
    builder.startObject(1);
    Request.addName(builder, nameOffset);
    return Request.endRequest(builder);
  }

  public static void
  startRequest(FlatBufferBuilder builder) {
    builder.startObject(1);
  }
  public static void
  addName(FlatBufferBuilder builder, int nameOffset) {
    builder.addOffset(0, nameOffset, 0);
  }
  public static int
  endRequest(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
}
