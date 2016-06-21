package com.emi.nwodcombat.persistence;

import android.content.Context;
import android.util.Log;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.model.realm.wrappers.DemeanorTrait;
import com.emi.nwodcombat.model.realm.wrappers.NatureTrait;
import com.emi.nwodcombat.model.realm.wrappers.ViceTrait;
import com.emi.nwodcombat.model.realm.wrappers.VirtueTrait;
import com.emi.nwodcombat.tools.Constants;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
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

    public static RealmHelper getInstance(Context context) {
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
        } catch (RealmMigrationNeededException e) {
            try {
                Realm.deleteRealm(realmConfig);
                realm = Realm.getInstance(realmConfig);
            } catch (Exception ex) {
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
        realm.copyToRealm(item);
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
        return realm.where(klass).findAll();
    }

    @Override
    public <T extends RealmObject> T get(Class<T> klass, long id) {
        return realm.where(klass).equalTo(Constants.FIELD_ID, id).findFirst();
    }

    @Override
    public int getCount(Class className) {
        return getList(className).size();
    }

    @Override
    public long getLastId(Class className) {
        long key;
        try {
            RealmQuery query = realm.where(className);
            Number max = query.max("id");
            key = max != null ? max.longValue() + 1 : 0;
        } catch (ArrayIndexOutOfBoundsException ex) {
            key = 0;
        }
        return key;
    }

    @Override
    public <T extends RealmObject> void delete(Class<T> clazz, long id) {
        realm.beginTransaction();

        T target = realm.where(clazz).equalTo(Constants.FIELD_ID, id).findAll().first();

        target.deleteFromRealm();

        realm.commitTransaction();
    }

    @Override
    public <T extends RealmObject> T get(long id) {
        return null;
    }

    @Override
    public boolean updateEntry(Long characterId, Long entryId, String value) {
        Character characterToUpdate = get(Character.class, characterId);

        for (Entry cycledEntry : characterToUpdate.getEntries()) {
            if (cycledEntry.getId() == entryId) {
                realm.beginTransaction();

                cycledEntry.setValue(String.valueOf(value));

                realm.commitTransaction();

                return true;
            }
        }

        return false;
    }

    public void addEntry(Long characterId, String key, String type, String value) {
        Character characterToUpdate = get(Character.class, characterId);

        Entry entry = Entry.newInstance()
            .setKey(key)
            .setType(type)
            .setValue(value);

        entry.setId(getLastId(Entry.class));

        realm.beginTransaction();
        characterToUpdate.getEntries().add(entry);
        realm.commitTransaction();
    }

    public int getDemeanorTraitCount() {
        return getCount(DemeanorTrait.class);
    }

    public int getNatureTraitCount() {
        return getCount(NatureTrait.class);
    }

    public int getViceTraitCount() {
        return getCount(ViceTrait.class);
    }

    public int getVirtueTraitCount() {
        return getCount(VirtueTrait.class);
    }

    public void updateDemeanorTrait(Long characterId, DemeanorTrait demeanorTrait) {
        Character characterToUpdate = get(Character.class, characterId);

        Long ordinal = demeanorTrait.getOrdinal();
        Demeanor demeanor = demeanorTrait.getDemeanor();

        for (DemeanorTrait trait : characterToUpdate.getDemeanorTraits()) {
            if (trait.getOrdinal().equals(ordinal)) {
                realm.beginTransaction();

                trait.setDemeanor(demeanor);

                realm.commitTransaction();

                break;
            }
        }
    }

    public void updateNatureTrait(Long characterId, NatureTrait natureTrait) {
        Character characterToUpdate = get(Character.class, characterId);

        Long ordinal = natureTrait.getOrdinal();
        Nature nature = natureTrait.getNature();

        for (NatureTrait trait : characterToUpdate.getNatureTraits()) {
            if (trait.getOrdinal().equals(ordinal)) {
                realm.beginTransaction();

                trait.setNature(nature);

                realm.commitTransaction();

                break;
            }
        }
    }

    public void updateViceTrait(Long characterId, ViceTrait ViceTrait) {
        Character characterToUpdate = get(Character.class, characterId);

        Long ordinal = ViceTrait.getOrdinal();
        Vice Vice = ViceTrait.getVice();

        for (ViceTrait trait : characterToUpdate.getViceTraits()) {
            if (trait.getOrdinal().equals(ordinal)) {
                realm.beginTransaction();

                trait.setVice(Vice);

                realm.commitTransaction();

                break;
            }
        }
    }

    public void updateVirtueTrait(Long characterId, VirtueTrait VirtueTrait) {
        Character characterToUpdate = get(Character.class, characterId);

        Long ordinal = VirtueTrait.getOrdinal();
        Virtue Virtue = VirtueTrait.getVirtue();

        for (VirtueTrait trait : characterToUpdate.getVirtueTraits()) {
            if (trait.getOrdinal().equals(ordinal)) {
                realm.beginTransaction();

                trait.setVirtue(Virtue);

                realm.commitTransaction();

                break;
            }
        }
    }

    public boolean updateEntry(Long characterId, Entry entry) {
        Character characterToUpdate = get(Character.class, characterId);

        RealmList<Entry> entries = characterToUpdate.getEntries();

        for (Entry cycledEntry : entries) {
            if (cycledEntry.getId() == entry.getId()) {
                realm.beginTransaction();

                entries.set(entries.indexOf(cycledEntry), entry);

                realm.commitTransaction();

                return true;
            }
        }

        return false;
    }
}
