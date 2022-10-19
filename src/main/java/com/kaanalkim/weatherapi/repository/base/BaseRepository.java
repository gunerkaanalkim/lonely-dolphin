package com.kaanalkim.weatherapi.repository.base;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, PK> extends CrudRepository<T, PK> {
}
