package com.solvd.depot.dao;

import com.solvd.depot.models.Model;

public interface IModelDAO extends IBaseDAO<Model>{

    Model getModelByBusId (Long id);


}
