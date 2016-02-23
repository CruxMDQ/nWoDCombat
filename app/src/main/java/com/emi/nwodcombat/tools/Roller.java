package com.emi.nwodcombat.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Toolkit for handling dice rolls.
 * Created by emiliano.desantis on 08/05/2015.
 */
@SuppressWarnings("unused")
public class Roller {

    static private Random mRand = new Random();

    static public int rollDnDDice(int dice, int size, int discard, int modifier) {
        int total = rollDnDDice(dice, size, discard);

        total += modifier;

        return total;
    }

    /**
     * Rolling dice in the fashion of Dungeons and Dragons.
     * @param dice Number of dice to roll.
     * @param size Size of the die rolled.
     * @param discard Number of dice to discard.
     * @return Total sum of the values of all dice rolled.
     */
    static public int rollDnDDice(int dice, int size, int discard) {
        int total = 0;

        Integer[] rolls = new Integer[dice];

        // Source: http://stackoverflow.com/questions/363681/
        for (int i = 0; i < dice; i++) {
            rolls[i] = Roller.randInt(size);
        }

        // Source: http://stackoverflow.com/questions/8938235/
        Arrays.sort(rolls, Collections.reverseOrder());

        for (int i = 0; i < 3; i++) {
            total += rolls[i];
        }

        return total;
    }

    /**
     * Rolling dice in the fashion of World of Darkness. Rerolls all values equal or higher than threshold.
     * @param dice Number of dice to roll.
     * @param size Size of the die rolled.
     * @return Array containing all the dice rolled.
     */
    static public ArrayList<Integer> rollNWoDDice(int dice, int size, int threshold) {
        Random random = new Random();

        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < dice; i++) {
            Integer integer = Roller.randInt(size);

            if (integer >= threshold) {
                dice++;
            }

            result.add(integer);
        }

        return result;
    }

    static public int randInt(int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
//        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return mRand.nextInt((max - 1) + 1) + 1;
    }

}
