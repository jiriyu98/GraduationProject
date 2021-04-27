package mbe.source;

import mbe.common.Edge;
import mbe.utils.SerializableUtils;
import org.apache.flink.api.common.io.DelimitedInputFormat;

import java.io.IOException;

/**
 * @description: Customized TextInputFormat, to adapt deserializable POJO by jackson.
 *
 * @className: CustomizedTextInputFormat
 * @author: Jiri Yu
 * @date: 2021/4/26
 */
public class CustomizedTextInputFormat extends DelimitedInputFormat<Edge> {

    @Override
    public Edge readRecord(Edge edge, byte[] bytes, int offset, int numBytes) throws IOException {
        return SerializableUtils.deserializePojo(new String(bytes, offset, numBytes), Edge.class);
    }
}
