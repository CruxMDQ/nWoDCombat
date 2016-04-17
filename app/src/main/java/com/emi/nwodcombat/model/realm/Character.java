package com.emi.nwodcombat.model.realm;

import com.emi.nwodcombat.Constants;
import com.emi.nwodcombat.tools.ArrayHelper;

import java.util.NoSuchElementException;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by emiliano.desantis on 05/04/2016.
 */
public class Character extends RealmObject {
    @PrimaryKey
    private Long id;

    private RealmList<POJOField> pojoFields = new RealmList<>();
    private RealmList<PersonalityArchetype> personalityTraits = new RealmList<>();
    private RealmList<Vice> vices = new RealmList<>();
    private RealmList<Virtue> virtues = new RealmList<>();

    public RealmList<PersonalityArchetype> getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(RealmList<PersonalityArchetype> personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public String getName() {
        // TODO This solution requires Java 8 support. Test it once it's available.
//        List<POJOField> result = Stream.of(getPojoFields())
//                .filter(a -> Objects.equals(a.getKey(), Constants.CHARACTER_NAME))
//                .collect(Collectors.toList());
//        if (result.size() > 0) return result.get(0).getValue();
//        else return name;
        return (String) ArrayHelper.find(pojoFields, Constants.CHARACTER_NAME);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RealmList<Vice> getVices() {
        return vices;
    }

    public void setVices(RealmList<Vice> vices) {
        this.vices = vices;
    }

    public RealmList<Virtue> getVirtues() {
        return virtues;
    }

    public void setVirtues(RealmList<Virtue> virtues) {
        this.virtues = virtues;
    }

    public RealmList<POJOField> getPojoFields() {
        return pojoFields;
    }

    public void setPojoFields(RealmList<POJOField> pojoFields) {
        this.pojoFields = pojoFields;
    }
}
