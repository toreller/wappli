package org.wappli.admin.server.domain;

import org.junit.Test;
import org.wappli.common.server.util.SeqNameGen;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static org.junit.Assert.*;

public class SeqNameGenTest {

    @Test
    public void testGenSeqName() {
        SeqNameGen seqNameGen = new SeqNameGen();

        assertEquals("seq_seq_name_gen_test_id", seqNameGen.seqName(SeqNameGenTest.class));
        assertEquals("seq_system_id", seqNameGen.seqName(System.class));
    }

    @Test
    public void testSeqNames() throws IOException, ClassNotFoundException, IllegalAccessException {
        Class[] domainClasses = getClasses("org.wappli.admin.server.domain");
        SeqNameGen seqNameGen = new SeqNameGen();

        for (Class domainClass : domainClasses) {
            Field field;

            try {
                field = domainClass.getDeclaredField("SEQ_NAME");
            } catch (NoSuchFieldException e) {
                continue;
            }

            field.setAccessible(true);
            assertEquals("incorrect sequence name for entity " + domainClass.getSimpleName(), seqNameGen.seqName(domainClass), field.get(null));
        }
    }

    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }
}