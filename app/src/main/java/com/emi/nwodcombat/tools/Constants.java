package com.emi.nwodcombat.tools;

/**
 * Created by Emi on 2/18/16.
 */
public class Constants {
    private Constants() {
        throw new AssertionError("This class is NOT MEANT FOR INSTANTIATION!");
    }

    public static final boolean MODE_TEST = true;

    public static final String TYPEFACE_FOLDER = "fonts/";
    public static final String TYPEFACE_EXTENSION_TTF = ".ttf";

    public static final String TAG_FRAG_CHARACTER_CREATOR_PAGER = "characterCreatorPager";
    public static final String TAG_FRAG_CHARACTER_VIEWER = "characterViewerFragment";
    public static final String TAG_FRAG_CHARACTER_LIST = "characterListFragment";
    public static final String TAG_FRAG_SETTINGS = "settingsFragment";
    public static final String TAG_SHARED_PREFS = "sharedPrefs";

    public static final String SETTING_CHEAT = "cheat";
    public static final String SETTING_IGNORE_STAT_CAPS = "ignoreStatCaps";

    public static final String DICE_RULE_8_AGAIN = "8-again";
    public static final String DICE_RULE_9_AGAIN = "9-again";
    public static final String DICE_RULE_10_AGAIN = "10-again";
    public static final String DICE_RULE_NO_AGAIN = "No rerolls";
    public static final Integer DICE_VALUE_8_AGAIN = 8;
    public static final Integer DICE_VALUE_9_AGAIN = 9;
    public static final Integer DICE_VALUE_10_AGAIN = 10;
    public static final Integer DICE_VALUE_NO_AGAIN = 11;

    public static final String CONTENT_DESC_NBPK_BTN_MINUS = "Dice decrease for: ";
    public static final String CONTENT_DESC_NBPK_BTN_PLUS = "Dice increase for: ";
    public static final String CONTENT_DESC_ATTR_MENTAL = "Mental";
    public static final String CONTENT_DESC_ATTR_PHYSICAL = "Physical";
    public static final String CONTENT_DESC_ATTR_SOCIAL = "Social";
    public static final String CONTENT_DESC_SKILL_MENTAL = "Mental";
    public static final String CONTENT_DESC_SKILL_PHYSICAL = "Physical";
    public static final String CONTENT_DESC_SKILL_SOCIAL = "Social";

    public static final int ATTR_PTS_PRIMARY = 5;
    public static final int ATTR_PTS_SECONDARY = 4;
    public static final int ATTR_PTS_TERTIARY = 3;

    public static final int SKILL_PTS_PRIMARY = 11;
    public static final int SKILL_PTS_SECONDARY = 7;
    public static final int SKILL_PTS_TERTIARY = 4;

    public static final int ABSOLUTE_MINIMUM_ATTR = 1;
    public static final int ABSOLUTE_MINIMUM_SKILL = 0;

    public static final String CHARACTER_NAME = "Name";
    public static final String CHARACTER_CONCEPT = "Concept";
    public static final String CHARACTER_PLAYER = "Player";
    public static final String CHARACTER_VICE = "Vice";
    public static final String CHARACTER_VIRTUE = "Virtue";
    public static final String CHARACTER_DEMEANOR = "Demeanor";
    public static final String CHARACTER_NATURE = "Nature";
    public static final String CHARACTER_BEATS = "Beats";
    public static final String CHARACTER_EXPERIENCE = "Experience";

    public static final String ATTR_INT = "Intelligence";
    public static final String ATTR_WIT = "Wits";
    public static final String ATTR_RES = "Resolve";
    public static final String ATTR_STR = "Strength";
    public static final String ATTR_DEX = "Dexterity";
    public static final String ATTR_STA = "Stamina";
    public static final String ATTR_PRE = "Presence";
    public static final String ATTR_MAN = "Manipulation";
    public static final String ATTR_COM = "Composure";

    public static final String SKILL_ACADEMICS = "Academics";
    public static final String SKILL_COMPUTER = "Computer";
    public static final String SKILL_CRAFTS = "Crafts";
    public static final String SKILL_INVESTIGATION = "Investigation";
    public static final String SKILL_MEDICINE = "Medicine";
    public static final String SKILL_OCCULT = "Occult";
    public static final String SKILL_POLITICS = "Politics";
    public static final String SKILL_SCIENCE = "Science";

    public static final String SKILL_ATHLETICS = "Athletics";
    public static final String SKILL_BRAWL = "Brawl";
    public static final String SKILL_DRIVE = "Drive";
    public static final String SKILL_FIREARMS = "Firearms";
    public static final String SKILL_LARCENY = "Larceny";
    public static final String SKILL_STEALTH = "Stealth";
    public static final String SKILL_SURVIVAL = "Survival";
    public static final String SKILL_WEAPONRY = "Weaponry";

    public static final String SKILL_ANIMAL_KEN = "Animal Ken";
    public static final String SKILL_EMPATHY = "Empathy";
    public static final String SKILL_EXPRESSION = "Expression";
    public static final String SKILL_INTIMIDATION = "Intimidation";
    public static final String SKILL_PERSUASION = "Persuasion";
    public static final String SKILL_SOCIALIZE = "Socialize";
    public static final String SKILL_STREETWISE = "Streetwise";
    public static final String SKILL_SUBTERFUGE = "Subterfuge";

    public static final String TRAIT_MORALITY = "Morality";
    public static final String TRAIT_DERIVED_DEFENSE = "Defense";
    public static final String TRAIT_DERIVED_HEALTH = "Health";
    public static final String TRAIT_DERIVED_INITIATIVE = "Initiative";
    public static final String TRAIT_DERIVED_SPEED = "Speed";
    public static final String TRAIT_DERIVED_WILLPOWER = "Willpower";

    public static final int NO_OPTION_SELECTED = -1;
    public static final int SKILL_SPECIALTIES_STARTING = 3;
    public static final int TRAIT_MORALITY_DEFAULT = 7;
    public static final int TRAIT_SIZE_DEFAULT = 5;

    public static final String FIELD_ID = "id";
    public static final String FIELD_KEY = "key";
    public static final String FIELD_VALUE = "value";
    public static final String FIELD_NAMESPACE = "namespace";

    public static final String FIELD_TYPE_STRING = "String";
    public static final String FIELD_TYPE_INTEGER = "Integer";

    public static final String FIELD_TRAIT_ORDINAL = "ordinal";

    public static final String TITLE_STEP_PERSONAL_INFO = "Personal info";
    public static final String TITLE_STEP_POINTS_SET = "Distribute points";

    public static final String SKILL_SPECIALTY = "Skill specialty";
    public static final String SKILL_SPECIALTY_EMPTY = "has no specialties";
    public static final String SKILL_SPECIALTY_LOADED = "has specialties";

    public static final String MENTAL = "Mental";
    public static final String PHYSICAL = "Physical";
    public static final String SOCIAL = "Social";
    public static final String POWER = "Power";
    public static final String FINESSE = "Finesse";
    public static final String RESISTANCE = "Resistance";

    public static final String MERIT_DREAM = "Dream";
    public static final String MERIT_DREAM_HINT = "Your character can search for answers within her dreams.";
    public static final String MERIT_DREAM_DESC = "Your character can dig within her dreams for prophetic answers to primordial truths. She may enter her own dreams without a meditation roll when she sleeps, and if she has a basic understanding of something she wishes to divine from her dreams, you may use this Merit. Your character must sleep or meditate for at least four hours. Then, ask the Storyteller a yes or no question about the topic at hand. He must answer accurately, but can use \"maybe\" if the answer is truly neither yes or no. Depending on the answer, you may ask additional questions, up to your Dream Merit dots. You can use that many questions per chapter.";
    public static final String MERIT_TRAINED_OBSERVER = "Trained Observer";
    public static final String MERIT_TRAINED_OBSERVER_HINT = "Your character has spent years in the field, catching tiny details and digging for secrets.";
    public static final String MERIT_TRAINED_OBSERVER_DESC = "Your character has spent years in the field, catching tiny details and digging for secrets.";
    public static final String MERIT_COMMON_SENSE = "Common Sense";
    public static final String MERIT_COMMON_SENSE_HINT = "Your character has an exceptionally sound and rational mind.";
    public static final String MERIT_COMMON_SENSE_DESC = "Your character has an exceptionally sound and rational mind. Within a moment\'s thought, she can weigh potential courses of action and outcomes.";

    public static final String NAMESPACE_ATTRIBUTE = "Attribute";
    public static final String NAMESPACE_SKILL = "Skill";
    public static final String NAMESPACE_AWAKENED = "Awakened";
    public static final String NAMESPACE_MERIT = "Merit";
    public static final String NAMESPACE_COMMON = "Common";
    public static final String NAMESPACE_ADVANTAGE = "Advantage";
    public static final String NAMESPACE_PERSONAL_INFO = "Personal info";
}
