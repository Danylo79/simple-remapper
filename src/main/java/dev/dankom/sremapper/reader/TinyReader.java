package dev.dankom.sremapper.reader;

import dev.dankom.file.type.Directory;
import dev.dankom.sremapper.reader.base.MappingReader;
import dev.dankom.sremapper.util.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TinyReader extends MappingReader {
    public TinyReader(File in) {
        super(in);
    }

    @Override
    public void read() {
        if (!in.isDirectory()) {
            return;
        }

        Directory dir = new Directory(in.getAbsolutePath());
        dir.getFiles().forEach(file -> {
            if (file.getName().endsWith(".mapping")) readFile(file);
        });
    }

    public void readFile(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fr);

            boolean loop = true;
            while (loop) {
                String s = buffer.readLine();

                if (s != null && !s.isEmpty()) {
                    String[] parts = s.split(" ");
                    String[] trimmedParts = s.trim().split(" ");
                    if (s.startsWith("\t")) {
                        if (s.startsWith("\t\t")) {
                            String argName = trimmedParts[2];
                            System.out.println("Arg: " + argName);
                        } else {
                            if (trimmedParts[0].equals("FIELD")) {
                                String fieldName = trimmedParts[2];
                                String obfName = trimmedParts[1];
                                String returnType = trimmedParts[3];
                                System.out.println("Field: " + fieldName + " -> " + obfName);
                            } else if (trimmedParts[0].equals("METHOD")) {
                                if (trimmedParts.length > 2) {
                                    String methodName = trimmedParts[1];
                                    String returnType = StringUtil.getAfter(trimmedParts[2], ')');
                                    String params = StringUtil.getBetween(trimmedParts[2], '(', ')');
                                    System.out.println("Method: " + methodName);
                                } else {
                                    String methodName = trimmedParts[2];
                                    String obfName = trimmedParts[1];
                                    String returnType = StringUtil.getAfter(trimmedParts[3], ')');
                                    String params = StringUtil.getBetween(trimmedParts[3], '(', ')');
                                    System.out.println("Method: " + methodName + " -> " + obfName);
                                }
                            }
                        }
                    } else {
                        String className = parts[2];
                        String obfName = parts[1];
                        System.out.println("Class: " + className + " -> " + obfName);
                    }
                } else loop = false;
            }

            buffer.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TinyReader reader = new TinyReader(new File("./test"));
        reader.read();
    }
}
