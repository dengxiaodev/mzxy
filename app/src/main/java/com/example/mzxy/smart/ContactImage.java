package com.example.mzxy.smart;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/13 0013.
 */

public class ContactImage implements SmartImage {
    private long contactId;

    public ContactImage(long contactId) {
        this.contactId = contactId;
    }

    public Bitmap getBitmap(Context context) {
        Bitmap bitmap = null;
        ContentResolver contentResolver = context.getContentResolver();

        try {
            Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(contentResolver, uri);
            if(input != null) {
                bitmap = BitmapFactory.decodeStream(input);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}