public class FASTASequence
{
    public byte[] description;
    public int    descriptionLength;
    public byte[] comment;
    public int    commentLength;
    public byte[] sequence;
    public int    sequenceLength;

    @Override
    public String toString()
    {
        return "description length: " + description.length + ", comment length: " + comment.length +
               ", sequence length: " + sequence.length;
    }
}

