/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assginment.similiratythread;

/**
 *
 * @author Dhanuka
 */
public class LCSalgo2 {

    public static int findMatches(String str1, String str2, int p, int q) {

        int[][] tableForLCS = new int[p + 1][q + 1];

        for (int i = 0; i <= p; i++) {
            for (int j = 0; j <= q; j++) {
                if (i == 0 || j == 0) {
                    tableForLCS[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    tableForLCS[i][j] = tableForLCS[i - 1][j - 1] + 1;
                } else {
                    tableForLCS[i][j] = Math.max(tableForLCS[i - 1][j], tableForLCS[i][j - 1]);
                }
            }
        }

        int index = tableForLCS[p][q];
        int temp = index;

        char[] longestCommonSubsequence = new char[index + 1];
        longestCommonSubsequence[index] = '\0';

        int i = p, j = q;
        String lcs = "";
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {

                longestCommonSubsequence[index - 1] = str1.charAt(i - 1);
                i--;
                j--;
                index--;
            } else if (tableForLCS[i - 1][j] > tableForLCS[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        for (int k = 0; k <= temp; k++) {
            lcs = lcs + longestCommonSubsequence[k];
        }

        return lcs.length();
    }

    public float calculateScore(int matches, int file1_content_l, int file1_content_l2) {
        float final_score = 0;
        final_score = (float) (matches * 2) / (file1_content_l + file1_content_l2);
        return final_score;
    }

}
