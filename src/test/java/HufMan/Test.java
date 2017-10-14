package HufMan;

import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Test {
    @org.junit.Test
    public Word[] init(){
        Byte[] bytes = new Byte[]{'Y', 'H', 'L'};
        double[] frenqucies = new double[]{0.5, 0.3, 0.1};
        Word[] words = new Word[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            words[i] = new Word(bytes[i], frenqucies[i]);
        }
        return words;
    }
    public void buildTest(Word[] words) {
        TreeNode root = new Hufman().buildTree(words);
    }

    public void getCodeTest(Word[] words) {
        TreeNode root =new Hufman().buildTree(words);
        HashMap<Byte, String> map = new HashMap<>();
        new Hufman().getCode(root, "", map);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
     new Hufman().compress("F:\\yhl.txt","F:\\yhl.rar");
     new Hufman().extract("F:\\yhl.rar","F:\\wzj.txt");
    }

}
