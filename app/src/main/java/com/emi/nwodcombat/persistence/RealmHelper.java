package com.emi.nwodcombat.persistence;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.tools.ArrayHelper;
import com.emi.nwodcombat.utils.Constants;

import java.util.List;
import java.util.NoSuchElementException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

/**
 * Created by emiliano.desantis on 07/04/2016.
 */
public class RealmHelper implements PersistenceLayer {
    private static RealmHelper instance;

    private Realm realm;
    private RealmConfiguration realmConfig;

    public static RealmHelper getInstance (Context context) {
        if (instance == null) {
            instance = new RealmHelper(context);
        }
        return instance;
    }

    private RealmHelper(Context context) {
        RealmConfiguration.Builder builder = new RealmConfiguration.Builder(context);
        realmConfig = builder.build();

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

    @Override
    public <T extends RealmObject> long save(T item) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(item);
            realm.commitTransaction();
            return 0;
    }

    public <T extends RealmObject> long save(Class<T> klass, String json) {
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
    public <T extends RealmObject> long save(Class<T> klass, List<String> jsonObjects) {
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

    @Override
    public <T extends RealmObject> RealmResults<T> getList(Class<T> klass) {
        return realm.allObjects(klass);
    }

    @Override
    public <T extends RealmObject> T get(Class<T> klass, long id) {
        return realm.where(klass).equalTo(Constants.FIELD_ID, id).findFirst();
    }

    @Override
    public void update(Object item) {

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

    @Override
    public int getCountVirtue() {
        return getCount(Virtue.class);
    }

    @Override
    public int getCountVice() {
        return getCount(Vice.class);
    }

    @Override
    public int getCountPersonalityArchetype() {
        return getCount(Nature.class);
    }

    @Override
    public void delete(Object item) {
        if (item instanceof Character) {
            realm.beginTransaction();

            Character target = realm.where(Character.class).equalTo(Constants.FIELD_ID, ((Character) item).getId()).findAll().first();

            target.removeFromRealm();

            realm.commitTransaction();
        }
    }

    @Override
    public <T extends RealmObject> T get(long id) {
        return null;
    }

    public void updateCharacter(Character updateInfo) {
        realm.beginTransaction();

        Character characterToUpdate = get(Character.class, updateInfo.getId());

        if (updateInfo.getDemeanors().first() != null) {
            characterToUpdate.getDemeanors().set(0, updateInfo.getDemeanors().first());
        }
        if (updateInfo.getNatures().first() != null) {
            characterToUpdate.getNatures().set(0, updateInfo.getNatures().first());
        }
        if (updateInfo.getVirtues().first() != null) {
            characterToUpdate.getVirtues().set(0, updateInfo.getVirtues().first());
        }
        if (updateInfo.getVices().first() != null) {
            characterToUpdate.getVices().set(0, updateInfo.getVices().first());
        }

        for (Entry entry : updateInfo.getEntries()) {
            try {
                ArrayHelper.findEntry(characterToUpdate.getEntries(), Constants.FIELD_ID)
                    .setValue(String.valueOf(entry.getValue()));
            } catch (NoSuchElementException e) {
                characterToUpdate.getEntries().add(entry);
            }
        }

        realm.commitTransaction();
    }
}
