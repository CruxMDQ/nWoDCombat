package com.emi.nwodcombat.charactercreator.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.activities.NavDrawerActivity;
import com.emi.nwodcombat.charactercreator.interfaces.PagerFinisher;
import com.emi.nwodcombat.charactercreator.interfaces.PagerStep;
import com.emi.nwodcombat.model.realm.Character;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.POJOField;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.persistence.PersistenceLayer;
import com.emi.nwodcombat.persistence.RealmHelper;
import com.emi.nwodcombat.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Emi on 3/10/16.
 */
public class SummaryStep extends WizardStep implements PagerStep.ChildStep, PagerFinisher {
    @Bind(R.id.txtSummaryAttrMental) TextView txtSummaryAttrMental;
    @Bind(R.id.txtSummaryAttrPhysical) TextView txtSummaryAttrPhysical;
    @Bind(R.id.txtSummaryAttrSocial) TextView txtSummaryAttrSocial;
    @Bind(R.id.txtSummarySkillsMental) TextView txtSummarySkillsMental;
    @Bind(R.id.txtSummarySkillsPhysical) TextView txtSummarySkillsPhysical;
    @Bind(R.id.txtSummarySkillsSocial) TextView txtSummarySkillsSocial;

    private Integer intelligence, wits, resolve;
    private Integer strength, dexterity, stamina;
    private Integer presence, manipulation, composure;

    private Integer academics, computer, crafts, investigation, medicine, occult, politics, science;
    private Integer athletics, brawl, drive, firearms, larceny, stealth, survival, weaponry;
    private Integer animalKen, empathy, expression, intimidation, persuasion, socialize, streetwise, subterfuge;

    private String characterName, characterConcept, characterPlayer;

    private Long characterVirtue, characterVice, characterDemeanor, characterNature;

    private Long lastPOJOFieldId;

    private PersistenceLayer helper;

    private Character character;

    public SummaryStep() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(
            getLayout(), container, false);

        ButterKnife.bind(this, view);

        helper = RealmHelper.getInstance(getActivity());

        character = new Character();

        lastPOJOFieldId = helper.getLastId(POJOField.class);

        return view;
    }

    private void setUpCharacter() {
        addFieldToCharacter(Constants.CHARACTER_NAME, characterName);
        addFieldToCharacter(Constants.CHARACTER_PLAYER, characterPlayer);
        addFieldToCharacter(Constants.CHARACTER_CONCEPT, characterConcept);

        addFieldToCharacter(Constants.ATTR_INT, intelligence);
        addFieldToCharacter(Constants.ATTR_WIT, wits);
        addFieldToCharacter(Constants.ATTR_RES, resolve);

        addFieldToCharacter(Constants.ATTR_STR, strength);
        addFieldToCharacter(Constants.ATTR_DEX, dexterity);
        addFieldToCharacter(Constants.ATTR_STA, stamina);

        addFieldToCharacter(Constants.ATTR_PRE, presence);
        addFieldToCharacter(Constants.ATTR_MAN, manipulation);
        addFieldToCharacter(Constants.ATTR_COM, composure);

        addFieldToCharacter(Constants.SKILL_ACADEMICS, academics);
        addFieldToCharacter(Constants.SKILL_COMPUTER, computer);
        addFieldToCharacter(Constants.SKILL_CRAFTS, crafts);
        addFieldToCharacter(Constants.SKILL_INVESTIGATION, investigation);
        addFieldToCharacter(Constants.SKILL_MEDICINE, medicine);
        addFieldToCharacter(Constants.SKILL_OCCULT, occult);
        addFieldToCharacter(Constants.SKILL_POLITICS, politics);
        addFieldToCharacter(Constants.SKILL_SCIENCE, science);

        addFieldToCharacter(Constants.SKILL_ATHLETICS, athletics);
        addFieldToCharacter(Constants.SKILL_BRAWL, brawl);
        addFieldToCharacter(Constants.SKILL_DRIVE, drive);
        addFieldToCharacter(Constants.SKILL_FIREARMS, firearms);
        addFieldToCharacter(Constants.SKILL_LARCENY, larceny);
        addFieldToCharacter(Constants.SKILL_STEALTH, stealth);
        addFieldToCharacter(Constants.SKILL_SURVIVAL, survival);
        addFieldToCharacter(Constants.SKILL_WEAPONRY, weaponry);

        addFieldToCharacter(Constants.SKILL_ANIMAL_KEN, animalKen);
        addFieldToCharacter(Constants.SKILL_EMPATHY, empathy);
        addFieldToCharacter(Constants.SKILL_EXPRESSION, expression);
        addFieldToCharacter(Constants.SKILL_INTIMIDATION, intimidation);
        addFieldToCharacter(Constants.SKILL_PERSUASION, persuasion);
        addFieldToCharacter(Constants.SKILL_SOCIALIZE, socialize);
        addFieldToCharacter(Constants.SKILL_STREETWISE, streetwise);
        addFieldToCharacter(Constants.SKILL_SUBTERFUGE, subterfuge);

        character.setId(helper.getLastId(Character.class));

        Demeanor demeanor = helper.get(Demeanor.class, characterDemeanor);
        Nature nature = helper.get(Nature.class, characterNature);
        Vice vice = helper.get(Vice.class, characterVice);
        Virtue virtue = helper.get(Virtue.class, characterVirtue);

        character.getDemeanors().add(demeanor);
        character.getNatures().add(nature);
        character.getVices().add(vice);
        character.getVirtues().add(virtue);
    }

    private void addFieldToCharacter(String key, String value) {
        character.getPojoFields().add(new POJOField().setId(lastPOJOFieldId)
            .setKey(key).setType(Constants.FIELD_TYPE_STRING).setValue(value));

        lastPOJOFieldId++;
    }

    private void addFieldToCharacter(String key, String type, String value) {
        character.getPojoFields().add(new POJOField().setId(lastPOJOFieldId)
            .setKey(key).setType(type).setValue(value));

        lastPOJOFieldId++;
    }

    private void addFieldToCharacter(String key, Integer value) {
        character.getPojoFields().add(new POJOField().setId(lastPOJOFieldId)
            .setKey(key).setType(Constants.FIELD_TYPE_INTEGER).setValue(String.valueOf(value)));

        lastPOJOFieldId++;
    }

    @Override
    public void setUserVisibleHint(boolean isVisible) {
        super.setUserVisibleHint(isVisible);

        if (isVisible) {
            retrieveChoices();

            setUpCharacter();

            pagerMaster.checkStepIsComplete(true, this);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpUI();
    }

    @Override
    protected void setUpUI() { }

    @Override
    public void checkCompletionConditions() {
        pagerMaster.checkStepIsComplete(!hasLeftoverPoints(), this);
    }

    @Override
    public HashMap<String, Object> saveChoices() {
        return null;
    }

    @Override
    public String getToolbarTitle() {
        return getString(R.string.title_summary);
    }

    @Override
    public int getLayout() {
        return R.layout.step_summary;
    }

    @Override
    public void retrieveChoices() {
        HashMap<String, Object> values = characterCreatorHelper.getAll();

        characterName = (String) values.get(Constants.CHARACTER_NAME);
        characterConcept = (String) values.get(Constants.CHARACTER_CONCEPT);
        characterPlayer = (String) values.get(Constants.CHARACTER_PLAYER);
        characterVice = (Long) values.get(Constants.CHARACTER_VICE);
        characterVirtue = (Long) values.get(Constants.CHARACTER_VIRTUE);
        characterDemeanor = (Long) values.get(Constants.CHARACTER_DEMEANOR);
        characterNature = (Long) values.get(Constants.CHARACTER_NATURE);

        intelligence = (Integer) values.get(Constants.ATTR_INT);
        wits = (Integer) values.get(Constants.ATTR_WIT);
        resolve = (Integer) values.get(Constants.ATTR_RES);
        strength = (Integer) values.get(Constants.ATTR_STR);
        dexterity = (Integer) values.get(Constants.ATTR_DEX);
        stamina = (Integer) values.get(Constants.ATTR_STA);
        presence = (Integer) values.get(Constants.ATTR_PRE);
        manipulation = (Integer) values.get(Constants.ATTR_MAN);
        composure = (Integer) values.get(Constants.ATTR_COM);
        
        academics = (Integer) values.get(Constants.SKILL_ACADEMICS);
        computer = (Integer) values.get(Constants.SKILL_COMPUTER);
        crafts = (Integer) values.get(Constants.SKILL_CRAFTS);
        investigation = (Integer) values.get(Constants.SKILL_INVESTIGATION);
        medicine = (Integer) values.get(Constants.SKILL_MEDICINE);
        occult = (Integer) values.get(Constants.SKILL_OCCULT);
        politics = (Integer) values.get(Constants.SKILL_POLITICS);
        science = (Integer) values.get(Constants.SKILL_SCIENCE);

        athletics = (Integer) values.get(Constants.SKILL_ATHLETICS);
        brawl = (Integer) values.get(Constants.SKILL_BRAWL);
        drive = (Integer) values.get(Constants.SKILL_DRIVE);
        firearms = (Integer) values.get(Constants.SKILL_FIREARMS);
        larceny = (Integer) values.get(Constants.SKILL_LARCENY);
        stealth = (Integer) values.get(Constants.SKILL_STEALTH);
        survival = (Integer) values.get(Constants.SKILL_SURVIVAL);
        weaponry = (Integer) values.get(Constants.SKILL_WEAPONRY);

        animalKen = (Integer) values.get(Constants.SKILL_ANIMAL_KEN);
        empathy = (Integer) values.get(Constants.SKILL_EMPATHY);
        expression = (Integer) values.get(Constants.SKILL_EXPRESSION);
        intimidation = (Integer) values.get(Constants.SKILL_INTIMIDATION);
        persuasion = (Integer) values.get(Constants.SKILL_PERSUASION);
        socialize = (Integer) values.get(Constants.SKILL_SOCIALIZE);
        streetwise = (Integer) values.get(Constants.SKILL_STREETWISE);
        subterfuge = (Integer) values.get(Constants.SKILL_SUBTERFUGE);

        txtSummaryAttrMental.setText(getString(R.string.summary_attr_mental, intelligence, wits, resolve));
        txtSummaryAttrPhysical.setText(getString(R.string.summary_attr_physical, strength, dexterity, stamina));
        txtSummaryAttrSocial.setText(getString(R.string.summary_attr_social, presence, manipulation, composure));

        ArrayList<Map.Entry<String, Object>> mentalSkills = new ArrayList<>();
        ArrayList<Map.Entry<String, Object>> physicalSkills = new ArrayList<>();
        ArrayList<Map.Entry<String, Object>> socialSkills = new ArrayList<>();
        
        for (Map.Entry<String, Object> entry : values.entrySet()) {

            if (entry.getValue() != null && entry.getValue() instanceof Integer) {
                int key = (Integer) entry.getValue();

                if (key > 0) {
                    switch (entry.getKey()) {
                        case Constants.SKILL_ACADEMICS: {
                            mentalSkills.add(entry);            // 1
                            break;
                        }
                        case Constants.SKILL_COMPUTER: {
                            mentalSkills.add(entry);            // 2
                            break;
                        }
                        case Constants.SKILL_CRAFTS: {
                            mentalSkills.add(entry);            // 3
                            break;
                        }
                        case Constants.SKILL_INVESTIGATION: {
                            mentalSkills.add(entry);            // 4
                            break;
                        }
                        case Constants.SKILL_MEDICINE: {
                            mentalSkills.add(entry);            // 5
                            break;
                        }
                        case Constants.SKILL_OCCULT: {
                            mentalSkills.add(entry);            // 6
                            break;
                        }
                        case Constants.SKILL_POLITICS: {
                            mentalSkills.add(entry);            // 7
                            break;
                        }
                        case Constants.SKILL_SCIENCE: {
                            mentalSkills.add(entry);            // 8
                            break;
                        }
                        case Constants.SKILL_ATHLETICS: {
                            physicalSkills.add(entry);          // 1
                            break;
                        }
                        case Constants.SKILL_BRAWL: {
                            physicalSkills.add(entry);          // 2
                            break;
                        }
                        case Constants.SKILL_DRIVE: {
                            physicalSkills.add(entry);          // 3
                            break;
                        }
                        case Constants.SKILL_FIREARMS: {
                            physicalSkills.add(entry);          // 4
                            break;
                        }
                        case Constants.SKILL_LARCENY: {
                            physicalSkills.add(entry);          // 5
                            break;
                        }
                        case Constants.SKILL_STEALTH: {
                            physicalSkills.add(entry);          // 6
                            break;
                        }
                        case Constants.SKILL_SURVIVAL: {
                            physicalSkills.add(entry);          // 7
                            break;
                        }
                        case Constants.SKILL_WEAPONRY: {
                            physicalSkills.add(entry);          // 8
                            break;
                        }
                        case Constants.SKILL_ANIMAL_KEN: {
                            socialSkills.add(entry);            // 1
                            break;
                        }
                        case Constants.SKILL_EMPATHY: {
                            socialSkills.add(entry);            // 2
                            break;
                        }
                        case Constants.SKILL_EXPRESSION: {
                            socialSkills.add(entry);            // 3
                            break;
                        }
                        case Constants.SKILL_INTIMIDATION: {
                            socialSkills.add(entry);            // 4
                            break;
                        }
                        case Constants.SKILL_PERSUASION: {
                            socialSkills.add(entry);            // 5
                            break;
                        }
                        case Constants.SKILL_SOCIALIZE: {
                            socialSkills.add(entry);            // 6
                            break;
                        }
                        case Constants.SKILL_STREETWISE: {
                            socialSkills.add(entry);            // 7
                            break;
                        }
                        case Constants.SKILL_SUBTERFUGE: {
                            socialSkills.add(entry);            // 8
                            break;
                        }
                    }
                }
            }
        }

        populateSkillField(txtSummarySkillsMental, mentalSkills);
        populateSkillField(txtSummarySkillsPhysical, physicalSkills);
        populateSkillField(txtSummarySkillsSocial, socialSkills);
    }

    private void populateSkillField(TextView textView, ArrayList<Map.Entry<String, Object>> skills) {
        StringBuilder builder = new StringBuilder();

        Iterator<Map.Entry<String, Object>> iterator = skills.iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();

            builder.append(entry.getKey());
            builder.append(" ");
            builder.append(String.valueOf(entry.getValue()));
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }

        textView.setText(builder.toString());
    }

    @Override
    public boolean hasLeftoverPoints() {
        return false;
    }

    public void saveCharacterToRealm() {
        helper.save(character);

        ((NavDrawerActivity) getActivity()).onCharacterCreatorFinish();
    }

    @Override
    public void onPagerFinished() {
        saveCharacterToRealm();
    }
}
