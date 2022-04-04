package com.example.mygeoquiz.struct;

public class pEntry implements Comparable<pEntry> {
    public int key;
    public String value;

    public pEntry(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey(){
        return this.key;
    }

    @Override
    public int compareTo(pEntry other) {
        if(this.getKey() < other.getKey()){
            return -1;
        }
        if(this.getKey() > other.getKey()){
            return 1;
        }
        return 0;
    }

}
