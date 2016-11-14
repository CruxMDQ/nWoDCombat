package com.emi.nwodcombat.tools;

import com.emi.nwodcombat.adapters.MeritsAdapter;
import com.emi.nwodcombat.rules.Rule;

/**
 * Created by emiliano.desantis on 18/05/2016.
 */
public class Events {
    public static class CharacterDeleted {
        public final long id;

        public CharacterDeleted(long id) {
            this.id = id;
        }
    }

    public static class ExperiencePoolChanged {
        public final boolean isIncrease;

        public ExperiencePoolChanged(boolean isIncrease) {
            this.isIncrease = isIncrease;
        }
    }

    public static class DemeanorChanged {
        public final int position;

        public DemeanorChanged(int position) {
            this.position = position;
        }
    }

    public static class NatureChanged {

        public final int position;

        public NatureChanged(int position) {
            this.position = position;
        }
    }

    public static class ViceChanged {

        public final int position;

        public ViceChanged(int position) {
            this.position = position;
        }
    }

    public static class VirtueChanged {

        public final int position;

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
        public final Long id;

        public CharacterDetail(Long id) {
            this.id = id;
        }
    }

    public static class TextEntryChanged {
        public final String key;
        public final String type;
        public final String value;

        public TextEntryChanged(String key, String type, String value) {
            this.key = key;
            this.type = type;
            this.value = value;
        }
    }

    public static class AttributeChanged {
        public final String key;
        public final String category;
        public final boolean isIncrease;

        public AttributeChanged(boolean isIncrease, String key, String traitCategory) {
            this.isIncrease = isIncrease;
            this.key = key;
            this.category = traitCategory;
        }
    }

    public static class SkillChanged {
        public final String key;
        public final String category;
        public final boolean isIncrease;

        public SkillChanged(boolean isIncrease, String key, String traitCategory) {
            this.isIncrease = isIncrease;
            this.key = key;
            this.category = traitCategory;
        }
    }

    public static class ValueChanged {
        public final String key;
        public final String kind;
        public final String category;
        public final boolean isIncrease;

        public ValueChanged(boolean isIncrease, String key, String kind, String traitCategory) {
            this.isIncrease = isIncrease;
            this.key = key;
            this.kind = kind;
            this.category = traitCategory;
        }
    }

    public static class WizardComplete {
        public WizardComplete() { }
    }

    public static class WizardClose {
        public WizardClose() { }
    }

    public static class SpecialtyClicked {
        public final boolean isChecked;
        public final String key;
        public final String category;
        public final String specialtyName;

        public SpecialtyClicked(boolean isChecked, String key, String category, String specialtyName) {
            this.category = category;
            this.isChecked = isChecked;
            this.key = key;
            this.specialtyName = specialtyName;
        }
    }

    public static class SpecialtyDialogClosing {
        public final String key;

        public SpecialtyDialogClosing(String key) {
            this.key = key;
        }
    }

    public static class SpecialtyTapped {
        public final String key;
        public final String value;

        public SpecialtyTapped(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    public static class WizardProgressEvent {
        public final int currentItem;
        public final boolean movesForward;

        public WizardProgressEvent(int currentItem, boolean movesForward) {
            this.currentItem = currentItem;
            this.movesForward = movesForward;
        }
    }

    public static class InfoFragmentLoaded {
        public InfoFragmentLoaded() {}
    }

    public static class AttrsFragmentLoaded {
        public AttrsFragmentLoaded() {}
    }

    public static class SkillsFragmentLoaded {
        public SkillsFragmentLoaded() {}
    }

    public static class MeritsFragmentLoaded {
        public MeritsFragmentLoaded() {}
    }

    public static class MeritValueChanged {
        public MeritsAdapter.ViewHolder holder;
        public Rule rule;
        public boolean isIncrease;

        public MeritValueChanged(MeritsAdapter.ViewHolder holder, Rule rule, boolean isIncrease) {
            this.holder = holder;
            this.rule = rule;
            this.isIncrease = isIncrease;
        }
    }
}
