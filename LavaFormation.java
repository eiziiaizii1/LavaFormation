
package azizonder_hw3;

import java.util.Scanner;

// Aziz Önder - 22050141021

public class AzizOnder_HW3 {

    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        boolean hasNegativeNum;
        int[] rockFormationArray1D = new int[10];
        System.out.println("Enter 10 numbers representing the rock formations in the valley: ");
        
        //if array has negative number, prompts user to input again
        do {
            hasNegativeNum = false;
            for (int i = 0; i < rockFormationArray1D.length; i++) {
                rockFormationArray1D[i] = input.nextInt();
                if (rockFormationArray1D[i]<0)
                    hasNegativeNum = true;
            }
            if(hasNegativeNum)
                System.out.println("You entered negative number/numbers. Please enter positive numbers!!");
        } while (hasNegativeNum);

        System.out.println(puddle(rockFormationArray1D));
    }

    public static int puddle(int[] rockArray1D) {
        int lavaAmountTotal = 0;
        
        // Array for finding the boundries from right side
        int[] rightMaxValOfArray = new int[rockArray1D.length];
        // Array for finding the boundries from left side
        int[] leftMaxValOfArray = new int[rockArray1D.length];
        // We are gonna combine left and right array, then assign it to this keyArray 
        int[] keyArray = new int[rockArray1D.length];

        // To minimize to errors, I assigned arrays' values as minimum .
        // Code works without this process, but I think it is better to do in this way
        for (int i = 0; i < rockArray1D.length; i++) {
            rightMaxValOfArray[i] = Integer.MIN_VALUE;
            leftMaxValOfArray[i] = Integer.MIN_VALUE;
        }

/* Örneğin elimizde {7,1,2,10,8,1,7}'luk array var. Bu array'i incelediğimizde: 0. indexte 0,
* 1. indexte 6, 2. indexte 5, 3. indexte 0 ,4. indexte 0, 5. indexte 6, 6. indexte 0 tane lav bulunmalı. 
* Bulmamız gereken sayılardan yola çıkarak       
* "(Bulacağımız bir sayı arrayi) - array[i] = (bir indexteki lav miktarı)" şeklinde bir işlem yapmalıyız
* (Bulacağımız bir sayı arrayi) = keyArray dersek, keyArray = {7,7,7,10,8,7,7} olmalı. Bu arrayi elde 
* etmemiz için de soldan ve sağdan başlayarak lavları sınırlandıran max değerleri bulmamız gerekiyor. 
* Array[enSağ] bu değerlerden biri olabilir, bu olasılıktan yola çıkarak keyArray[ensSağ]= array[enSağ] diyoruz.
* Daha sonra sağdan sola doğru giderek sınırlandıran değerleri bulmamz için loopa sokmamız gerekiyor.
* Zaten en sağ değeri varsayarak atadığımız için loopa en sağın bir solundan başlayrak sokmalıyız. Bu yüzden       
* array.length'ten 1 değil 2 çıkararak başlıyoruz. Eğer varsayımımız doğruysa(array[enSağ] sınırlandıran değerse),
* rightMaxValOfArray[i] en sağdaki değere eşit olmalı, eğer varsayımımız doğru değilse sınırlandıran değer 
* array[i]'ye eşit olmalı. Sınırlandıran değer daha büyüktür, bu yüzden iki array arasında hangisi daha büyükse
* rightMaxValOfArray[i] o değere eşit olmalı. Hangisinin daha büyük olduğunu da  findMaxBetweenTwo() metodu yazarak
* buldum. */        
    
        rightMaxValOfArray[rockArray1D.length - 1] = rockArray1D[rockArray1D.length - 1];
        leftMaxValOfArray[0] = rockArray1D[0];

// Açıklamada bahsettiğim loop bu şekilde oluyor. Örnek verdiğim değer için 
// rightMaxValOfArray[]= {10,10,10,10,8,7,7} olur.
        for (int i = rockArray1D.length - 2; i >= 0; i--) {
            rightMaxValOfArray[i] = findMaxBetweenTwo(rockArray1D[i], rightMaxValOfArray[i + 1]);
        }
        
// Aynı işlemi aynı mantığı kullanarak soldan sağa doğru bu loop ile yapıyoruz 
// ve örnek için leftMaxValOfArray[]= {7,7,7,10,10,10,10} oluyor.
        for (int i = 1; i < rockArray1D.length; i++) {
            leftMaxValOfArray[i] = findMaxBetweenTwo(rockArray1D[i], leftMaxValOfArray[i - 1]);
        }

// Bulmamız gereken     keyArray = {7,7,7,10,8,7,7} olmalı. 
// elimizdeki rightMaxValOfArray = {10,10,10,10,8,7,7}
//             leftMaxValOfArray = {7,7,7,10,10,10,10}
// Bu arraylerden keyArrayi elde etmek için iki arrayin aynı indexindeki minimum veya eşit değerlerini almalıyız.
// min değeri almamızın sebebi ise eğer 2 max değer varsa lavların en çok küçük olan max değere kadar çıkabilmesi
// findMinBetweenTwo() metoduyla ve arraylari loopa sokarak istediğmize ulaşabiliyoruz.
        for (int i = 0; i < keyArray.length; i++) {
            keyArray[i] = findMinBetweenTwo(leftMaxValOfArray[i], rightMaxValOfArray[i]);
        }

// Son olarak toplam lav miktarını bulmak için açıklama başında söylediğim
// (Bulacağımız bir sayı arrayi) - array[i] = (bir indexteki lav miktarı) işlemini loopa sokuyoruz.
        for (int i = 0; i < rockArray1D.length; i++) {
            lavaAmountTotal += keyArray[i] - rockArray1D[i];
        }

        return lavaAmountTotal;
    }

    public static int findMaxBetweenTwo(int x, int y) {
        if (x > y) {
            return x;
        } else {
            return y;
        }
    }

    public static int findMinBetweenTwo(int x, int y) {
        if (x < y) {
            return x;
        } else {
            return y;
        }
    }
}
