package com.solvd.depot.dao;

import com.solvd.depot.models.Category;
import com.solvd.depot.models.Licence;

import java.util.List;

public interface ILicenceDAO extends IBaseDAO<Licence> {

    List<Licence> getAllLicencesByCategoryId(Long id);
    Licence getLicenceByDriverId(Long id);

}
