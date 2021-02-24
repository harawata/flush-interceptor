package cz.kvasnickakb.flush.interceptor.example.dao;

import lombok.Data;

/**
 * @author : Daniel Kvasnička
 * @inheritDoc
 * @since : 24.02.2021
 **/
@Data
public class Customer {

    private Long id;
    private String name;
    private Long version;

}
