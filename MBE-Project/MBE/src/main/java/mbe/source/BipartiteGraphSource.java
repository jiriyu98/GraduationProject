//package mbe.source;
//
//import mbe.common.Edge;
//import org.apache.flink.api.common.serialization.DeserializationSchema;
//import org.apache.flink.streaming.api.functions.source.SourceFunction;
//import org.apache.flink.table.connector.ChangelogMode;
//import org.apache.flink.table.connector.format.DecodingFormat;
//import org.apache.flink.table.connector.source.DynamicTableSource;
//import org.apache.flink.table.connector.source.ScanTableSource;
//import org.apache.flink.table.connector.source.SourceFunctionProvider;
//import org.apache.flink.table.data.RowData;
//import org.apache.flink.table.types.DataType;
//
///**
// * @Description: Data source
// * @ClassName: BipartiteGraphSource
// * @author: Jiri Yu
// * @date: 2021/4/2
// */
//public class BipartiteGraphSource implements ScanTableSource {
//    private final long verticesNumX;
//    private final long verticesNumY;
//    private final byte byteDelimiter;
//    private final DecodingFormat<DeserializationSchema<RowData>> decodingFormat;
//    private final DataType producedDataType;
//
//    public BipartiteGraphSource(long verticesNumX,
//                                long verticesNumY){
//        this.verticesNumX = verticesNumX;
//        this.verticesNumY = verticesNumY;
//        this.byteDelimiter = 0;
//        this.decodingFormat = null;
//        this.producedDataType = null;
//    }
//
//    public BipartiteGraphSource(long verticesNumX,
//                                long verticesNumY,
//                                byte byteDelimiter,
//                                DecodingFormat<DeserializationSchema<RowData>> decodingFormat,
//                                DataType producedDataType){
//        this.verticesNumX = verticesNumX;
//        this.verticesNumY = verticesNumY;
//        this.byteDelimiter = byteDelimiter;
//        this.decodingFormat = decodingFormat;
//        this.producedDataType = producedDataType;
//    }
//
//    @Override
//    public DynamicTableSource copy() {
//        return new BipartiteGraphSource(verticesNumX, verticesNumY, byteDelimiter, decodingFormat, producedDataType);
//    }
//
//    @Override
//    public String asSummaryString() {
//        return "Bipartite Graph Source";
//    }
//
//    @Override
//    public ChangelogMode getChangelogMode() {
//        return null;
//    }
//
//    @Override
//    public ScanRuntimeProvider getScanRuntimeProvider(ScanContext scanContext) {
//        final DeserializationSchema<RowData> deserializer = decodingFormat.createRuntimeDecoder(
//                scanContext,
//                producedDataType);
//
//        final SourceFunction<Edge> sourceFunction = new BipartiteGraphSourceFunctionRandom(
//                verticesNumX,
//                verticesNumY);
//
//        return SourceFunctionProvider.of(sourceFunction, false);
//    }
//}
