import java.util.Scanner;

public class test
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String[] input = sc.nextLine().split(",");
        int[] A = new int[input.length];
        for (int k = 0;k<input.length;k++)
            A[k]=Integer.parseInt(input[k]);
        int[] B = sort(A);
        for (int k= 0; k < B.length;k++)
            System.out.print(B[k]+" ");
    }
    static int[] sort(int[] A)
    {
        int max = A[0];
        int min = A[0];
        for (int k = 1;k<A.length;k++)
        {
            if (max<A[k])
                max = A[k];
            if (min > A[k])
                min =  A[k];
        }
        min = -min;
        int[] C = new int[max+min+1];
        int[] B = new int[A.length];
        for (int j = 0;j<A.length;j++)
        {
            if (A[j]>=0)
                C[A[j]] += 1;
            else
                C[-A[j]+max] += 1;
        }
        for (int i= max+min-1;i > max;i--)
            C[i] = C[i] + C[i+1];
        C[0] = C[0] + C [max+1];
        for (int i= 1;i < max+1;i++)
            C[i] = C[i] + C[i-1];
        for (int j= A.length-1;j >= 0;j--)
        {
            if (A[j]>=0)
            {
                B[C[A[j]]-1] = A[j];
                C[A[j]] -= 1;
            }
            else
            {
                B[C[-A[j]+max]-1] = A[j];
                C[-A[j]+max] -= 1;
            }
            for (int k= 0; k < B.length;k++)
                System.out.print(B[k]+" ");
            System.out.println();
        }
        return B;
    }
}