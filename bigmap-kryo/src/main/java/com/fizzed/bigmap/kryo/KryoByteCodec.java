package com.fizzed.bigmap.kryo;

import com.esotericsoftware.kryo.kryo5.Kryo;
import com.esotericsoftware.kryo.kryo5.io.Input;
import com.esotericsoftware.kryo.kryo5.io.Output;
import com.fizzed.bigmap.ByteCodec;

import static com.fizzed.bigmap.ByteCodecs.ZERO_BYTES;

public class KryoByteCodec<K> implements ByteCodec<K> {

    private final Kryo kryo;
    private final Class<K> type;

    public KryoByteCodec(Class<K> type) {
        // NOTE: kryo is apparently not thread-safe, and is also expensive to create
        this.kryo = new Kryo();
        this.kryo.setRegistrationRequired(false);
        this.type = type;
        this.kryo.register(type);
    }

    @Override
    public byte[] serialize(K value) {
        if (value == null) {
            return ZERO_BYTES;
        }
        final Output output = new Output(1024, -1);
        this.kryo.writeObjectOrNull(output, value, this.type);
        return output.toBytes();
    }

    @Override
    public K deserialize(byte[] bytes) {
        // kryo does not allow zero byte arrays to be deserialized, so its null
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        final Input input = new Input(bytes);
        return this.kryo.readObjectOrNull(input, this.type);
    }

}