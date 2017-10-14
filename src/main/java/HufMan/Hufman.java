package HufMan;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Hufman {
    public TreeNode<Word> buildTree(Word[] words) {
        Comparator<TreeNode<Word>> comparator = (o1, o2) -> {
            if (o1.data.frequency < o2.data.frequency)
                return -1;
            if (o1.data.frequency > o2.data.frequency)
                return 1;
            return 0;
        };
        PriorityQueue<TreeNode<Word>> queue = new PriorityQueue<>(comparator);
        for (Word word : words) {
            TreeNode<Word> node = new TreeNode<>();
            node.data = word;
            queue.add(node);
        }

        while (queue.size() > 1) {
            TreeNode<Word> treeNode = new TreeNode<Word>();
            treeNode.left = queue.poll();
            treeNode.right = queue.poll();
            treeNode.data = new Word();
            treeNode.data.frequency = treeNode.left.data.frequency + treeNode.right.data.frequency;
            queue.add(treeNode);
        }

        return queue.poll();
    }

    public HashMap<Byte, String> getEncodeMap(TreeNode root) {
        HashMap<Byte, String> encodeMap = new HashMap<>();
        getCode(root, "", encodeMap);
        return encodeMap;
    }

    public void getCode(TreeNode<Word> root, String prefix, HashMap<Byte, String> encodeTable) {
        if (root.left == null && root.right == null) {
            encodeTable.put(root.data.symbol, prefix);
            return;
        }
        if (root.left != null) {
            getCode(root.left, prefix + "0", encodeTable);
        }
        if (root.right != null) {
            getCode(root.right, prefix + "1", encodeTable);
        }
        return;
    }

    public static class configuration implements Serializable {
        private int  size;
        private HashMap<Byte, String> encodeMap;
    }

    public void compress(String input, String output) throws IOException {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input));
            byte[] bytes = new byte[bis.available()];
            bis.read(bytes);
            HashMap<Byte, Integer> map = new HashMap<>();
            for (int i = 0; i < bytes.length; i++) {
                if (map.containsKey(bytes[i])) {
                    map.put(bytes[i], map.get(bytes[i]) + 1);
                } else
                    map.put(bytes[i], 1);
            }
            Word[] words = new Word[map.size()];
            int i = 0;
            Set<Byte> keySet = map.keySet();
            Iterator<Byte> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                Word word = new Word();
                word.symbol = iterator.next();
                word.frequency = map.get(word.symbol);
                words[i++] = word;
            }
            HashMap<Byte, String> encodeMap = new HashMap<>();
            TreeNode root = buildTree(words);
            encodeMap = getEncodeMap(root);
            configuration configuration = new configuration();
            configuration.encodeMap = encodeMap;
            byte[] code =getCodeAndConfig(bytes, configuration);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output));
            bos.write(code);
            bos.close();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(output + ".config"));
            oos.writeObject(configuration);
            oos.close();

        }

        public byte[] getCodeAndConfig( byte[] bytes, configuration configuration){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StringBuilder sb = new StringBuilder();
            int templen = 0;
            int i = 0;
            byte temp = 0x00;
           int sum = 0;
            for (byte b : bytes) {
                sb.append(configuration.encodeMap.get(b));
            }
            String code = sb.toString();
            for (i = 0; i < code.length(); i++) {
                if (templen == 8) {
                    baos.write(temp);
                    templen = 0;
                    temp = 0x00;
                }
                temp <<= 1;
                if (code.charAt(i) == '1') {
                    temp += 1;
                }
                templen++;
                sum++;
            }
            while (templen++ < 8) {
                temp <<= 1;
            }
            baos.write(temp);
            configuration.size = sum;
            return baos.toByteArray();
        }


    public void extract(String input, String output) throws IOException, ClassNotFoundException {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(input));
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(input + ".config"));
            FileOutputStream fos = new FileOutputStream(output);
            byte[] codes = new byte[bis.available()];
            bis.read(codes);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <codes.length ; i++) {
           sb.append(changeByteToString(codes[i]));
        }

        configuration config = (configuration)ois.readObject();
        String s =sb.toString();
        HashMap<String, Byte> hashMap = new HashMap<>();
        for (Map.Entry<Byte, String> entry : config.encodeMap.entrySet()) {
           hashMap.put(entry.getValue(),entry.getKey());
        }
            int i = 1;
            int len=config.size;
            s=s.substring(0,len);
            List<Byte> list = new ArrayList<>();
            while (s.length()>0) {
                String tempcode = s.substring(0, i);
                if (hashMap.containsKey(tempcode)) {
                    list.add(hashMap.get(tempcode));
                    if(tempcode.length()==s.length())
                        break;
                    s = s.substring(tempcode.length());
                    i = 1;
                } else {

                    ++i;
                }
            }
        byte[] bytes = new byte[list.size()];
            for (int j = 0; j < list.size(); j++) {
                bytes[j] = list.get(j).byteValue();
            }
            fos.write(bytes);
       }
       public  String changeByteToString(byte b) {
           StringBuilder sb = new StringBuilder();
           int i = 0;
           while (i < 8) {
               if ((0x80 & b) == 0)
               {
                   sb.append("0");
                   b <<= 1;
               }
               else if ((0x80 & b)!= 0)
               {
                   sb.append("1");
                   b <<= 1;
               }
               i++;
           }
           return sb.toString();

       }

}



