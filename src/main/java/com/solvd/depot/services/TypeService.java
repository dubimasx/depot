package com.solvd.depot.services;

import com.solvd.depot.dao.IBusDAO;
import com.solvd.depot.dao.jdbcMySqlImpl.BusDAO;
import com.solvd.depot.dao.jdbcMySqlImpl.TypeDAO;
import com.solvd.depot.models.Bus;
import com.solvd.depot.models.Type;

public class TypeService {
    public Type getTypeById (Long id){
        Type type = new Type();
        Bus bus = new Bus();
        BusDAO busDAO = new BusDAO();
        TypeDAO typeDAO = new TypeDAO();
        type = typeDAO.getEntityById(id);
        type.setBuses(busDAO.getALLBusesByTypeId(id));

        return type;
    }
}
