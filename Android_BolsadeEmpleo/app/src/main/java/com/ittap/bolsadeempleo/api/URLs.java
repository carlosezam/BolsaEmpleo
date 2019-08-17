package com.ittap.bolsadeempleo.api;


public class URLs {
    private static final String SITE_URL = "http://kodyms.tapachulaenlinea.com/bolsa/web/";
    //public static final String SITE_URL = "http://192.168.173.1/bolsa/web/";
    public static final String URI_CURRICULA = SITE_URL + "api/curricula/";
    public static final String URI_PDF = SITE_URL + "site/pdf";
    public static final String URI_EMPLEOS = URI_CURRICULA + "empleos";

    public static final String URI_SIGNUP = SITE_URL + "api/usuarios";
    public static final String URI_LOGIN = SITE_URL + "api/usuarios/login";



    public static final String CURRICULA_CREATE = URI_CURRICULA + "create";
    public static final String CURRICULA_READ = URI_CURRICULA + "read";
    public static final String CURRICULA_UPDATE = URI_CURRICULA + "update";
    public static final String CURRICULA_DELETE = URI_CURRICULA + "delete";
    public static final String CURRICULA_VIEW = URI_CURRICULA + "view";
    public static final String CURRICULA_UPLOAD = URI_CURRICULA + "upload";
    public static final String CURRICULA_IMAGE = SITE_URL + "fotos/";
}
