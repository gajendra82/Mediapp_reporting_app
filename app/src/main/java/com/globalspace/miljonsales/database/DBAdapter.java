package com.globalspace.miljonsales.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.globalspace.miljonsales.model.AddProductToCart;
import com.globalspace.miljonsales.model.discountProductInfo;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import static com.globalspace.miljonsales.database.Constants.TABLE_GST_ADDTOCART;
import static com.globalspace.miljonsales.database.Constants.TABLE_GST_ADDTOCART_PRODUCT_MASTER;


public class DBAdapter extends SQLiteOpenHelper {

    /******************** if debug is set true then it will show all Logcat message ************/
    public static final boolean DEBUG = true;

    /******************** Logcat TAG ************/
    public static final String LOG_TAG = "DBAdapter";


    /******************** Database Name ************/
    public static final String DATABASE_NAME = "DB_Reporting";

    /******************** Database Version (Increase one if want to also upgrade your database) ************/
    public static final int DATABASE_VERSION = 1;

    /******************** Set all table with comma seperated like USER_TABLE,ABC_TABLE ************/
    private static final String[] ALL_TABLES = {
            TABLE_GST_ADDTOCART_PRODUCT_MASTER, TABLE_GST_ADDTOCART
    };


    public DBAdapter(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (DEBUG)
            Log.i(LOG_TAG, "new create");
        try {

            db.execSQL(createAddToCartproductmaster);
            db.execSQL(createAddToCart);


            // db.execSQL(createTablecustomertypeherierchyMaster);

        } catch (Exception exception) {
            if (DEBUG)
                Log.i(LOG_TAG, "Exception onCreate() exception");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //region Table Add to cart
    private static final String createAddToCartproductmaster = "CREATE TABLE "
            + TABLE_GST_ADDTOCART_PRODUCT_MASTER + "(" + Constants.ADDTOCART_PRODUCT_ID
            + " TEXT PRIMARY KEY ," + Constants.ADDTOCART_PRODUCT_NAME + " TEXT,"
            + Constants.ADDTOCART_MANUFACTURE_ID + " INTEGER   ,"
            + Constants.ADDTOCART_SELECTED_MANUFACTURE_NAME + " TEXT ,"
            + Constants.ADDTOCART_TAX_NAME + " TEXT,"
            + Constants.ADDTOCART_DISCOUNT + " TEXT,"
            + Constants.ADDTOCART_MINIMUM_QTY + " TEXT,"
            + Constants.ADDTOCART_TAX_VALUE + " TEXT,"
            + Constants.ADDTOCART_SELECTED_QUANTITY + " INTEGER ,"
            + Constants.ADDTOCART_SELECTED_PRD_PRICE + " TEXT ," + Constants.ADDTOCART_SELECTED_PRD_PTR + " TEXT ,"
            + Constants.ADDTOCART_SELECTED_PRD_SCHEME_VALUE + " TEXT ,"
            + Constants.ADDTOCART_SELECTED_PRD_SCHEME_QTY + " TEXT ,"
            + Constants.ADDTOCART_SESSION_ID + " TEXT ,"
            + Constants.ADDTOCART_SCHEME_ID + " TEXT ,"
            + Constants.ADDTOCART_SELECTED_PRD_PTSP + " TEXT ,"
            + Constants.ADDTOCART_SELECTED_PRD_IMAGES + " TEXT ,"
            + Constants.MINIMUM_AMOUNT + " TEXT ,"
            + Constants.FREE_PROD_IDS + " TEXT ,"
            + Constants.FREE_PROD_QUANTITY + " TEXT ,"
            + Constants.FREE_PROD_NAME + " TEXT ,"
            + Constants.ADDTOCART_SELECTED_PRD_PTS + " TEXT ," + Constants.ADDTOCART_FLAG + " TEXT ) ;";


    private static final String createAddToCart = "CREATE TABLE "
            + TABLE_GST_ADDTOCART + "(" + Constants.ADDTOCART_PRODUCT_ID
            + " TEXT PRIMARY KEY ," + Constants.ADDTOCART_PRODUCT_NAME + " TEXT) ;";

    //endregion


    public void addProductsToCart(discountProductInfo productData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String check = "SELECT * FROM " + TABLE_GST_ADDTOCART_PRODUCT_MASTER + " WHERE " + Constants.ADDTOCART_PRODUCT_ID + " =? ;";
        Cursor cursor = db.rawQuery(check, new String[]{productData.getProductId()});
        if (cursor.getCount() <= 0) {
            ContentValues values = new ContentValues();
            values.put(Constants.ADDTOCART_PRODUCT_ID, productData.getProductId());
            values.put(Constants.ADDTOCART_PRODUCT_NAME, productData.getProdName());
            values.put(Constants.ADDTOCART_SELECTED_QUANTITY, 1);
            values.put(Constants.ADDTOCART_SELECTED_PRD_PRICE, productData.getProdMRP());
            values.put(Constants.ADDTOCART_SELECTED_PRD_PTR, productData.getProdPTR());
            values.put(Constants.ADDTOCART_SELECTED_PRD_PTS, productData.getProdPTS());
            values.put(Constants.ADDTOCART_SCHEME_ID, productData.getScheme_id());
            values.put(Constants.ADDTOCART_SELECTED_PRD_SCHEME_QTY, productData.getSchemeQuantity());
            values.put(Constants.ADDTOCART_SELECTED_PRD_SCHEME_VALUE, productData.getSchemeValue());
            values.put(Constants.ADDTOCART_DISCOUNT, productData.getDiscount());
            values.put(Constants.ADDTOCART_TAX_NAME, productData.getTaxName());
            values.put(Constants.ADDTOCART_TAX_VALUE, productData.getTaxValue());
            db.insert(TABLE_GST_ADDTOCART_PRODUCT_MASTER, null, values);
        }
        cursor.close();
        db.close();

    }

    public void deleteProductFromCart(String productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GST_ADDTOCART_PRODUCT_MASTER, Constants.ADDTOCART_PRODUCT_ID + "=?", new String[]{productId});
        db.close();

    }

    public ArrayList<AddProductToCart> getAllCartProducts() {
        ArrayList<AddProductToCart> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String getAll = "SELECT * FROM " + TABLE_GST_ADDTOCART_PRODUCT_MASTER + ";";
        Cursor cursor = db.rawQuery(getAll, null);
        if (cursor.moveToFirst()) {
            do {
                AddProductToCart data = new AddProductToCart();
                data.setProductId(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_PRODUCT_ID)));
                data.setProductName(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_PRODUCT_NAME)));
                data.setMrp(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_SELECTED_PRD_PRICE)));
                data.setSelectedQuantity(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_SELECTED_QUANTITY)));
                data.setPts(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_SELECTED_PRD_PTS)));
                data.setSchemeId(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_SCHEME_ID)));
                data.setSchemeQuantity(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_SELECTED_PRD_SCHEME_QTY)));
                data.setSchemeValue(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_SELECTED_PRD_SCHEME_VALUE)));
                data.setTaxValue(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_TAX_VALUE)));
                data.setDiscount(cursor.getString(cursor.getColumnIndex(Constants.ADDTOCART_DISCOUNT)));
                list.add(data);
            } while (cursor.moveToNext());
        }


        return list;
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String check = "DELETE FROM " + TABLE_GST_ADDTOCART_PRODUCT_MASTER + ";";
        db.execSQL(check);
        db.close();

    }

    public void updateCartQuantity(String productId, String qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.ADDTOCART_SELECTED_QUANTITY, qty);
        db.update(TABLE_GST_ADDTOCART_PRODUCT_MASTER, values, Constants.ADDTOCART_PRODUCT_ID + " =?", new String[]{productId});
        db.close();
    }


    public long cartSize() {
        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(db, TABLE_GST_ADDTOCART_PRODUCT_MASTER);
        db.close();
        return count;
    }


}
