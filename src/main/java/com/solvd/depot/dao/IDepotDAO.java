package com.solvd.depot.dao;

import com.solvd.depot.models.Depot;

public interface IDepotDAO extends IBaseDAO<Depot> {
    Depot getDepotByBusId (Long id);
}
