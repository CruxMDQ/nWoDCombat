package com.emi.nwodcombat.tools;

/**
 * Created by emiliano.desantis on 28/03/2016.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.text.util.Linkify;
import android.widget.TextView;

import com.emi.nwodcombat.Constants;

import java.util.Hashtable;
import java.util.regex.Pattern;

public class TypefaceHelper {

    // store the opened typefaces(fonts)
    private static final Hashtable<String, Typeface>  mCache = new Hashtable<String, Typeface>();

    /**
     * Load the given font from assets
     *
     * @param fontName font name
     * @return Typeface object representing the font painting
     */
    public static Typeface loadFontFromAssets(Context context, String fontName) {

        // make sure we load each font only once
        synchronized (mCache) {
            if (!mCache.containsKey(fontName)) {
                String fontNameWithExtension = Constants.TYPEFACE_FOLDER + fontName + Constants.TYPEFACE_EXTENSION_TTF;

                Typeface typeface = Typeface.createFromAsset(context.getAssets(), fontNameWithExtension);
                mCache.put(fontName, typeface);
            }

            return mCache.get(fontName);
        }
    }

    /**
     * Convert the given linkedText in the given TextView to be a clickable link with the given scheme.
     */
    public static void linkifyTextView(TextView textView, String scheme, String linkedText) {
        textView.setAutoLinkMask(0);
        Pattern pattern = Pattern.compile(linkedText);
        Linkify.addLinks(textView, pattern, scheme);
    }
}