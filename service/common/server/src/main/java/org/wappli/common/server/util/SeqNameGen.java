package org.wappli.common.server.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class SeqNameGen {
    public final String seqName(final Class clazz) {
        ArrayList<String> words = splitCamelCaseString(clazz.getSimpleName());
        StringBuilder sb = new StringBuilder();

        sb.append(StringUtils.lowerCase(words.get(0)));

        for (int i = 1; i < words.size(); i++) {
             String word = words.get(i);

             sb.append("_");
             sb.append(StringUtils.lowerCase(word));
        }

        return "seq_" + sb.toString() + "_id";
    }

    private static ArrayList<String> splitCamelCaseString(String s){
        ArrayList<String> result = new ArrayList<String>();
        for (String w : s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            result.add(w);
        }

        return result;
    }
}
