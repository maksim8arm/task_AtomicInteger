package com.company;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static int threeLetter = 0;
    public static int fourLetter = 0;
    public static int letters = 0;

    public static void main(String[] args) throws InterruptedException {

        AtomicInteger threeLetterAtom = new AtomicInteger(threeLetter);
        AtomicInteger fourLetterAtom = new AtomicInteger(fourLetter);
        AtomicInteger lettersAtom = new AtomicInteger(letters);

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Runnable fourLettterMethod = () -> {
            for (String str : texts) {
                if (str.equals("abba")) {
                    fourLetterAtom.incrementAndGet();
                }
            }
            System.out.println("Красивых слов \"abba\" с длиной 4: " + fourLetterAtom.get());
        };

        Runnable threeLetterMethod = () -> {
            for (String str : texts) {
                if (str.equals("aaa")) {
                    threeLetterAtom.incrementAndGet();
                }
            }
            System.out.println("Красивых слов \"aaa\" с длиной 3: " + threeLetterAtom.get());
        };

        Runnable lettersMethod = () -> {
            for (String str : texts) {
                int i = 0;
                int j = 0;
                while (i < str.length() - 1) {
                    if (str.charAt(i) <= str.charAt(i + 1)) {
                        j = 1;
                    } else {
                        j = 0;
                        break;
                    }
                    i++;
                }

                if (j == 1) {
                    lettersAtom.incrementAndGet();
                }

            }
            System.out.println("Красивых слов с буквами по порядку: " + lettersAtom.get());
        };

        Thread threadThree = new Thread(threeLetterMethod);
        Thread threadFour = new Thread(fourLettterMethod);
        Thread threadLetters = new Thread(lettersMethod);

        threadThree.start();
        threadFour.start();
        threadLetters.start();

        threadThree.join();
        threadFour.join();
        threadLetters.join();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
