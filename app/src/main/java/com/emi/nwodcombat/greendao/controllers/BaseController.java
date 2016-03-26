package com.emi.nwodcombat.greendao.controllers;

import android.content.Context;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.greendao.DaoMaster;
import com.emi.nwodcombat.greendao.DaoSession;

import java.util.List;

import de.greenrobot.dao.AbstractDao;

@SuppressWarnings("unchecked")
abstract public class BaseController<T> {
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

    public long save(T item) {
//        return dao.insert(item);
        return dao.insertOrReplace(item);
    }

    public List<T> getList() {
        return dao.loadAll();
    }

    public void delete(T item) {
        dao.delete(item);
    }

    public T get(long id) {
        return (T) dao.loadByRowId(id);
    }

    public void update(T item) {
        dao.update(item);
    }
}
