package com.anish.calabashbros;

public class SelectSorter<T extends Comparable<T>> implements Sorter<T> {

    private T[][] a;

    @Override
    public void load(T[][] a) {
        this.a = a;
    }

    private void swap(int i, int j) {
        T temp;
        temp = a[i/16][i%16];
        a[i/16][i%16] = a[j/16][j%16];
        a[j/16][j%16] = temp;
        plan += "" + a[i/16][i%16] + "<->" + a[j/16][j%16] + "\n";
    }

    private String plan = "";

    @Override
    public void sort() {
        for(int i = 0; i < a.length * a.length - 1; i++) {
            int k = i;
            for(int j = k + 1; j < a.length * a.length; j++){
                if(a[j/16][j%16].compareTo(a[k/16][k%16]) < 0){
                    k = j;
                }
            }
            if(i != k){
                swap(i,k);
            }
        }
    }

    @Override
    public String getPlan() {
        return this.plan;
    }

}