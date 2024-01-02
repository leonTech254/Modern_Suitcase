package com.leonteqsecurity.suitcasenew.Services.Iservice;

import com.leonteqsecurity.suitcasenew.Models.VacationItem;

import java.util.List;

public interface ProductServiceDAO {
    void deleteProduct(String productDescription);
    List<VacationItem> getAlltems();


}
