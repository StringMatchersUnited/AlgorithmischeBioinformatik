package de.huberlin.algobio.ws1314.gruppe2.io;

public class FASTASequence
{
    public byte[] description;
    public byte[] comment;
    public byte[] sequence;

    @Override
    public String toString()
    {
        return "description length: " + description.length + ", comment length: " + comment.length +
               ", sequence length: " + sequence.length;
    }
}

