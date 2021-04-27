package mbe.source;

import org.apache.flink.api.java.io.CsvInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;

import java.io.IOException;
import java.io.Serializable;

/**
 * @description: read source data from file
 *
 * @className: BipartiteGraphSourceFunctionFromFile
 * @author: Jiri Yu
 * @date: 2021/4/4 
 */
public class BipartiteGraphSourceFunctionFromFile extends CsvInputFormat {
    protected BipartiteGraphSourceFunctionFromFile(Path filePath) {
        super(filePath);
    }

    @Override
    protected Object fillRecord(Object o, Object[] objects) {
        return null;
    }

    @Override
    public void reopen(InputSplit inputSplit, Serializable serializable) throws IOException {

    }

    @Override
    public InputSplitAssigner getInputSplitAssigner(InputSplit[] inputSplits) {
        return null;
    }

    @Override
    public void open(InputSplit inputSplit) throws IOException {

    }
}
