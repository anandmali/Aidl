// IAidlDemoInterface.aidl
package com.anand.aidlserver;

// Declare any non-default types here with import statements
//Package structure of client aidl should be same server aidl
interface IAidlDemoInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    //Server aidl abstract method
    int checkAnagram(String childString);
}
