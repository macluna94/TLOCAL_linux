package com.example.tlocal_linux.DBHelper;

public class SqlConstants {


    //public static final String DB_NAME = ;
    public static final String DB_NAME ="dblocales";
    public static final String NameTable= "locales";

    public static final String ID_LOCAL ="id";
    public static final String NAME_LOCAL ="nameLocal";
    public  static final String DESCRIPTION_LOCAL ="description";
    public  static final String ADDRESS_LOCAL ="address";
    public  static final String TEL_LOCAL ="phoneNumber";
    public  static final String PERSONA_LOCAL ="namePersonal";
    public  static final String TIME_LOCAL = "openTime";
    public  static final String CATEGORY_LOCAL = "category";
    public  static final String DELIVERY_LOCAL ="delivery";
    public  static final String IMAGE = "dirImage";

    public static final String CREATE_TABLE = "CREATE TABLE " + NameTable  + " ( "
            + ID_LOCAL + " TEXT, "
            + NAME_LOCAL + " TEXT, "
            + DESCRIPTION_LOCAL + " TEXT, "
            + ADDRESS_LOCAL + " TEXT, "
            + TEL_LOCAL + " TEXT, "
            + PERSONA_LOCAL + " TEXT, "
            + TIME_LOCAL + " TEXT, "
            + CATEGORY_LOCAL + " TEXT, "
            + DELIVERY_LOCAL + " TEXT, "
            + IMAGE + " TEXT) ";


}
