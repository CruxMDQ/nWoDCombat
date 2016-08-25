package com.emi.nwodcombat.application;

import android.app.Application;

import com.emi.nwodcombat.R;
import com.emi.nwodcombat.model.pojos.PersonalityArchetypePojo;
import com.emi.nwodcombat.model.pojos.VicePojo;
import com.emi.nwodcombat.model.pojos.VirtuePojo;
import com.emi.nwodcombat.model.realm.Demeanor;
import com.emi.nwodcombat.model.realm.Entry;
import com.emi.nwodcombat.model.realm.Nature;
import com.emi.nwodcombat.model.realm.Vice;
import com.emi.nwodcombat.model.realm.Virtue;
import com.emi.nwodcombat.persistence.PersistenceLayer;
import com.emi.nwodcombat.persistence.RealmHelper;
import com.emi.nwodcombat.rules.Rule;
import com.emi.nwodcombat.rules.RulesEngine;
import com.emi.nwodcombat.tools.Constants;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by Emi on 3/14/16.
 * Refer to this site for training on Realm: https://realm.io/docs/java/latest/
 */
public class NwodCombatApplication extends Application {

    PersistenceLayer helper;

    @Override
    public void onCreate() {
        super.onCreate();

        helper = RealmHelper.getInstance(this);

        generateRules();

        generatePersonalityArchetypes();
        generateRealmVices();
        generateRealmVirtues();
    }

    private void generateRules() {
        Rule rule = new Rule();

        rule.setName("Dream");
        rule.setNamespace("Awakened");
        rule.setDescription("Your character can dig within her dreams for prophetic answers to primordial truths. She may enter her own dreams withour a meditation roll when she sleeps, and if she has a basic understanding of something she wishes to divine from her dreams, you may use this Merit. Your character must sleep or meditate for at least four hours. Then, ask the Storyteller a yes or no question about the topic at hand. He must answer accurately, but can use \"maybe\" if the answer is truly neither yes or no. Depending on the answer, you may ask additional questions, up to your Dream Merit dots. You can use that many questions per chapter.");

        ArrayList<Entry> requisiteOne = new ArrayList<>();

        Entry wits = new Entry();

        wits.setKey(Constants.ATTR_WIT);
        wits.setType(Constants.FIELD_TYPE_INTEGER);
        wits.setValue(3);

        requisiteOne.add(wits);

        rule.addRequirement(requisiteOne);

        ArrayList<Entry> requisiteTwo = new ArrayList<>();

        Entry composure = new Entry();

        composure.setKey(Constants.ATTR_COM);
        composure.setType(Constants.FIELD_TYPE_INTEGER);
        composure.setValue(3);

        requisiteTwo.add(composure);

        rule.addRequirement(requisiteTwo);

        RulesEngine.addRule(rule);
    }

    private void generateRealmVirtues() {
        Gson gson = new Gson();

        if (helper.getCount(Virtue.class) == 0) {
            ArrayList<String> jsonObjects = new ArrayList<>();

            VirtuePojo charity = new VirtuePojo();
            charity.setId((long) jsonObjects.size());
            charity.setName(getString(R.string.virtue_name_charity));
            charity.setDescription(getString(R.string.virtue_description_charity));
            charity.setRegainAll(getString(R.string.virtue_regain_all_charity));
            jsonObjects.add(gson.toJson(charity));

            VirtuePojo faith = new VirtuePojo();
            faith.setId((long) jsonObjects.size());
            faith.setName(getString(R.string.virtue_name_faith));
            faith.setDescription(getString(R.string.virtue_description_faith));
            faith.setRegainAll(getString(R.string.virtue_regain_all_faith));
            jsonObjects.add(gson.toJson(faith));

            VirtuePojo fortitude = new VirtuePojo();
            fortitude.setId((long) jsonObjects.size());
            fortitude.setName(getString(R.string.virtue_name_fortitude));
            fortitude.setDescription(getString(R.string.virtue_description_fortitude));
            fortitude.setRegainAll(getString(R.string.virtue_regain_all_fortitude));
            jsonObjects.add(gson.toJson(fortitude));

            VirtuePojo hope = new VirtuePojo();
            hope.setId((long) jsonObjects.size());
            hope.setName(getString(R.string.virtue_name_hope));
            hope.setDescription(getString(R.string.virtue_description_hope));
            hope.setRegainAll(getString(R.string.virtue_regain_all_hope));
            jsonObjects.add(gson.toJson(hope));

            VirtuePojo justice = new VirtuePojo();
            justice.setId((long) jsonObjects.size());
            justice.setName(getString(R.string.virtue_name_justice));
            justice.setDescription(getString(R.string.virtue_description_justice));
            justice.setRegainAll(getString(R.string.virtue_regain_all_justice));
            jsonObjects.add(gson.toJson(justice));

            VirtuePojo prudence = new VirtuePojo();
            prudence.setId((long) jsonObjects.size());
            prudence.setName(getString(R.string.virtue_name_prudence));
            prudence.setDescription(getString(R.string.virtue_description_prudence));
            prudence.setRegainAll(getString(R.string.virtue_regain_all_prudence));
            jsonObjects.add(gson.toJson(prudence));

            VirtuePojo temperance = new VirtuePojo();
            temperance.setId((long) jsonObjects.size());
            temperance.setName(getString(R.string.virtue_name_temperance));
            temperance.setDescription(getString(R.string.virtue_description_temperance));
            temperance.setRegainAll(getString(R.string.virtue_regain_all_temperance));
            jsonObjects.add(gson.toJson(temperance));

            helper.save(Virtue.class, jsonObjects);
        }
    }

    private void generateRealmVices() {
        Gson gson = new Gson();

        if (helper.getCount(Vice.class) == 0) {
            ArrayList<String> jsonObjects = new ArrayList<>();

//            envy.setId(Long.valueOf(helper.getCount(Vice.class)));
            VicePojo envy = new VicePojo();
            envy.setId((long) jsonObjects.size());
            envy.setName(getString(R.string.vice_name_envy));
            envy.setDescription(getString(R.string.vice_description_envy));
            envy.setRegainOne(getString(R.string.vice_regain_one_envy));
            jsonObjects.add(gson.toJson(envy));

            VicePojo gluttony = new VicePojo();
            gluttony.setId((long) jsonObjects.size());
            gluttony.setName(getString(R.string.vice_name_gluttony));
            gluttony.setDescription(getString(R.string.vice_description_gluttony));
            gluttony.setRegainOne(getString(R.string.vice_regain_one_gluttony));
            jsonObjects.add(gson.toJson(gluttony));

            VicePojo greed = new VicePojo();
            greed.setId((long) jsonObjects.size());
            greed.setName(getString(R.string.vice_name_greed));
            greed.setDescription(getString(R.string.vice_description_greed));
            greed.setRegainOne(getString(R.string.vice_regain_one_greed));
            jsonObjects.add(gson.toJson(greed));

            VicePojo lust = new VicePojo();
            lust.setId((long) jsonObjects.size());
            lust.setName(getString(R.string.vice_name_lust));
            lust.setDescription(getString(R.string.vice_description_lust));
            lust.setRegainOne(getString(R.string.vice_regain_one_lust));
            jsonObjects.add(gson.toJson(lust));

            VicePojo pride = new VicePojo();
            pride.setId((long) jsonObjects.size());
            pride.setName(getString(R.string.vice_name_pride));
            pride.setDescription(getString(R.string.vice_description_pride));
            pride.setRegainOne(getString(R.string.vice_regain_one_pride));
            jsonObjects.add(gson.toJson(pride));

            VicePojo sloth = new VicePojo();
            sloth.setId((long) jsonObjects.size());
            sloth.setName(getString(R.string.vice_name_sloth));
            sloth.setDescription(getString(R.string.vice_description_sloth));
            sloth.setRegainOne(getString(R.string.vice_regain_one_sloth));
            jsonObjects.add(gson.toJson(sloth));

            VicePojo wrath = new VicePojo();
            wrath.setId((long) jsonObjects.size());
            wrath.setName(getString(R.string.vice_name_wrath));
            wrath.setDescription(getString(R.string.vice_description_wrath));
            wrath.setRegainOne(getString(R.string.vice_regain_one_wrath));
            jsonObjects.add(gson.toJson(wrath));

            helper.save(Vice.class, jsonObjects);
        }
    }

    private void generatePersonalityArchetypes() {

        Gson gson = new Gson();

        if (helper.getCount(Nature.class) == 0) {
            ArrayList<String> jsonObjects = new ArrayList<>();

            PersonalityArchetypePojo authoritarian = new PersonalityArchetypePojo();
            authoritarian.setId((long) jsonObjects.size());
            authoritarian.setName(getString(R.string.personality_name_authoritarian));
            authoritarian.setDescription(getString(R.string.personality_description_authoritarian));
            authoritarian.setRegainOne(getString(R.string.personality_regain_one_authoritarian));
            authoritarian.setRegainAll(getString(R.string.personality_regain_all_authoritarian));
            jsonObjects.add(gson.toJson(authoritarian));

            PersonalityArchetypePojo child = new PersonalityArchetypePojo();
            child.setId((long) jsonObjects.size());
            child.setName(getString(R.string.personality_name_child));
            child.setDescription(getString(R.string.personality_description_child));
            child.setRegainOne(getString(R.string.personality_regain_one_child));
            child.setRegainAll(getString(R.string.personality_regain_all_child));
            jsonObjects.add(gson.toJson(child));

            PersonalityArchetypePojo competitor = new PersonalityArchetypePojo();
            competitor.setId((long) jsonObjects.size());
            competitor.setName(getString(R.string.personality_name_competitor));
            competitor.setDescription(getString(R.string.personality_description_competitor));
            competitor.setRegainOne(getString(R.string.personality_regain_one_competitor));
            competitor.setRegainAll(getString(R.string.personality_regain_all_competitor));
            jsonObjects.add(gson.toJson(competitor));

            PersonalityArchetypePojo conformist = new PersonalityArchetypePojo();
            conformist.setId((long) jsonObjects.size());
            conformist.setName(getString(R.string.personality_name_conformist));
            conformist.setDescription(getString(R.string.personality_description_conformist));
            conformist.setRegainOne(getString(R.string.personality_regain_one_conformist));
            conformist.setRegainAll(getString(R.string.personality_regain_all_conformist));
            jsonObjects.add(gson.toJson(conformist));

            PersonalityArchetypePojo conspirator = new PersonalityArchetypePojo();
            conspirator.setId((long) jsonObjects.size());
            conspirator.setName(getString(R.string.personality_name_conspirator));
            conspirator.setDescription(getString(R.string.personality_description_conspirator));
            conspirator.setRegainOne(getString(R.string.personality_regain_one_conspirator));
            conspirator.setRegainAll(getString(R.string.personality_regain_all_conspirator));
            jsonObjects.add(gson.toJson(conspirator));

            PersonalityArchetypePojo courtesan = new PersonalityArchetypePojo();
            courtesan.setId((long) jsonObjects.size());
            courtesan.setName(getString(R.string.personality_name_courtesan));
            courtesan.setDescription(getString(R.string.personality_description_courtesan));
            courtesan.setRegainOne(getString(R.string.personality_regain_one_courtesan));
            courtesan.setRegainAll(getString(R.string.personality_regain_all_courtesan));
            jsonObjects.add(gson.toJson(courtesan));

            PersonalityArchetypePojo cultLeader = new PersonalityArchetypePojo();
            cultLeader.setId((long) jsonObjects.size());
            cultLeader.setName(getString(R.string.personality_name_cult_leader));
            cultLeader.setDescription(getString(R.string.personality_description_cult_leader));
            cultLeader.setRegainOne(getString(R.string.personality_regain_one_cult_leader));
            cultLeader.setRegainAll(getString(R.string.personality_regain_all_cult_leader));
            jsonObjects.add(gson.toJson(cultLeader));

            PersonalityArchetypePojo deviant = new PersonalityArchetypePojo();
            deviant.setId((long) jsonObjects.size());
            deviant.setName(getString(R.string.personality_name_deviant));
            deviant.setDescription(getString(R.string.personality_description_deviant));
            deviant.setRegainOne(getString(R.string.personality_regain_one_deviant));
            deviant.setRegainAll(getString(R.string.personality_regain_all_deviant));
            jsonObjects.add(gson.toJson(deviant));

            PersonalityArchetypePojo follower = new PersonalityArchetypePojo();
            follower.setId((long) jsonObjects.size());
            follower.setName(getString(R.string.personality_name_follower));
            follower.setDescription(getString(R.string.personality_description_follower));
            follower.setRegainOne(getString(R.string.personality_regain_one_follower));
            follower.setRegainAll(getString(R.string.personality_regain_all_follower));
            jsonObjects.add(gson.toJson(follower));

            PersonalityArchetypePojo guru = new PersonalityArchetypePojo();
            guru.setId((long) jsonObjects.size());
            guru.setName(getString(R.string.personality_name_guru));
            guru.setDescription(getString(R.string.personality_description_guru));
            guru.setRegainOne(getString(R.string.personality_regain_one_guru));
            guru.setRegainAll(getString(R.string.personality_regain_all_guru));
            jsonObjects.add(gson.toJson(guru));

            PersonalityArchetypePojo idealist = new PersonalityArchetypePojo();
            idealist.setId((long) jsonObjects.size());
            idealist.setName(getString(R.string.personality_name_idealist));
            idealist.setDescription(getString(R.string.personality_description_idealist));
            idealist.setRegainOne(getString(R.string.personality_regain_one_idealist));
            idealist.setRegainAll(getString(R.string.personality_regain_all_idealist));
            jsonObjects.add(gson.toJson(idealist));

            PersonalityArchetypePojo jester = new PersonalityArchetypePojo();
            jester.setId((long) jsonObjects.size());
            jester.setName(getString(R.string.personality_name_jester));
            jester.setDescription(getString(R.string.personality_description_jester));
            jester.setRegainOne(getString(R.string.personality_regain_one_jester));
            jester.setRegainAll(getString(R.string.personality_regain_all_jester));
            jsonObjects.add(gson.toJson(jester));

            PersonalityArchetypePojo junkie = new PersonalityArchetypePojo();
            junkie.setId((long) jsonObjects.size());
            junkie.setName(getString(R.string.personality_name_junkie));
            junkie.setDescription(getString(R.string.personality_description_junkie));
            junkie.setRegainOne(getString(R.string.personality_regain_one_junkie));
            junkie.setRegainAll(getString(R.string.personality_regain_all_junkie));
            jsonObjects.add(gson.toJson(junkie));

            PersonalityArchetypePojo martyr = new PersonalityArchetypePojo();
            martyr.setId((long) jsonObjects.size());
            martyr.setName(getString(R.string.personality_name_martyr));
            martyr.setDescription(getString(R.string.personality_description_martyr));
            martyr.setRegainOne(getString(R.string.personality_regain_one_martyr));
            martyr.setRegainAll(getString(R.string.personality_regain_all_martyr));
            jsonObjects.add(gson.toJson(martyr));

            PersonalityArchetypePojo masochist = new PersonalityArchetypePojo();
            masochist.setId((long) jsonObjects.size());
            masochist.setName(getString(R.string.personality_name_masochist));
            masochist.setDescription(getString(R.string.personality_description_masochist));
            masochist.setRegainOne(getString(R.string.personality_regain_one_masochist));
            masochist.setRegainAll(getString(R.string.personality_regain_all_masochist));
            jsonObjects.add(gson.toJson(masochist));

            PersonalityArchetypePojo monster = new PersonalityArchetypePojo();
            monster.setId((long) jsonObjects.size());
            monster.setName(getString(R.string.personality_name_monster));
            monster.setDescription(getString(R.string.personality_description_monster));
            monster.setRegainOne(getString(R.string.personality_regain_one_monster));
            monster.setRegainAll(getString(R.string.personality_regain_all_monster));
            jsonObjects.add(gson.toJson(monster));

            PersonalityArchetypePojo nomad = new PersonalityArchetypePojo();
            nomad.setId((long) jsonObjects.size());
            nomad.setName(getString(R.string.personality_name_nomad));
            nomad.setDescription(getString(R.string.personality_description_nomad));
            nomad.setRegainOne(getString(R.string.personality_regain_one_nomad));
            nomad.setRegainAll(getString(R.string.personality_regain_all_nomad));
            jsonObjects.add(gson.toJson(nomad));

            PersonalityArchetypePojo nurturer = new PersonalityArchetypePojo();
            nurturer.setId((long) jsonObjects.size());
            nurturer.setName(getString(R.string.personality_name_nurturer));
            nurturer.setDescription(getString(R.string.personality_description_nurturer));
            nurturer.setRegainOne(getString(R.string.personality_regain_one_nurturer));
            nurturer.setRegainAll(getString(R.string.personality_regain_all_nurturer));
            jsonObjects.add(gson.toJson(nurturer));

            PersonalityArchetypePojo perfectionist = new PersonalityArchetypePojo();
            perfectionist.setId((long) jsonObjects.size());
            perfectionist.setName(getString(R.string.personality_name_perfectionist));
            perfectionist.setDescription(getString(R.string.personality_description_perfectionist));
            perfectionist.setRegainOne(getString(R.string.personality_regain_one_perfectionist));
            perfectionist.setRegainAll(getString(R.string.personality_regain_all_perfectionist));
            jsonObjects.add(gson.toJson(perfectionist));

            PersonalityArchetypePojo penitent = new PersonalityArchetypePojo();
            penitent.setId((long) jsonObjects.size());
            penitent.setName(getString(R.string.personality_name_penitent));
            penitent.setDescription(getString(R.string.personality_description_penitent));
            penitent.setRegainOne(getString(R.string.personality_regain_one_penitent));
            penitent.setRegainAll(getString(R.string.personality_regain_all_penitent));
            jsonObjects.add(gson.toJson(penitent));

            PersonalityArchetypePojo questioner = new PersonalityArchetypePojo();
            questioner.setId((long) jsonObjects.size());
            questioner.setName(getString(R.string.personality_name_questioner));
            questioner.setDescription(getString(R.string.personality_description_questioner));
            questioner.setRegainOne(getString(R.string.personality_regain_one_questioner));
            questioner.setRegainAll(getString(R.string.personality_regain_all_questioner));
            jsonObjects.add(gson.toJson(questioner));

            PersonalityArchetypePojo rebel = new PersonalityArchetypePojo();
            rebel.setId((long) jsonObjects.size());
            rebel.setName(getString(R.string.personality_name_rebel));
            rebel.setDescription(getString(R.string.personality_description_rebel));
            rebel.setRegainOne(getString(R.string.personality_regain_one_rebel));
            rebel.setRegainAll(getString(R.string.personality_regain_all_rebel));
            jsonObjects.add(gson.toJson(rebel));

            PersonalityArchetypePojo scholar = new PersonalityArchetypePojo();
            scholar.setId((long) jsonObjects.size());
            scholar.setName(getString(R.string.personality_name_scholar));
            scholar.setDescription(getString(R.string.personality_description_scholar));
            scholar.setRegainOne(getString(R.string.personality_regain_one_scholar));
            scholar.setRegainAll(getString(R.string.personality_regain_all_scholar));
            jsonObjects.add(gson.toJson(scholar));

            PersonalityArchetypePojo socialChameleon = new PersonalityArchetypePojo();
            socialChameleon.setId((long) jsonObjects.size());
            socialChameleon.setName(getString(R.string.personality_name_social_chameleon));
            socialChameleon.setDescription(
                    getString(R.string.personality_description_social_chameleon));
            socialChameleon.setRegainOne(
                    getString(R.string.personality_regain_one_social_chameleon));
            socialChameleon.setRegainAll(
                    getString(R.string.personality_regain_all_social_chameleon));
            jsonObjects.add(gson.toJson(socialChameleon));

            PersonalityArchetypePojo spy = new PersonalityArchetypePojo();
            spy.setId((long) jsonObjects.size());
            spy.setName(getString(R.string.personality_name_spy));
            spy.setDescription(getString(R.string.personality_description_spy));
            spy.setRegainOne(getString(R.string.personality_regain_one_spy));
            spy.setRegainAll(getString(R.string.personality_regain_all_spy));
            jsonObjects.add(gson.toJson(spy));

            PersonalityArchetypePojo survivor = new PersonalityArchetypePojo();
            survivor.setId((long) jsonObjects.size());
            survivor.setName(getString(R.string.personality_name_survivor));
            survivor.setDescription(getString(R.string.personality_description_survivor));
            survivor.setRegainOne(getString(R.string.personality_regain_one_survivor));
            survivor.setRegainAll(getString(R.string.personality_regain_all_survivor));
            jsonObjects.add(gson.toJson(survivor));

            PersonalityArchetypePojo visionary = new PersonalityArchetypePojo();
            visionary.setId((long) jsonObjects.size());
            visionary.setName(getString(R.string.personality_name_visionary));
            visionary.setDescription(getString(R.string.personality_description_visionary));
            visionary.setRegainOne(getString(R.string.personality_regain_one_visionary));
            visionary.setRegainAll(getString(R.string.personality_regain_all_visionary));
            jsonObjects.add(gson.toJson(visionary));

            helper.save(Nature.class, jsonObjects);
            helper.save(Demeanor.class, jsonObjects);
        }
    }
}
