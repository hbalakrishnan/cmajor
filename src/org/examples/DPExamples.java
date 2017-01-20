package org.examples;

import java.util.Arrays;
import java.util.Scanner;

public class DPExamples {
	
	
	private static int fibUsingMemoization(int[] mem, int n) {
		if(mem[n] == -1) {
			if(n <= 1) {
				mem[n] = n;
			} else {
				mem[n] = fibUsingMemoization(mem, n-1) + fibUsingMemoization(mem, n-2);
			}
		} 
		return mem[n];
	}
	
	private static int fibUsingBottomup(int[] mem, int n) {
		mem[0] = 0;
		mem[1] = 1;
		
		for(int i=2; i<=n; i++) {
			mem[i] = mem[i-1] + mem[i-2];
		}
		return mem[n];
	}
	
	private static int solveMinStepsMemoization(int[] mem, int n) {
		// handle base case
		if(n==1) {
			return 0;
		}
		
		// if its solved already return that value
		if(mem[n] != -1) {
			return mem[n];
		}
		
		int optimalValue = 1 + solveMinStepsMemoization(mem, n-1);
		if(n%2 == 0) {
			optimalValue = Math.min(optimalValue, 1 + solveMinStepsMemoization(mem, n/2));
		} 
		if(n%3 == 0) {
			optimalValue = Math.min(optimalValue, 1 + solveMinStepsMemoization(mem, n/3));
		}
		mem[n] = optimalValue;
		return optimalValue;
	}
	
	private static int solveBottomupDP(int[] dp, int n) {
		// handle base case
		if(n==1) {
			dp[1] = 0;
		}
		for(int i=2; i <= n; i++) {
			dp[i] = 1+ dp[i-1];
			if(i%2==0) {
				dp[i] = Math.min(dp[i], 1 + dp[i/2]);
			} else if(i%3==0) {
				dp[i] = Math.min(dp[i], 1 + dp[i/3]);
			}
		}
		return dp[n];
	}
	
	private static int binomialCoeff(int n, int k) {
		if(k==0 || k ==n) {
			return 1;
		}
		return binomialCoeff(n-1, k-1) + binomialCoeff(n-1, k);
	}
	
	
	private static int binomialCoeffDP(int n, int k) {
		int[][] dp = new int[n+1][k+1];
		
		
		for(int i=0; i<=n; i++) {
			int max = Math.min(i,  k);
			for(int j=0; j<=max; j++) {
				if(j==0 || j == i) {
					dp[i][j] = 1;
				} else {
					dp[i][j] = dp[i-1][j-1] + dp[i-1][j];
				}
			}
		}
		return dp[n][k];
	}
	
	private static int longestCommonSubSeq(char[] a, char[] b, int n, int m) {
		int[][] result = new int[n+1][m+1];
		// handle base case 
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<=n; i++) {
			for(int j=0; j<=m; j++) {
				if(i==0 || j ==0) {
					result[i][j] = 0;
				} else if (a[i-1] == b[j-1]) {
					result[i][j] = 1 + result[i-1][j-1];
					sb.append(a[i-1]);
				} else {
					result[i][j] = Math.max(result[i-1][j], result[i][j-1]);
				}
			}
		}
		System.out.println(sb.toString());
		return result[n][m];
	}
	
	public static int NumberOfCoins(int change, int[] denom) {
		final int[] C = new int [change+1];
        Arrays.fill (C, Integer.MAX_VALUE);
        C[0] = 0;  
  
        for (int p=1; p<=change; p++) {
           int min = Integer.MAX_VALUE;
           for (int i=0; i<denom.length; i++) {
              if (denom[i]<=p) {
                 final int x = C[p-denom[i]];
                 if (x<Integer.MAX_VALUE && (x+1)<min) {
                	 min = 1+x;
                 }
              }
           }
           C[p] = min;
        }
        return C[change]; 
	}
	
	
	private static int minimumPathSum(int[][] matrix) {
		if(matrix.length == 0) {
			return 0;
		}
		return minPath(0, 0, matrix);
	}
	
	private static int minPath(int i, int j, int[][] matrix) {
		if(i==matrix.length-1 && j==matrix[0].length-1) {
			return matrix[i][j];
		}
		
		if(i<matrix.length-1 && j <matrix[0].length-1) {
			int sum1 = matrix[i][j] + minPath(i+1, j, matrix);
			int sum2 = matrix[i][j] + minPath(i, j+1, matrix);
			return Math.min(sum1, sum2);
		}
		
		if(i<matrix.length-1) {
			return matrix[i][j] + minPath(i+1, j, matrix);
		}
		
		if(j<matrix[0].length-1) {
			return matrix[i][j] + minPath(i, j+1, matrix);
		}
		return 0;
	}
	
	private static int minPathDP(int[][] matrix) {
		if(matrix.length == 0) {
			return 0;
		}
		
		int cols = matrix.length;
		int rows = matrix[0].length;
		
		int[][] result = new int[rows][cols];
		result[0][0] = matrix[0][0];
		
		for(int i=1; i<rows; i++) {
			result[0][i] = result[0][i-1] + matrix[0][i];
		}
		
		for(int i=1; i<cols; i++) {
			result[i][0] = result[i-1][0] + matrix[i][0];
		}
		
		for(int i=1; i<cols; i++) {
			for(int j=1; j<rows; j++) {
				if(result[i-1][j] > result[i][j-1]) {
					result[i][j] = result[i][j-1] + result[i][j];
				} else {
					result[i][j] = result[i-1][j] + result[i][j];
				}
			}
		}
		return result[rows-1][cols-1];
	}
	
	private static int uniquePaths(int rows, int cols) {
		return uniquePaths(0, 0, rows, cols);
	}
	
	private static int uniquePaths(int i, int j, int rows, int cols) {
		if(i==rows-1 && j==cols-1) {
			return 1;
		}
		
		if(i<rows-1 && j<cols-1) {
			return uniquePaths(i+1, j, rows, cols) + uniquePaths(i, j+1, rows, cols);
		}
		
		if(i<rows-1) {
			return uniquePaths(i+1, j, rows, cols);
		}
		
		if(j<cols-1) {
			return uniquePaths(i, j+1, rows, cols);
		}
		return 0;
	}
	
	private static int uniquePathsDP(int rows, int cols) {
		// base case
		if(rows==1 || cols ==1) {
			return 1;
		}
		
		int[][] result = new int[rows][cols];
		
		// init first column and all rows
		for(int i=0; i<rows; i++) {
			result[i][0] = 1;
		}
		
		// init top row and all columns
		for(int i=0; i<cols; i++) {
			result[0][i] = 1;
		}
		
		for(int i=1; i<cols; i++) {
			for(int j=1; j<rows; j++) {
				result[i][j] = result[i-1][j] + result[i][j-1];
			}
		}
		return result[rows-1][cols-1];
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(String.format("%1$s,%2$s", "a","b","c"));
		Scanner in = new Scanner(System.in);
		//int n = in.nextInt();
		//int k = in.nextInt();
		
		//System.out.println(binomialCoeff(n, k));
		//System.out.println(binomialCoeffDP(n, k));
		
		/**int[] mem = new int[n+1];
		// init values with -1
		for(int i=0; i<=n; i++) {
			mem[i] = -1;
		}
		
		int[] mem2 = new int[n+1];
		
		
		int optimalValue1 = solveMinStepsMemoization(mem, n);
		System.out.println(optimalValue1);
		int optimalValue2 = solveBottomupDP(mem2, n);
		System.out.println(optimalValue2);
		int value = fibUsingMemoization(mem, n);
		System.out.println(value);
		
		int value2 = fibUsingBottomup(mem2, n);
		System.out.println(value2);*/
		String str1 = in.next();
		String str2 = in.next();
		System.out.println(longestCommonSubSeq(str1.toCharArray(), str2.toCharArray(), str1.length(), str2.length()));
		in.close();
	}
}
