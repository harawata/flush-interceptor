package cz.kvasnickakb.flush.interceptor.example.optimistic;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.executor.flush.FlushResultHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.List;
import java.util.Properties;
import java.util.stream.IntStream;

/**
 * @author : Daniel Kvasnička
 * @inheritDoc
 * @since : 24.02.2021
 **/
@Intercepts({@Signature(type = FlushResultHandler.class, method = "handleResults", args = {})})
public class OptimisticLockingFlushInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object result = invocation.proceed();
        List<BatchResult> batchResults = (List<BatchResult>) result;

        for (BatchResult batchResult : batchResults) {
            final MappedStatement mappedStatement = batchResult.getMappedStatement();

            if (mappedStatement.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
                checkAffectedRows(batchResult);
            }
        }
        return result;
    }

    private void checkAffectedRows(BatchResult batchResult) {
        IntStream.range(0, batchResult.getParameterObjects().size()).forEach(index -> {
            final int affectedRows = batchResult.getUpdateCounts()[index];
            OptimisticLocking.checkOptimisticLocking(affectedRows,
                    batchResult.getParameterObjects().get(index));
        });
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}

