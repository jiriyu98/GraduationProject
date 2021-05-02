package mbe.utils;

/**
 * @description: Constants for all classes
 *
 * @className: Constants
 * @author: Jiri Yu
 * @date: 2021/5/2
 */
public class Constants {
    // bound & batch size for optimization in DelMap in real-time computing
    public static final long boundSize = Long.MAX_VALUE / 2;
    public static final long batchSize = 5L;

    // source batch size for offline computing
    public static final int batchSourceSize = 10;
    public static final int offlineSourceParallelism = 5;
}
