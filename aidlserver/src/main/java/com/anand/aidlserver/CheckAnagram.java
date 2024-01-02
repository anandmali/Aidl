package com.anand.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.HashMap;

/**
 * Aidl server service
 * This class gives number of occurrence of string if the given string from Aidl Client application
 * is Anagram of or not wrt string in aidl server application
 */
public class CheckAnagram extends Service {

    //Parent string
    private String letters = "AdnBndAndBdaBn";

    public CheckAnagram() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return (IBinder) anInterface;
    }

    private IAidlDemoInterface anInterface = new IAidlDemoInterface.Stub() {
        @Override
        public int checkAnagram(String childString) throws RemoteException {
            Log.e("Int ", occurrence(letters, childString)+"");
            return occurrence(letters, childString);
        }
    };


    //Return number of occurrence of
    private int occurrence(String parent, String child) {
        int child_hash = hash(child);

        int start = 0;
        int end = start + child.length();

        int occurred = 0;
        while (end < parent.length()) {
            String sub = parent.substring(start, end);
            if (hash(sub) == child_hash) {
                occurred++;
            }
            start++;
            end++;
        }
        return occurred;
    }

    int hash(String str) {
		/*
		 * Map letters to prime numbers... the Java way. :(
		 * We should only do this once and cache the result for
		 * better performance.
		 */
        HashMap<Character, Integer> letter_map = new HashMap<>();
        Integer[] primes = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
        for (int i = 0; i < letters.length(); ++i) {
            // Zip anyone?
            letter_map.put(letters.charAt(i), primes[i]);
        }

        int result = 0;
        str = str.toLowerCase(); // Don't care about uppercase
        for (char c : str.toCharArray()) {
            result += letter_map.get(c);
        }
        return result;
    }

    /**
     * This class contains aidl server response to aidl client
     */
//    class AidlImp extends IAidlDemoInterface.Stub {
//        @Override
//        public int checkAnagram(String childString) throws RemoteException {
//            return occurrence(letters, childString);
//        }
//
//        //Return number of occurrence of
//        private int occurrence(String parent, String child) {
//            int child_hash = hash(child);
//
//            int start = 0;
//            int end = start + child.length();
//
//            int occurred = 0;
//            while (end < parent.length()) {
//                String sub = parent.substring(start, end);
//                if (hash(sub) == child_hash) {
//                    occurred++;
//                }
//                start++;
//                end++;
//            }
//            return occurred;
//        }
//
//        int hash(String str) {
//		/*
//		 * Map letters to prime numbers... the Java way. :(
//		 * We should only do this once and cache the result for
//		 * better performance.
//		 */
//            HashMap<Character, Integer> letter_map = new HashMap<>();
//            Integer[] primes = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,101};
//            for (int i = 0; i < letters.length(); ++i) {
//                // Zip anyone?
//                letter_map.put(letters.charAt(i), primes[i]);
//            }
//
//            int result = 0;
//            str = str.toLowerCase(); // Don't care about uppercase
//            for (char c : str.toCharArray()) {
//                result += letter_map.get(c);
//            }
//            return result;
//        }
//    }
}
