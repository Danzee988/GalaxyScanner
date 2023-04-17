package Methods;

public class unionFind {

    //Recursive version of find
    public static int find(int[] a, int id) {
        if(a[id]<0) return a[id];
        if (a[id] == id) return id;
        else return find(a, a[id]);
    }
    //Quick union of disjoint sets containing elements p and q (Version 1)
    public static void union(int[] a, int p, int q) {

        a[find(a,q)]=find(a,p); //The root of q is made reference p
    }
}

