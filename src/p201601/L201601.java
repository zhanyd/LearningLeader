package p201601;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/10 0010.
 */
public class L201601 {
    public static void main(String[] args){
        int[] arry = {4,6,2,1,67,78,99,17,18,23,55,17,33};

        Salary[] salarArry = {new Salary("zhangsang",34,12),new Salary("lisi",22,12),new Salary("wangwu",89,12)
                ,new Salary("zhaoliu",66,12)};

        for (Salary salary: salarArry) {
            System.out.println(salary.getName() + " " + salary.getBaseSalary());
        }
        System.out.println("-----------------------");
        maopaoSalary(salarArry);
        for (Salary salary: salarArry) {
            System.out.println(salary.getName() + " " + salary.getBaseSalary());
        }

        //maopao(arry);
        //quick(arry,0,arry.length-1);
      /*  for (int i: arry) {
            System.out.println(i);
        }
*/
    }

    public static Salary[] maopaoSalary( Salary[] arry){

        boolean noswap = true;
        Salary temp;
        for(int i=0; i<arry.length - 1; i++){
            noswap = true;
            for(int j=0; j<arry.length - 1 - i; j++){
                if(arry[j].getBaseSalary() >= arry[j+1].getBaseSalary()){
                    temp = arry[j];
                    arry[j] = arry[j+1];
                    arry[j+1] = temp;
                    noswap = false;
                }

            }
            if(noswap){
                break;
            }
        }

        return arry;
    }

    public static int[] maopao(int[] arry){

        boolean noswap = true;
        int temp;
        for(int i=0; i<arry.length - 1; i++){
            noswap = true;
            for(int j=0; j<arry.length - 1 - i; j++){
                if(arry[j] < arry[j+1]){
                    temp = arry[j];
                    arry[j] = arry[j+1];
                    arry[j+1] = temp;
                    noswap = false;
                }

            }
            if(noswap){
                break;
            }
        }

        return arry;
    }


    public static int getMiddle(int[] list,int low,int high){
        int tmp = list[low];
        while(low < high){
            while(low < high && list[high] >= tmp){
                high--;
            }
            list[low] = list[high];

            while(low < high && list[low] < tmp){
                low++;
            }
           list[high] = list[low];
        }
        list[low] = tmp;
        return low;
    }

    public static void quick(int[] list,int low,int high){
        if(low < high){
            int middle = getMiddle(list,low,high);
            quick(list,low,middle-1);
            quick(list,middle+1,high);
        }
    }
}
