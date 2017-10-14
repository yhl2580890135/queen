package HufMan;

public class Word implements Comparable<Word>{
    public Byte symbol;
    public double frequency;

    public Word(Byte symbol, double frenquency) {
        this.symbol = symbol;
        this.frequency = frenquency;
    }

    public Word() {
    }

    public int compareTo(Word word) {
        if(this.frequency <word.frequency)
            return 1;
        if(this.frequency >word.frequency)
            return -1;
        return 0;
    }
}
