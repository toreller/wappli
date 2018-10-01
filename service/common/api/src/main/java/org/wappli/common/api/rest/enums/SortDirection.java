package org.wappli.common.api.rest.enums;

public enum SortDirection {
    ASC("asc"), DESC("desc");

    private String urlEncodedName;

    SortDirection(String urlEncodedName) {
        this.urlEncodedName = urlEncodedName;
    }

    public String getUrlEncodedName() {
        return urlEncodedName;
    }

    public static SortDirection fromUrlEncodedName(String urlEncodedName){
        if(urlEncodedName != null) {
            switch (urlEncodedName.toLowerCase()) {
                case "asc": return ASC;
                case "desc": return DESC;
                default: return null;
            }
        }
        return null;
    }
}
