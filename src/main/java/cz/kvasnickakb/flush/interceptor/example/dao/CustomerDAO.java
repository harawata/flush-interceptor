package cz.kvasnickakb.flush.interceptor.example.dao;

import org.apache.ibatis.annotations.*;

/**
 * @author : Daniel Kvasnička
 * @inheritDoc
 * @since : 24.02.2021
 **/
@Mapper
public interface CustomerDAO {

    @Insert({"INSERT INTO Customer (Id, Name, Version)" +
            " VALUES (" +
            "#{c.id}," +
            "#{c.name}, " +
            "#{c.version} " +
            ")"})
    void insert(@Param("c") Customer customer);

    @Update({"UPDATE Customer " +
            "SET " +
            "Name = #{c.name}, " +
            "Version = Version + 1 " +
            "WHERE Id = #{c.customerId} AND Version = #{c.version} "})
    void update(@Param("c") Customer customer);

    @Select("SELECT Id, Name, Version " +
            "FROM Customer " +
            "WHERE Id = #{id} ")
    @Results(value = {
            @Result(property = "id", column = "Id"),
            @Result(property="name", column = "Name"),
            @Result(property = "version", column = "Version")
    })
    Customer findById(@Param("id") Long id);

}
