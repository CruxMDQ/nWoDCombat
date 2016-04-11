package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.greendao.DaoMaster;
import com.emi.nwodcombat.greendao.DaoSession;
import com.emi.nwodcombat.persistence.PersistenceLayer;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.AbstractDao;

@SuppressWarnings("unchecked")
abstract public class BaseController<T extends Object> implements PersistenceLayer {
    private static DaoSession session;
    AbstractDao dao;

    DaoSession getSession(Context context) {
        if (session == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, Constants.DB_NAME, null);
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            session = daoMaster.newSession();
        }
        return session;
    }

    @Override
    public long save(Object item) {
//        return dao.insert(item);
        return dao.insertOrReplace(item);
    }

    @Override
    public List<Object> getList() {
        return dao.loadAll();
    }

    @Override
    public void delete(Object item) {
        dao.delete(item);
    }

    public void deleteById(long id) {
        dao.deleteByKey(id);
    }

    @Override
    public T get(long id) {
        return (T) dao.loadByRowId(id);
    }

    @Override
    public void update(Object item) {
        dao.update(item);
    }

    @Override
    public int getCount(Class className) {
        return 0;
    }

    @Override
    public long save(Class className, String json) {
        // TODO If it works, refer to implementation in RealmHelper
        return 0;
    }

    @Override
    public long save(Class klass, ArrayList<String> jsonObjects) {
        return 0;
    }

    @Override
    public long getLastId(Class className) {
        return 0;
    }

    @Override
    public List getList(Class klass) {
        return null;
    }

    @Override
    public Object get(Class klass, long id) {
        return null;
    }
}
