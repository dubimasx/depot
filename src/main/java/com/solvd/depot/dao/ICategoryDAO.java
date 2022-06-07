package com.solvd.depot.dao;

import com.solvd.depot.models.Category;
import com.solvd.depot.models.Licence;

import java.util.List;

public interface ICategoryDAO extends IBaseDAO<Category>{

    List<Category> getAllCategoriesByLicenceId(Long id);


}
