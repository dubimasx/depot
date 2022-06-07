package com.solvd.depot.dao;

import com.solvd.depot.models.Bus;

import java.util.List;

public interface IBusDAO extends IBaseDAO<Bus>{
    Bus getBusByDepotId(Long id);
    List<Bus> getALLBusesByTypeId(Long id);
}
