package com.emi.nwodcombat.tools;

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

    public static class DemeanorChanged {
        public int position;

        public DemeanorChanged(int position) {
            this.position = position;
        }
    }

    public static class NatureChanged {

        public int position;

        public NatureChanged(int position) {
            this.position = position;
        }
    }

    public static class ViceChanged {

        public int position;

        public ViceChanged(int position) {
            this.position = position;
        }
    }

    public static class VirtueChanged {

        public int position;

        public VirtueChanged(int position) {
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
        public String key;
        public String type;
        public String value;

        public TextEntryChanged(String key, String type, String value) {
            this.key = key;
            this.type = type;
            this.value = value;
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

    public static class ValueChanged {
        public String key;
        public String category;
        public boolean isIncrease;

        public ValueChanged(boolean isIncrease, String key, String traitCategory) {
            this.isIncrease = isIncrease;
            this.key = key;
            this.category = traitCategory;
        }
    }

    public static class WizardComplete {
        public WizardComplete() { }
    }

    public static class WizardClose {
        public WizardClose() { }
    }

    public static class SpecialtyChecked {
        public boolean isChecked;
        public String key;
        public String category;
        public String specialtyName;

        public SpecialtyChecked(boolean isChecked, String key, String category, String specialtyName) {
            this.category = category;
            this.isChecked = isChecked;
            this.key = key;
            this.specialtyName = specialtyName;
        }
    }
}
