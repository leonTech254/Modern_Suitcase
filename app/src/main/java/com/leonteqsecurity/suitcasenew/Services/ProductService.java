package com.leonteqsecurity.suitcasenew.Services;

import android.content.Context;

import com.airbnb.lottie.animation.content.Content;
import com.leonteqsecurity.suitcasenew.Database.DbHelper;
import com.leonteqsecurity.suitcasenew.Models.VacationItem;
import com.leonteqsecurity.suitcasenew.Services.Iservice.ProductServiceDAO;

import java.util.List;

public class ProductService implements ProductServiceDAO {
    DbHelper dbHelper;
    Context content;
    public ProductService(DbHelper _dbHelper,Context _context) {
        this.dbHelper=_dbHelper;
        this.content=_context;
    }
    @Override
    public void deleteProduct(String productDescription) {
        dbHelper.deleteVacationItem(productDescription);
    }

    @Override
    public List<VacationItem> getAlltems() {
        return dbHelper.getAllVacationItems();
    }
}
