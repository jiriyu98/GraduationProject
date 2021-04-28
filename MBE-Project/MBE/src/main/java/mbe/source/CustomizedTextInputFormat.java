package mbe.source;

import mbe.common.Edge;
import mbe.utils.SerializableUtils;
import org.apache.flink.api.common.io.DelimitedInputFormat;

import java.io.IOException;

/**
 * @description: Customized TextInputFormat, to adapt deserializable POJO by jackson.
 * Please be attention: this class will warn when other read thread has already completed reading file.
 *
 * @className: CustomizedTextInputFormat
 * @author: Jiri Yu
 * @date: 2021/4/26
 */
public class CustomizedTextInputFormat extends DelimitedInputFormat<Edge> {
    private static final long serialVersionUID = 1L;

    @Override
    public Edge nextRecord(Edge record) throws IOException {
        if(this.reachedEnd()){
            return null;
        }
        Edge returnRecord = null;

        do {
            returnRecord = super.nextRecord(record);
        } while(returnRecord == null && !this.reachedEnd());

        return returnRecord;
    }

    @Override
    public Edge readRecord(Edge edge, byte[] bytes, int offset, int numBytes) throws IOException {
        return SerializableUtils.deserializePojo(new String(bytes, offset, numBytes), Edge.class);
    }
}
