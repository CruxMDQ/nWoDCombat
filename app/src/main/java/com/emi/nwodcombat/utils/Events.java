package com.emi.nwodcombat.utils;

import com.emi.nwodcombat.model.realm.Entry;

/**
 * Created by emiliano.desantis on 18/05/2016.
 */
public class Events {
    public static class CharacterDeleted {
        public long id;

        public CharacterDeleted(long id) {
            this.id = id;
        }
    }

    public static class ExperiencePoolChanged {
        public boolean isIncrease;

        public ExperiencePoolChanged(boolean isIncrease) {
            this.isIncrease = isIncrease;
        }
    }

    public static class DemeanorTraitChanged {
        public int position;

        public DemeanorTraitChanged(int position) {
            this.position = position;
        }
    }

    public static class NatureTraitChanged {

        public int position;

        public NatureTraitChanged(int position) {
            this.position = position;
        }
    }

    public static class ViceTraitChanged {

        public int position;

        public ViceTraitChanged(int position) {
            this.position = position;
        }
    }

    public static class VirtueTraitChanged {

        public int position;

        public VirtueTraitChanged(int position) {
            this.position = position;
        }
    }

    public static class StepCompletionChecked {
        public final boolean isStepComplete;

        public StepCompletionChecked(boolean isStepComplete) {
            this.isStepComplete = isStepComplete;
        }
    }

    public static class CharacterDetail {
        public Long id;

        public CharacterDetail(Long id) {
            this.id = id;
        }
    }

    public static class TextEntryChanged {
        public Entry entry;

        public TextEntryChanged(Entry entry) {
            this.entry = entry;
        }
    }

    public static class AttributeChanged {
        public String key;
        public String category;
        public boolean isIncrease;

        public AttributeChanged(boolean isIncrease, String key, String traitCategory) {
            this.isIncrease = isIncrease;
            this.key = key;
            this.category = traitCategory;
        }
    }

    public static class SkillChanged {
        public String key;
        public String category;
        public boolean isIncrease;

        public SkillChanged(boolean isIncrease, String key, String traitCategory) {
            this.isIncrease = isIncrease;
            this.key = key;
            this.category = traitCategory;
        }
    }

}
