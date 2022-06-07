package com.solvd.depot.dao;

import com.solvd.depot.models.Type;

public interface ITypeDAO extends IBaseDAO<Type> {
    Type getTypeByBusId(Long id);
}
