package cz.kvasnickakb.flush.interceptor.example.optimistic;

import org.apache.ibatis.executor.BatchExecutor;

import javax.persistence.OptimisticLockException;

/**
 * @author : Daniel Kvasnička
 * @inheritDoc
 * @since : 24.02.2021
 **/
public class OptimisticLocking {

    public static void checkOptimisticLocking(int affectedRows, Object params) throws OptimisticLockException, UnsupportedOperationException {
        //if (existVersionFieldInPersistedEntity(params)) {
            if (affectedRows != BatchExecutor.BATCH_UPDATE_RETURN_VALUE) {
                if (affectedRows == 0) {
                    throw new OptimisticLockException("Entity doesn't exists or was concurrently changed!");
                }
                if (affectedRows > 1) {
                    throw new UnsupportedOperationException("Multi-row update isn't supported!");
                }
            }
        //}
    }
}
