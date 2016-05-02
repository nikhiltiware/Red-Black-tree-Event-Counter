package com.Nikhil;

import java.io.*;
import java.util.Scanner;

public class bbst {

    public static void main(String[] args) {
	// write your code here

        RBEventTree RBobj = new RBEventTree();
        try {
            File file = new File(args[0]);
            String line = null;
            FileReader fileReader =
                    new FileReader(file);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            int T = Integer.parseInt(bufferedReader.readLine());

            int[] idArray = new int[T];
            int[] countArray = new int[T];
            int i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] str = line.split(" ");
                idArray[i] = Integer.parseInt(str[0]);
                countArray[i++] = Integer.parseInt(str[1]);
            }
            RBobj.intializer(idArray,countArray);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            Scanner sc = new Scanner(System.in);
            String fileName = sc.nextLine();
            File file = new File(fileName);

            sc = new Scanner(file);

           // int T = Integer.parseInt(sc.nextLine());
            while (sc.hasNextLine()) {
                String[] str = sc.nextLine().split(" ");

                String command = str[0];
                switch (command)
                {
                    case "increase" :
                        RBobj.increase(Integer.parseInt(str[1]),Integer.parseInt(str[2]));
                        break;

                    case "reduce" :
                        RBobj.reduce(Integer.parseInt(str[1]),Integer.parseInt(str[2]));
                        break;

                    case "count" :
                        RBobj.count(Integer.parseInt(str[1]));
                        break;

                    case "inrange" :
                        RBobj.inrange(Integer.parseInt(str[1]),Integer.parseInt(str[2]));
                        break;

                    case "next" :
                        RBobj.next(Integer.parseInt(str[1]));
                        break;

                    case "previous" :
                        RBobj.previous(Integer.parseInt(str[1]));
                        break;

                    case "quit" :
                        break;

                }
            }
            sc.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        

    }
}
