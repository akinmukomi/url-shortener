package me.akinmukomi.assessment.urlshortener.utils;

import java.security.SecureRandom;
import java.util.*;


public class UniqueKeyGenerator {

    private static final String DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43};
    private static final int[] SEPS_INDICES = {0, 4, 8, 12};

    private final String salt_ = UUID.randomUUID().toString();

    private String alphabet_ = "";

    private int minHashLength_;

    private ArrayList<Character> seps_ = new ArrayList<Character>();
    private ArrayList<Character> guards_ = new ArrayList<Character>();


    public UniqueKeyGenerator(int minHashLength) {
        this(minHashLength, DEFAULT_ALPHABET);
    }

    public UniqueKeyGenerator(int minHashLength, String alphabet) {
        if (alphabet == null || alphabet.trim().isEmpty()) {
            throw new IllegalArgumentException("alphabet must not be empty");
        }

        if (minHashLength > 0) {
            minHashLength_ = minHashLength;
        }

        alphabet_ = join(new LinkedHashSet<String>(Arrays.asList(alphabet.split(""))), "");

        if (alphabet_.length() < 4) {
            throw new IllegalArgumentException("Alphabet must contain at least 4 unique characters.");
        }

        for (int prime : PRIMES) {
            if (prime < alphabet_.length()) {
                char c = alphabet_.charAt(prime - 1);
                seps_.add(c);
                alphabet_ = alphabet_.replace(c, ' ');
            }
        }

        for (int index : SEPS_INDICES) {
            if (index < seps_.size()) {
                guards_.add(seps_.get(index));
                seps_.remove(index);
            }
        }

        alphabet_ = consistentShuffle(alphabet_.replaceAll(" ", ""), salt_);
    }

    public String generateUniqueKey() {
        SecureRandom secureRandom = new SecureRandom();
        long number = secureRandom.nextInt(Integer.MAX_VALUE);
        return encode(new long[]{number}, alphabet_, salt_, minHashLength_);
    }

    private String encode(long[] numbers, String alphabet, String salt, int minHashLength) {
        String ret = "";
        String seps = consistentShuffle(join(seps_, ""), join(numbers, ""));
        char lotteryChar = 0;

        for (int i = 0; i < numbers.length; i++) {
            if (i == 0) {
                String lotterySalt = join(numbers, "-");
                for (long number : numbers) {
                    lotterySalt += "-" + (number + 1) * 2;
                }
                String lottery = consistentShuffle(alphabet, lotterySalt);
                lotteryChar = lottery.charAt(0);
                ret += lotteryChar;

                alphabet = lotteryChar + alphabet.replaceAll(String.valueOf(lotteryChar), "");
            }

            alphabet = consistentShuffle(alphabet, ((int) lotteryChar & 12345) + salt);
            ret += hash(numbers[i], alphabet);

            if (i + 1 < numbers.length) {
                ret += seps.charAt((int) ((numbers[i] + i) % seps.length()));
            }
        }

        if (ret.length() < minHashLength) {
            int firstIndex = 0;
            for (int i = 0; i < numbers.length; i++) {
                firstIndex += (i + 1) * numbers[i];
            }

            int guardIndex = firstIndex % guards_.size();
            char guard = guards_.get(guardIndex);
            ret = guard + ret;

            if (ret.length() < minHashLength) {
                guardIndex = (guardIndex + ret.length()) % guards_.size();
                guard = guards_.get(guardIndex);
                ret += guard;
            }
        }

        while (ret.length() < minHashLength) {
            long[] padArray = new long[]{alphabet.charAt(1), alphabet.charAt(0)};
            String padLeft = encode(padArray, alphabet, salt, 0);
            String padRight = encode(padArray, alphabet, join(padArray, ""), 0);

            ret = padLeft + ret + padRight;
            int excess = ret.length() - minHashLength;
            if (excess > 0) {
                ret = ret.substring(excess / 2, excess / 2 + minHashLength);
            }
            alphabet = consistentShuffle(alphabet, salt + ret);
        }

        return ret;
    }

    private String hash(long number, String alphabet) {
        String hash = "";

        while (number > 0) {
            hash = alphabet.charAt((int) (number % alphabet.length())) + hash;
            number = number / alphabet.length();
        }

        return hash;
    }

    private static String consistentShuffle(String alphabet, String salt) {
        String ret = "";

        if (!alphabet.isEmpty()) {
            List<String> alphabetArray = charArrayToStringList(alphabet.toCharArray());
            if (salt == null || salt.isEmpty()) {
                salt = new String(new char[]{'\0'});
            }

            int[] sortingArray = new int[salt.length()];
            for (int i = 0; i < salt.length(); i++) {
                sortingArray[i] = salt.charAt(i);
            }

            for (int i = 0; i < sortingArray.length; i++) {
                boolean add = true;

                for (int k = i; k != sortingArray.length + i - 1; k++) {
                    int nextIndex = (k + 1) % sortingArray.length;

                    if (add) {
                        sortingArray[i] += sortingArray[nextIndex] + (k * i);
                    }
                    else {
                        sortingArray[i] -= sortingArray[nextIndex];
                    }

                    add = !add;
                }

                sortingArray[i] = Math.abs(sortingArray[i]);
            }

            int i = 0;
            while (alphabetArray.size() > 0) {
                int pos = sortingArray[i];
                if (pos >= alphabetArray.size()) {
                    pos %= alphabetArray.size();
                }
                ret += alphabetArray.get(pos);
                alphabetArray.remove(pos);
                i = ++i % sortingArray.length;
            }
        }
        return ret;
    }

    private static List<String> charArrayToStringList(char[] chars) {
        ArrayList<String> list = new ArrayList<String>(chars.length);
        for (char c : chars) {
            list.add(String.valueOf(c));
        }
        return list;
    }

    private static String join(long[] a, String delimiter) {
        ArrayList<String> strList = new ArrayList<String>(a.length);
        for (long l : a) {
            strList.add(String.valueOf(l));
        }
        return join(strList, delimiter);
    }

    private static String join(Collection<?> s, String delimiter) {
        Iterator<?> iter = s.iterator();
        if (iter.hasNext()) {
            StringBuilder builder = new StringBuilder(s.size());
            builder.append(iter.next());
            while (iter.hasNext()) {
                builder.append(delimiter);
                builder.append(iter.next());
            }
            return builder.toString();
        }
        return "";
    }

    public static void main(String[] args) {

        UniqueKeyGenerator hashids = new UniqueKeyGenerator(8);
        System.out.println(hashids.generateUniqueKey());

        System.out.println(hashids.generateUniqueKey());

        System.out.println(hashids.generateUniqueKey());

        System.out.println(hashids.generateUniqueKey());

        System.out.println(hashids.generateUniqueKey());
    }
}
