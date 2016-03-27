package com.emi.nwodcombat;

import android.app.Application;

import com.emi.nwodcombat.greendao.controllers.CharacterController;
import com.emi.nwodcombat.greendao.controllers.DemeanorController;
import com.emi.nwodcombat.greendao.controllers.NatureController;
import com.emi.nwodcombat.greendao.controllers.ViceController;
import com.emi.nwodcombat.greendao.controllers.VirtueController;
import com.emi.nwodcombat.model.db.Demeanor;
import com.emi.nwodcombat.model.db.Nature;
import com.emi.nwodcombat.model.db.Vice;
import com.emi.nwodcombat.model.db.Virtue;

/**
 * Created by Emi on 3/14/16.
 */
public class ExtendedApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initSingletons();
    }

    private void initSingletons() {
        CharacterController.init(getApplicationContext());
        ViceController.init(getApplicationContext());
        VirtueController.init(getApplicationContext());
        DemeanorController.init(getApplicationContext());
        NatureController.init(getApplicationContext());

        generateVices();
        generateVirtues();
        generateDemeanorArchetypes();
        generateNatureArchetypes();
    }

    private void generateDemeanorArchetypes() {
        DemeanorController demeanorController = DemeanorController.getInstance();

        if (demeanorController.getList().size() == 0) {
            Demeanor authoritarian = new Demeanor();
            authoritarian.setName(getString(R.string.personality_name_authoritarian));
            authoritarian.setDescription(getString(R.string.personality_description_authoritarian));
            authoritarian.setRegainOne(getString(R.string.personality_regain_one_authoritarian));
            authoritarian.setRegainAll(getString(R.string.personality_regain_all_authoritarian));
            demeanorController.save(authoritarian);

            Demeanor child = new Demeanor();
            child.setName(getString(R.string.personality_name_child));
            child.setDescription(getString(R.string.personality_description_child));
            child.setRegainOne(getString(R.string.personality_regain_one_child));
            child.setRegainAll(getString(R.string.personality_regain_all_child));
            demeanorController.save(child);

            Demeanor competitor = new Demeanor();
            competitor.setName(getString(R.string.personality_name_competitor));
            competitor.setDescription(getString(R.string.personality_description_competitor));
            competitor.setRegainOne(getString(R.string.personality_regain_one_competitor));
            competitor.setRegainAll(getString(R.string.personality_regain_all_competitor));
            demeanorController.save(competitor);

            Demeanor conformist = new Demeanor();
            conformist.setName(getString(R.string.personality_name_conformist));
            conformist.setDescription(getString(R.string.personality_description_conformist));
            conformist.setRegainOne(getString(R.string.personality_regain_one_conformist));
            conformist.setRegainAll(getString(R.string.personality_regain_all_conformist));
            demeanorController.save(conformist);

            Demeanor conspirator = new Demeanor();
            conspirator.setName(getString(R.string.personality_name_conspirator));
            conspirator.setDescription(getString(R.string.personality_description_conspirator));
            conspirator.setRegainOne(getString(R.string.personality_regain_one_conspirator));
            conspirator.setRegainAll(getString(R.string.personality_regain_all_conspirator));
            demeanorController.save(conspirator);

            Demeanor courtesan = new Demeanor();
            courtesan.setName(getString(R.string.personality_name_courtesan));
            courtesan.setDescription(getString(R.string.personality_description_courtesan));
            courtesan.setRegainOne(getString(R.string.personality_regain_one_courtesan));
            courtesan.setRegainAll(getString(R.string.personality_regain_all_courtesan));
            demeanorController.save(courtesan);

            Demeanor cultLeader = new Demeanor();
            cultLeader.setName(getString(R.string.personality_name_cult_leader));
            cultLeader.setDescription(getString(R.string.personality_description_cult_leader));
            cultLeader.setRegainOne(getString(R.string.personality_regain_one_cult_leader));
            cultLeader.setRegainAll(getString(R.string.personality_regain_all_cult_leader));
            demeanorController.save(cultLeader);

            Demeanor deviant = new Demeanor();
            deviant.setName(getString(R.string.personality_name_deviant));
            deviant.setDescription(getString(R.string.personality_description_deviant));
            deviant.setRegainOne(getString(R.string.personality_regain_one_deviant));
            deviant.setRegainAll(getString(R.string.personality_regain_all_deviant));
            demeanorController.save(deviant);

            Demeanor follower = new Demeanor();
            follower.setName(getString(R.string.personality_name_follower));
            follower.setDescription(getString(R.string.personality_description_follower));
            follower.setRegainOne(getString(R.string.personality_regain_one_follower));
            follower.setRegainAll(getString(R.string.personality_regain_all_follower));
            demeanorController.save(follower);

            Demeanor guru = new Demeanor();
            guru.setName(getString(R.string.personality_name_guru));
            guru.setDescription(getString(R.string.personality_description_guru));
            guru.setRegainOne(getString(R.string.personality_regain_one_guru));
            guru.setRegainAll(getString(R.string.personality_regain_all_guru));
            demeanorController.save(guru);

            Demeanor idealist = new Demeanor();
            idealist.setName(getString(R.string.personality_name_idealist));
            idealist.setDescription(getString(R.string.personality_description_idealist));
            idealist.setRegainOne(getString(R.string.personality_regain_one_idealist));
            idealist.setRegainAll(getString(R.string.personality_regain_all_idealist));
            demeanorController.save(idealist);

            Demeanor jester = new Demeanor();
            jester.setName(getString(R.string.personality_name_jester));
            jester.setDescription(getString(R.string.personality_description_jester));
            jester.setRegainOne(getString(R.string.personality_regain_one_jester));
            jester.setRegainAll(getString(R.string.personality_regain_all_jester));
            demeanorController.save(jester);

            Demeanor junkie = new Demeanor();
            junkie.setName(getString(R.string.personality_name_junkie));
            junkie.setDescription(getString(R.string.personality_description_junkie));
            junkie.setRegainOne(getString(R.string.personality_regain_one_junkie));
            junkie.setRegainAll(getString(R.string.personality_regain_all_junkie));
            demeanorController.save(junkie);

            Demeanor martyr = new Demeanor();
            martyr.setName(getString(R.string.personality_name_martyr));
            martyr.setDescription(getString(R.string.personality_description_martyr));
            martyr.setRegainOne(getString(R.string.personality_regain_one_martyr));
            martyr.setRegainAll(getString(R.string.personality_regain_all_martyr));
            demeanorController.save(martyr);

            Demeanor masochist = new Demeanor();
            masochist.setName(getString(R.string.personality_name_masochist));
            masochist.setDescription(getString(R.string.personality_description_masochist));
            masochist.setRegainOne(getString(R.string.personality_regain_one_masochist));
            masochist.setRegainAll(getString(R.string.personality_regain_all_masochist));
            demeanorController.save(masochist);

            Demeanor monster = new Demeanor();
            monster.setName(getString(R.string.personality_name_monster));
            monster.setDescription(getString(R.string.personality_description_monster));
            monster.setRegainOne(getString(R.string.personality_regain_one_monster));
            monster.setRegainAll(getString(R.string.personality_regain_all_monster));
            demeanorController.save(monster);

            Demeanor nomad = new Demeanor();
            nomad.setName(getString(R.string.personality_name_nomad));
            nomad.setDescription(getString(R.string.personality_description_nomad));
            nomad.setRegainOne(getString(R.string.personality_regain_one_nomad));
            nomad.setRegainAll(getString(R.string.personality_regain_all_nomad));
            demeanorController.save(nomad);

            Demeanor nurturer = new Demeanor();
            nurturer.setName(getString(R.string.personality_name_nurturer));
            nurturer.setDescription(getString(R.string.personality_description_nurturer));
            nurturer.setRegainOne(getString(R.string.personality_regain_one_nurturer));
            nurturer.setRegainAll(getString(R.string.personality_regain_all_nurturer));
            demeanorController.save(nurturer);

            Demeanor perfectionist = new Demeanor();
            perfectionist.setName(getString(R.string.personality_name_perfectionist));
            perfectionist.setDescription(getString(R.string.personality_description_perfectionist));
            perfectionist.setRegainOne(getString(R.string.personality_regain_one_perfectionist));
            perfectionist.setRegainAll(getString(R.string.personality_regain_all_perfectionist));
            demeanorController.save(perfectionist);

            Demeanor penitent = new Demeanor();
            penitent.setName(getString(R.string.personality_name_penitent));
            penitent.setDescription(getString(R.string.personality_description_penitent));
            penitent.setRegainOne(getString(R.string.personality_regain_one_penitent));
            penitent.setRegainAll(getString(R.string.personality_regain_all_penitent));
            demeanorController.save(penitent);

            Demeanor questioner = new Demeanor();
            questioner.setName(getString(R.string.personality_name_questioner));
            questioner.setDescription(getString(R.string.personality_description_questioner));
            questioner.setRegainOne(getString(R.string.personality_regain_one_questioner));
            questioner.setRegainAll(getString(R.string.personality_regain_all_questioner));
            demeanorController.save(questioner);

            Demeanor rebel = new Demeanor();
            rebel.setName(getString(R.string.personality_name_rebel));
            rebel.setDescription(getString(R.string.personality_description_rebel));
            rebel.setRegainOne(getString(R.string.personality_regain_one_rebel));
            rebel.setRegainAll(getString(R.string.personality_regain_all_rebel));
            demeanorController.save(rebel);

            Demeanor scholar = new Demeanor();
            scholar.setName(getString(R.string.personality_name_scholar));
            scholar.setDescription(getString(R.string.personality_description_scholar));
            scholar.setRegainOne(getString(R.string.personality_regain_one_scholar));
            scholar.setRegainAll(getString(R.string.personality_regain_all_scholar));
            demeanorController.save(scholar);

            Demeanor socialChameleon = new Demeanor();
            socialChameleon.setName(getString(R.string.personality_name_social_chameleon));
            socialChameleon.setDescription(getString(R.string.personality_description_social_chameleon));
            socialChameleon.setRegainOne(getString(R.string.personality_regain_one_social_chameleon));
            socialChameleon.setRegainAll(getString(R.string.personality_regain_all_social_chameleon));
            demeanorController.save(socialChameleon);

            Demeanor spy = new Demeanor();
            spy.setName(getString(R.string.personality_name_spy));
            spy.setDescription(getString(R.string.personality_description_spy));
            spy.setRegainOne(getString(R.string.personality_regain_one_spy));
            spy.setRegainAll(getString(R.string.personality_regain_all_spy));
            demeanorController.save(spy);

            Demeanor survivor = new Demeanor();
            survivor.setName(getString(R.string.personality_name_survivor));
            survivor.setDescription(getString(R.string.personality_description_survivor));
            survivor.setRegainOne(getString(R.string.personality_regain_one_survivor));
            survivor.setRegainAll(getString(R.string.personality_regain_all_survivor));
            demeanorController.save(survivor);

            Demeanor visionary = new Demeanor();
            visionary.setName(getString(R.string.personality_name_visionary));
            visionary.setDescription(getString(R.string.personality_description_visionary));
            visionary.setRegainOne(getString(R.string.personality_regain_one_visionary));
            visionary.setRegainAll(getString(R.string.personality_regain_all_visionary));
            demeanorController.save(visionary);
        }
    }

    private void generateNatureArchetypes() {
        NatureController natureController = NatureController.getInstance();

        if (natureController.getList().size() == 0) {
            Nature authoritarian = new Nature();
            authoritarian.setName(getString(R.string.personality_name_authoritarian));
            authoritarian.setDescription(getString(R.string.personality_description_authoritarian));
            authoritarian.setRegainOne(getString(R.string.personality_regain_one_authoritarian));
            authoritarian.setRegainAll(getString(R.string.personality_regain_all_authoritarian));
            natureController.save(authoritarian);

            Nature child = new Nature();
            child.setName(getString(R.string.personality_name_child));
            child.setDescription(getString(R.string.personality_description_child));
            child.setRegainOne(getString(R.string.personality_regain_one_child));
            child.setRegainAll(getString(R.string.personality_regain_all_child));
            natureController.save(child);

            Nature competitor = new Nature();
            competitor.setName(getString(R.string.personality_name_competitor));
            competitor.setDescription(getString(R.string.personality_description_competitor));
            competitor.setRegainOne(getString(R.string.personality_regain_one_competitor));
            competitor.setRegainAll(getString(R.string.personality_regain_all_competitor));
            natureController.save(competitor);

            Nature conformist = new Nature();
            conformist.setName(getString(R.string.personality_name_conformist));
            conformist.setDescription(getString(R.string.personality_description_conformist));
            conformist.setRegainOne(getString(R.string.personality_regain_one_conformist));
            conformist.setRegainAll(getString(R.string.personality_regain_all_conformist));
            natureController.save(conformist);

            Nature conspirator = new Nature();
            conspirator.setName(getString(R.string.personality_name_conspirator));
            conspirator.setDescription(getString(R.string.personality_description_conspirator));
            conspirator.setRegainOne(getString(R.string.personality_regain_one_conspirator));
            conspirator.setRegainAll(getString(R.string.personality_regain_all_conspirator));
            natureController.save(conspirator);

            Nature courtesan = new Nature();
            courtesan.setName(getString(R.string.personality_name_courtesan));
            courtesan.setDescription(getString(R.string.personality_description_courtesan));
            courtesan.setRegainOne(getString(R.string.personality_regain_one_courtesan));
            courtesan.setRegainAll(getString(R.string.personality_regain_all_courtesan));
            natureController.save(courtesan);

            Nature cultLeader = new Nature();
            cultLeader.setName(getString(R.string.personality_name_cult_leader));
            cultLeader.setDescription(getString(R.string.personality_description_cult_leader));
            cultLeader.setRegainOne(getString(R.string.personality_regain_one_cult_leader));
            cultLeader.setRegainAll(getString(R.string.personality_regain_all_cult_leader));
            natureController.save(cultLeader);

            Nature deviant = new Nature();
            deviant.setName(getString(R.string.personality_name_deviant));
            deviant.setDescription(getString(R.string.personality_description_deviant));
            deviant.setRegainOne(getString(R.string.personality_regain_one_deviant));
            deviant.setRegainAll(getString(R.string.personality_regain_all_deviant));
            natureController.save(deviant);

            Nature follower = new Nature();
            follower.setName(getString(R.string.personality_name_follower));
            follower.setDescription(getString(R.string.personality_description_follower));
            follower.setRegainOne(getString(R.string.personality_regain_one_follower));
            follower.setRegainAll(getString(R.string.personality_regain_all_follower));
            natureController.save(follower);

            Nature guru = new Nature();
            guru.setName(getString(R.string.personality_name_guru));
            guru.setDescription(getString(R.string.personality_description_guru));
            guru.setRegainOne(getString(R.string.personality_regain_one_guru));
            guru.setRegainAll(getString(R.string.personality_regain_all_guru));
            natureController.save(guru);

            Nature idealist = new Nature();
            idealist.setName(getString(R.string.personality_name_idealist));
            idealist.setDescription(getString(R.string.personality_description_idealist));
            idealist.setRegainOne(getString(R.string.personality_regain_one_idealist));
            idealist.setRegainAll(getString(R.string.personality_regain_all_idealist));
            natureController.save(idealist);

            Nature jester = new Nature();
            jester.setName(getString(R.string.personality_name_jester));
            jester.setDescription(getString(R.string.personality_description_jester));
            jester.setRegainOne(getString(R.string.personality_regain_one_jester));
            jester.setRegainAll(getString(R.string.personality_regain_all_jester));
            natureController.save(jester);

            Nature junkie = new Nature();
            junkie.setName(getString(R.string.personality_name_junkie));
            junkie.setDescription(getString(R.string.personality_description_junkie));
            junkie.setRegainOne(getString(R.string.personality_regain_one_junkie));
            junkie.setRegainAll(getString(R.string.personality_regain_all_junkie));
            natureController.save(junkie);

            Nature martyr = new Nature();
            martyr.setName(getString(R.string.personality_name_martyr));
            martyr.setDescription(getString(R.string.personality_description_martyr));
            martyr.setRegainOne(getString(R.string.personality_regain_one_martyr));
            martyr.setRegainAll(getString(R.string.personality_regain_all_martyr));
            natureController.save(martyr);

            Nature masochist = new Nature();
            masochist.setName(getString(R.string.personality_name_masochist));
            masochist.setDescription(getString(R.string.personality_description_masochist));
            masochist.setRegainOne(getString(R.string.personality_regain_one_masochist));
            masochist.setRegainAll(getString(R.string.personality_regain_all_masochist));
            natureController.save(masochist);

            Nature monster = new Nature();
            monster.setName(getString(R.string.personality_name_monster));
            monster.setDescription(getString(R.string.personality_description_monster));
            monster.setRegainOne(getString(R.string.personality_regain_one_monster));
            monster.setRegainAll(getString(R.string.personality_regain_all_monster));
            natureController.save(monster);

            Nature nomad = new Nature();
            nomad.setName(getString(R.string.personality_name_nomad));
            nomad.setDescription(getString(R.string.personality_description_nomad));
            nomad.setRegainOne(getString(R.string.personality_regain_one_nomad));
            nomad.setRegainAll(getString(R.string.personality_regain_all_nomad));
            natureController.save(nomad);

            Nature nurturer = new Nature();
            nurturer.setName(getString(R.string.personality_name_nurturer));
            nurturer.setDescription(getString(R.string.personality_description_nurturer));
            nurturer.setRegainOne(getString(R.string.personality_regain_one_nurturer));
            nurturer.setRegainAll(getString(R.string.personality_regain_all_nurturer));
            natureController.save(nurturer);

            Nature perfectionist = new Nature();
            perfectionist.setName(getString(R.string.personality_name_perfectionist));
            perfectionist.setDescription(getString(R.string.personality_description_perfectionist));
            perfectionist.setRegainOne(getString(R.string.personality_regain_one_perfectionist));
            perfectionist.setRegainAll(getString(R.string.personality_regain_all_perfectionist));
            natureController.save(perfectionist);

            Nature penitent = new Nature();
            penitent.setName(getString(R.string.personality_name_penitent));
            penitent.setDescription(getString(R.string.personality_description_penitent));
            penitent.setRegainOne(getString(R.string.personality_regain_one_penitent));
            penitent.setRegainAll(getString(R.string.personality_regain_all_penitent));
            natureController.save(penitent);

            Nature questioner = new Nature();
            questioner.setName(getString(R.string.personality_name_questioner));
            questioner.setDescription(getString(R.string.personality_description_questioner));
            questioner.setRegainOne(getString(R.string.personality_regain_one_questioner));
            questioner.setRegainAll(getString(R.string.personality_regain_all_questioner));
            natureController.save(questioner);

            Nature rebel = new Nature();
            rebel.setName(getString(R.string.personality_name_rebel));
            rebel.setDescription(getString(R.string.personality_description_rebel));
            rebel.setRegainOne(getString(R.string.personality_regain_one_rebel));
            rebel.setRegainAll(getString(R.string.personality_regain_all_rebel));
            natureController.save(rebel);

            Nature scholar = new Nature();
            scholar.setName(getString(R.string.personality_name_scholar));
            scholar.setDescription(getString(R.string.personality_description_scholar));
            scholar.setRegainOne(getString(R.string.personality_regain_one_scholar));
            scholar.setRegainAll(getString(R.string.personality_regain_all_scholar));
            natureController.save(scholar);

            Nature socialChameleon = new Nature();
            socialChameleon.setName(getString(R.string.personality_name_social_chameleon));
            socialChameleon.setDescription(getString(R.string.personality_description_social_chameleon));
            socialChameleon.setRegainOne(getString(R.string.personality_regain_one_social_chameleon));
            socialChameleon.setRegainAll(getString(R.string.personality_regain_all_social_chameleon));
            natureController.save(socialChameleon);

            Nature spy = new Nature();
            spy.setName(getString(R.string.personality_name_spy));
            spy.setDescription(getString(R.string.personality_description_spy));
            spy.setRegainOne(getString(R.string.personality_regain_one_spy));
            spy.setRegainAll(getString(R.string.personality_regain_all_spy));
            natureController.save(spy);

            Nature survivor = new Nature();
            survivor.setName(getString(R.string.personality_name_survivor));
            survivor.setDescription(getString(R.string.personality_description_survivor));
            survivor.setRegainOne(getString(R.string.personality_regain_one_survivor));
            survivor.setRegainAll(getString(R.string.personality_regain_all_survivor));
            natureController.save(survivor);

            Nature visionary = new Nature();
            visionary.setName(getString(R.string.personality_name_visionary));
            visionary.setDescription(getString(R.string.personality_description_visionary));
            visionary.setRegainOne(getString(R.string.personality_regain_one_visionary));
            visionary.setRegainAll(getString(R.string.personality_regain_all_visionary));
            natureController.save(visionary);
        }
    }

    private void generateVices() {
        ViceController viceController = ViceController.getInstance();

        if (viceController.getList().size() == 0) {
            Vice envy = new Vice();
            envy.setName(getString(R.string.vice_name_envy));
            envy.setDescription(getString(R.string.vice_description_envy));
            envy.setRegainOne(getString(R.string.vice_regain_one_envy));
            viceController.save(envy);

            Vice gluttony = new Vice();
            gluttony.setName(getString(R.string.vice_name_gluttony));
            gluttony.setDescription(getString(R.string.vice_description_gluttony));
            gluttony.setRegainOne(getString(R.string.vice_regain_one_gluttony));
            viceController.save(gluttony);

            Vice greed = new Vice();
            greed.setName(getString(R.string.vice_name_greed));
            greed.setDescription(getString(R.string.vice_description_greed));
            greed.setRegainOne(getString(R.string.vice_regain_one_greed));
            viceController.save(greed);

            Vice lust = new Vice();
            lust.setName(getString(R.string.vice_name_lust));
            lust.setDescription(getString(R.string.vice_description_lust));
            lust.setRegainOne(getString(R.string.vice_regain_one_lust));
            viceController.save(lust);

            Vice pride = new Vice();
            pride.setName(getString(R.string.vice_name_pride));
            pride.setDescription(getString(R.string.vice_description_pride));
            pride.setRegainOne(getString(R.string.vice_regain_one_pride));
            viceController.save(pride);

            Vice sloth = new Vice();
            sloth.setName(getString(R.string.vice_name_sloth));
            sloth.setDescription(getString(R.string.vice_description_sloth));
            sloth.setRegainOne(getString(R.string.vice_regain_one_sloth));
            viceController.save(sloth);

            Vice wrath = new Vice();
            wrath.setName(getString(R.string.vice_name_wrath));
            wrath.setDescription(getString(R.string.vice_description_wrath));
            wrath.setRegainOne(getString(R.string.vice_regain_one_wrath));
            viceController.save(wrath);
        }
    }
    
    private void generateVirtues() {
        VirtueController virtueController = VirtueController.getInstance();

        if (virtueController.getList().size() == 0) {
            Virtue charity = new Virtue();
            charity.setName(getString(R.string.virtue_name_charity));
            charity.setDescription(getString(R.string.virtue_description_charity));
            charity.setRegainAll(getString(R.string.virtue_regain_all_charity));
            virtueController.save(charity);
            
            Virtue faith = new Virtue();
            faith.setName(getString(R.string.virtue_name_faith));
            faith.setDescription(getString(R.string.virtue_description_faith));
            faith.setRegainAll(getString(R.string.virtue_regain_all_faith));
            virtueController.save(faith);

            Virtue fortitude = new Virtue();
            fortitude.setName(getString(R.string.virtue_name_fortitude));
            fortitude.setDescription(getString(R.string.virtue_description_fortitude));
            fortitude.setRegainAll(getString(R.string.virtue_regain_all_fortitude));
            virtueController.save(fortitude);

            Virtue hope = new Virtue();
            hope.setName(getString(R.string.virtue_name_hope));
            hope.setDescription(getString(R.string.virtue_description_hope));
            hope.setRegainAll(getString(R.string.virtue_regain_all_hope));
            virtueController.save(hope);

            Virtue justice = new Virtue();
            justice.setName(getString(R.string.virtue_name_justice));
            justice.setDescription(getString(R.string.virtue_description_justice));
            justice.setRegainAll(getString(R.string.virtue_regain_all_justice));
            virtueController.save(justice);

            Virtue prudence = new Virtue();
            prudence.setName(getString(R.string.virtue_name_prudence));
            prudence.setDescription(getString(R.string.virtue_description_prudence));
            prudence.setRegainAll(getString(R.string.virtue_regain_all_prudence));
            virtueController.save(prudence);

            Virtue temperance = new Virtue();
            temperance.setName(getString(R.string.virtue_name_temperance));
            temperance.setDescription(getString(R.string.virtue_description_temperance));
            temperance.setDescription(getString(R.string.virtue_regain_all_temperance));
            virtueController.save(temperance);
        }
    }
}
