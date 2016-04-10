package com.emi.nwodcombat.persistence;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public class RealmHelper<T extends RealmObject> implements PersistenceLayer<T> {
    public static RealmHelper instance;

    private Realm realm;
    private RealmConfiguration realmConfig;

    public RealmHelper() {}

    public RealmHelper(Context context) {
        realmConfig = new RealmConfiguration.Builder(context).build();

        try {
            realm = Realm.getInstance(realmConfig);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("RealmHelper", context.getString(R.string.error_message_context_null));
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(realmConfig);
                realm = Realm.getInstance(realmConfig);
            } catch (Exception ex){
                throw ex;
                //No Realm file to remove.
            }
        }
    }

    @Override
    public long save(RealmObject item) {
        try {
            realm.beginTransaction();
            realm.copyToRealm(item);
            realm.commitTransaction();
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

//    /* Sources:
//    http://stackoverflow.com/questions/5571092/convert-object-to-json-in-android
//    http://stackoverflow.com/a/18251429
//     */
//    @Override
//    public long save(Class className, Object... args) {
//        try {
//            Class[] types = new Class[args.length];
//            for (int i = 0; i < types.length; i++) {
//                types[i] = args[i].getClass();
//            }
//
//            Object t = Class.forName(className.getName()).getConstructor(types).newInstance(args);
//
//            Gson gson = new Gson();
//            String json = gson.toJson(t);
//
//            realm.beginTransaction();
//
//            RealmObject object = realm.createObjectFromJson(className, json);
//
//            realm.copyToRealm(object);
//
//            realm.commitTransaction();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public long save(Class klass, String json) {
        try {
            realm.beginTransaction();

            RealmObject object = realm.createObjectFromJson(klass, json);

            realm.copyToRealmOrUpdate(object);

            realm.commitTransaction();

            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public long save(Class klass, ArrayList<String> jsonObjects) {
        try {
            realm.beginTransaction();

            for (String json : jsonObjects) {
                RealmObject object = realm.createObjectFromJson(klass, json);

                realm.copyToRealm(object);
            }

            realm.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public RealmResults getList(Class klass) {
        return realm.allObjects(klass);
    }

    @Override
    public void delete(RealmObject item) {

    }

    @Override
    public T get(long id) {
        return null;
    }

    @Override
    public void update(RealmObject item) {

    }

    @Override
    public int getCount(Class className) {
        return realm.allObjects(className).size();
    }

    @Override
    public long getLastId(Class className) {
        long key;
        try {
            RealmQuery query = realm.where(className);
            Number max = query.max("id");
            key = max != null ? max.longValue() + 1 : 0;
        } catch(ArrayIndexOutOfBoundsException ex) {
            key = 0;
        }
        return key;
    }

    public Realm getRealm() {
        return realm;
    }

    @Override
    public List<T> getList() {
        return null;
    }
}
