package mbe.source;

import org.apache.flink.configuration.ConfigOption;
import org.apache.flink.table.connector.source.DynamicTableSource;
import org.apache.flink.table.factories.DynamicTableSourceFactory;

import java.util.Set;

/**
 * @Description: Data source factory for new edges
 * @ClassName: BipartiteGraphSourceFactory
 * @author: Jiri Yu
 * @date: 2021/3/23 
 */
public class BipartiteGraphSourceFactory implements DynamicTableSourceFactory {
    @Override
    public DynamicTableSource createDynamicTableSource(Context context) {
        return null;
    }

    @Override
    public String factoryIdentifier() {
        return "BipartiteGraphSourceFactory";
    }

    @Override
    public Set<ConfigOption<?>> requiredOptions() {
        return null;
    }

    @Override
    public Set<ConfigOption<?>> optionalOptions() {
        return null;
    }
}
