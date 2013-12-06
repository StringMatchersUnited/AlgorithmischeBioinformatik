package de.huberlin.algobio.ws1314.gruppe2.tools;

import java.io.Serializable;
import java.util.ArrayList;


public class IntArray implements Serializable
{
    ArrayList<int[]> arr      = new ArrayList<int[]>();
    Integer          capacity = 1000;
    Integer          position = 0;


    public IntArray()
    {
        arr.add(new int[capacity]);
    }

    public IntArray( int capacity )
    {
        this.capacity = capacity;
        arr.add(new int[capacity]);
    }


    public void add( int i )
    {
        int arrIndex = position / capacity;
        int posIndex = position % capacity;
        if ( arrIndex >= arr.size() )
        {
            arr.add(new int[capacity]);
        }

        arr.get(arrIndex)[posIndex] = i;
        ++position;
    }

    public Integer get( int i )
    {
        int arrIndex = i / capacity;
        int posIndex = i % capacity;

        if ( arrIndex >= arr.size() )
            return null;

        return arr.get(arrIndex)[posIndex];
    }

    public int size()
    {
        return position;
    }
}
