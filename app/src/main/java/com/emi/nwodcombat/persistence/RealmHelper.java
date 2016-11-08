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

    public static RealmHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RealmHelper(context);
        }
        return instance;
    }

    private RealmHelper(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(realmConfig);

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

        if (item.getClass().equals(Character.class)) {
            ((Character)item).setComplete(true);
        }

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

    @SuppressWarnings("unchecked")
    public RealmResults<Character> getCompletedCharacterList() {
        return (RealmResults) realm.where(Character.class)
            .equalTo("isComplete", true)
            .findAll();
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

    public boolean updateEntry(Character character, Entry entry) {
        for (Entry cycledEntry : character.getEntries()) {
            if (cycledEntry.getKey().equals(entry.getKey())) {
                realm.beginTransaction();

                cycledEntry.setValue(String.valueOf(entry.getValue()));

                realm.commitTransaction();

                return true;
            }
        }
        return false;

    }

    public void addEntry(Long characterId, String key, String type, String value) {
        Character characterToUpdate = get(Character.class, characterId);

        Entry entry = Entry.newInstance(getLastId(Entry.class))
            .setKey(key)
            .setType(type)
            .setValue(value);

        realm.beginTransaction();
        characterToUpdate.getEntries().add(entry);
        realm.commitTransaction();
    }

    public void addEntry(Long characterId, Entry entry) {
        Character characterToUpdate = get(Character.class, characterId);

        entry.setId(getLastId(Entry.class));

        realm.beginTransaction();
        characterToUpdate.getEntries().add(entry);
        realm.commitTransaction();

//        for (Entry t : character.getEntries()) {
//            if (t.getKey().equals(entry.getKey())) {
//                entry.setId(t.getId());
//
//                character.getEntries().set(character.getEntries().indexOf(t), entry);
//
//                realm.commitTransaction();
//
//                return;
//            }
//        }
//        character.getEntries().add(entry);
//
//        realm.commitTransaction();
    }

    public void updateDemeanorTrait(Long characterId, DemeanorTrait demeanorTrait) {
        Character characterToUpdate = get(Character.class, characterId);

        Long ordinal = demeanorTrait.getOrdinal();
        Demeanor demeanor = demeanorTrait.getDemeanor();

        RealmList<DemeanorTrait> demeanorTraits = characterToUpdate.getDemeanorTraits();

        if (demeanorTraits.size() > 0) {
            for (DemeanorTrait trait : demeanorTraits) {
                if (trait.getOrdinal().equals(ordinal)) {
                    realm.beginTransaction();

                    trait.setDemeanor(demeanor);

                    realm.commitTransaction();

                    break;
                }
            }
        } else {
            realm.beginTransaction();

            demeanorTraits.add(demeanorTrait);

            realm.commitTransaction();
        }
    }

    public void updateNatureTrait(Long characterId, NatureTrait natureTrait) {
        Character characterToUpdate = get(Character.class, characterId);

        Long ordinal = natureTrait.getOrdinal();
        Nature nature = natureTrait.getNature();

        RealmList<NatureTrait> natureTraits = characterToUpdate.getNatureTraits();

        if (natureTraits.size() > 0) {
            for (NatureTrait trait : natureTraits) {
                if (trait.getOrdinal().equals(ordinal)) {
                    realm.beginTransaction();

                    trait.setNature(nature);

                    realm.commitTransaction();

                    break;
                }
            }
        } else {
            realm.beginTransaction();

            natureTraits.add(natureTrait);

            realm.commitTransaction();
        }
    }

    public void updateViceTrait(Long characterId, ViceTrait viceTrait) {
        Character characterToUpdate = get(Character.class, characterId);

        Long ordinal = viceTrait.getOrdinal();
        Vice Vice = viceTrait.getVice();

        RealmList<ViceTrait> viceTraits = characterToUpdate.getViceTraits();

        if (viceTraits.size() > 0) {
            for (ViceTrait trait : viceTraits) {
                if (trait.getOrdinal().equals(ordinal)) {
                    realm.beginTransaction();

                    trait.setVice(Vice);

                    realm.commitTransaction();

                    break;
                }
            }
        } else {
            realm.beginTransaction();

            viceTraits.add(viceTrait);

            realm.commitTransaction();
        }
    }
    
    public void updateVirtueTrait(Long characterId, VirtueTrait virtueTrait) {
        Character characterToUpdate = get(Character.class, characterId);

        Long ordinal = virtueTrait.getOrdinal();
        Virtue Virtue = virtueTrait.getVirtue();

        RealmList<VirtueTrait> virtueTraits = characterToUpdate.getVirtueTraits();

        if (virtueTraits.size() > 0) {
            for (VirtueTrait trait : virtueTraits) {
                if (trait.getOrdinal().equals(ordinal)) {
                    realm.beginTransaction();

                    trait.setVirtue(Virtue);

                    realm.commitTransaction();

                    break;
                }
            }
        } else {
            realm.beginTransaction();

            virtueTraits.add(virtueTrait);

            realm.commitTransaction();
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

    public RealmObject createObject(Class<? extends RealmObject> klass, long id) {
        realm.beginTransaction();

        RealmObject result = realm.createObject(klass, id);

        realm.commitTransaction();

        return result;
    }

    public String getEntryValue(Long characterId, String constant) {
        Character character = realm.where(Character.class).equalTo(Constants.FIELD_ID, characterId)
            .findFirst();

        return character.getEntries().where().equalTo("key", constant).findFirst().getValue();
    }

    public Entry addSpecialty(Long characterId, String key, Entry specialty) {
        Character character = realm.where(Character.class).equalTo(Constants.FIELD_ID, characterId)
            .findFirst();

        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null &&
                entry.getKey().equalsIgnoreCase(key)) {

                realm.beginTransaction();

                if (entry.getExtras() == null) {
                    entry.setExtras(new RealmList<Entry>());
                }

                entry.getExtras().add(specialty);

                realm.commitTransaction();

                return specialty;
            }
        }
        return null;
    }

    public void removeSpecialty(Long characterId, String key, String specialty) {
        Character character = realm.where(Character.class).equalTo(Constants.FIELD_ID, characterId)
            .findFirst();

        for (Entry entry : character.getEntries()) {
            if (entry.getKey() != null &&
                entry.getKey().equalsIgnoreCase(key)) {

                Entry entryToRemove = null;

                for (Entry extra : entry.getExtras()) {
                    if (extra.getValue() != null
                        && extra.getValue().equalsIgnoreCase(specialty)) {
                        entryToRemove = extra;
                        break;
                    }
                }

                if (entryToRemove != null) {
                    realm.beginTransaction();

                    entry.getExtras().remove(entryToRemove);

                    realm.commitTransaction();
                }

                break;
            }
        }
    }

    public long saveCharacter(Character item) {
        realm.beginTransaction();

        item.setComplete(true);

        realm.copyToRealm(item);
        realm.commitTransaction();
        return 0;
    }
}
