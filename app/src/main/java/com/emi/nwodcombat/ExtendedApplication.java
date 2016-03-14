package com.emi.nwodcombat;

import android.app.Application;

import com.emi.nwodcombat.greendao.controllers.CharacterController;
import com.emi.nwodcombat.greendao.controllers.ViceController;
import com.emi.nwodcombat.greendao.controllers.VirtueController;
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

        generateVices();
        generateVirtues();
    }

    private void generateVices() {
        ViceController viceController = ViceController.getInstance();

        if (viceController.getList().size() == 0) {
            Vice envy = new Vice();
            envy.setName(getString(R.string.vice_envy));
            viceController.save(envy);

            Vice gluttony = new Vice();
            gluttony.setName(getString(R.string.vice_gluttony));
            viceController.save(gluttony);

            Vice greed = new Vice();
            greed.setName(getString(R.string.greed));
            viceController.save(greed);

            Vice lust = new Vice();
            lust.setName(getString(R.string.vice_lust));
            viceController.save(lust);

            Vice pride = new Vice();
            pride.setName(getString(R.string.vice_pride));
            viceController.save(pride);

            Vice sloth = new Vice();
            sloth.setName(getString(R.string.vice_sloth));
            viceController.save(sloth);

            Vice wrath = new Vice();
            wrath.setName(getString(R.string.vice_wrath));
            viceController.save(wrath);
        }
    }
    
    private void generateVirtues() {
        VirtueController virtueController = VirtueController.getInstance();

        if (virtueController.getList().size() == 0) {
            Virtue charity = new Virtue();
            charity.setName(getString(R.string.virtue_charity));
            virtueController.save(charity);
            
            Virtue faith = new Virtue();
            faith.setName(getString(R.string.virtue_faith));
            virtueController.save(faith);

            Virtue fortitude = new Virtue();
            fortitude.setName(getString(R.string.virtue_fortitude));
            virtueController.save(fortitude);

            Virtue hope = new Virtue();
            hope.setName(getString(R.string.virtue_hope));
            virtueController.save(hope);

            Virtue justice = new Virtue();
            justice.setName(getString(R.string.virtue_justice));
            virtueController.save(justice);

            Virtue prudence = new Virtue();
            prudence.setName(getString(R.string.virtue_prudence));
            virtueController.save(prudence);

            Virtue temperance = new Virtue();
            temperance.setName(getString(R.string.virtue_temperance));
            virtueController.save(temperance);
        }
    }
}
