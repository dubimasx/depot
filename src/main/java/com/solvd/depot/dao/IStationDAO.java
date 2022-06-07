package com.solvd.depot.dao;

import com.solvd.depot.models.Station;

import java.util.List;

public interface IStationDAO extends IBaseDAO<Station> {
    List<Station> getAllStationsByRouteId (Long id);
}
