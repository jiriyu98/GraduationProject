package mbe.source;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.connector.source.DynamicTableSource.DataStructureConverter;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.table.types.logical.LogicalTypeRoot;
import org.apache.flink.types.Row;
import org.apache.flink.types.RowKind;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description: 
 * @ClassName: NewEdgesCsvDeserializer
 * @author: Jiri Yu
 * @date: 2021/4/4 
 */
public class NewEdgesCsvDeserializer implements DeserializationSchema<RowData> {

    private final List<LogicalType> parsingTypes;
    private final DataStructureConverter converter;
    private final TypeInformation<RowData> producedTypeInfo;
    private final String columnDelimiter;

    public NewEdgesCsvDeserializer(List<LogicalType> parsingTypes,
                                   DataStructureConverter converter,
                                   TypeInformation<RowData> producedTypeInfo,
                                   String columnDelimiter) {
        this.parsingTypes = parsingTypes;
        this.converter = converter;
        this.producedTypeInfo = producedTypeInfo;
        this.columnDelimiter = columnDelimiter;
    }

    @Override
    public RowData deserialize(byte[] bytes) throws IOException {
        // parse the columns including a changelog flag
        final String[] columns = new String(bytes).split(Pattern.quote(columnDelimiter));
        final RowKind kind = RowKind.valueOf(columns[0]);
        final Row row = new Row(kind, parsingTypes.size());
        for (int i = 0; i < parsingTypes.size(); i++) {
            row.setField(i, parse(parsingTypes.get(i).getTypeRoot(), columns[i + 1]));
        }
        // convert to internal data structure
        return (RowData) converter.toInternal(row);
    }

    private static Object parse(LogicalTypeRoot root, String value) {
        switch (root) {
            case INTEGER:
                return Integer.parseInt(value);
            case VARCHAR:
                return value;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean isEndOfStream(RowData rowData) {
        return false;
    }

    @Override
    public TypeInformation<RowData> getProducedType() {
        return producedTypeInfo;
    }
}
