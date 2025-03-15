package cz.kvasnickakb.flush.interceptor.example.aspect;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import cz.kvasnickakb.flush.interceptor.example.optimistic.OptimisticLocking;

@Aspect
public class OptimisticLockingAspect {
  @AfterReturning(value = "execution(* org.apache.ibatis.executor.Executor.flushStatements(..))", returning = "batchResults")
  public void getInfo(JoinPoint joinPoint, List<BatchResult> batchResults) {
    System.out.println("Batch result size: " + batchResults.size());
    for (BatchResult batchResult : batchResults) {
      final MappedStatement mappedStatement = batchResult.getMappedStatement();
      if (mappedStatement.getSqlCommandType().equals(SqlCommandType.UPDATE)) {
        System.out.println("Check affected rows...");
        checkAffectedRows(batchResult);
      }
    }
  }

  private void checkAffectedRows(BatchResult batchResult) {
    IntStream.range(0, batchResult.getParameterObjects().size()).forEach(index -> {
      final int affectedRows = batchResult.getUpdateCounts()[index];
      OptimisticLocking.checkOptimisticLocking(affectedRows,
          batchResult.getParameterObjects().get(index));
    });
  }

}
