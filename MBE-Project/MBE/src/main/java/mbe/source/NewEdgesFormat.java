package mbe.source;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.table.connector.ChangelogMode;
import org.apache.flink.table.connector.format.DecodingFormat;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.connector.source.DynamicTableSource.DataStructureConverter;
import org.apache.flink.table.data.RowData;
import org.apache.flink.table.types.DataType;
import org.apache.flink.table.types.logical.LogicalType;
import org.apache.flink.types.RowKind;

import java.util.List;

/**
 * @Description:
 * @ClassName: NewEdgesFormat
 * @author: Jiri Yu
 * @date: 2021/4/4
 */
public class NewEdgesFormat implements DecodingFormat<DeserializationSchema<RowData>> {

    private final String columnDelimiter;

    public NewEdgesFormat(String columnDelimiter) {
        this.columnDelimiter = columnDelimiter;
    }

    @Override
    @SuppressWarnings("unchecked")
    public DeserializationSchema<RowData> createRuntimeDecoder(DynamicTableSource.Context context, DataType dataType) {
        final TypeInformation<RowData> producedTypeInfo =  context.createTypeInformation(dataType);
        final DataStructureConverter converter = context.createDataStructureConverter(dataType);
        final List<LogicalType> parsingTypes = dataType.getLogicalType().getChildren();

        return new NewEdgesCsvDeserializer(parsingTypes, converter, producedTypeInfo, columnDelimiter);
    }

    @Override
    public ChangelogMode getChangelogMode() {
        return ChangelogMode.newBuilder()
                .addContainedKind(RowKind.INSERT)
                .build();
    }
}
