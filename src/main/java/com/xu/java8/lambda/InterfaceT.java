package com.xu.java8.lambda;

/**
 * Class 接口的默认方法
 *
 * @author hua xu
 * @version 1.0.0
 * @date 16/08/30
 */
public class InterfaceT {

    /**
     * Java 8允许我们给接口添加一个非抽象的方法实现，只需要使用 default关键字即可，这个特征又叫做扩展方法
     */
    interface Formula {
        double calculate(int a);

        default double sqrt(int a) {
            return Math.sqrt(positive(a));
        }

        static int positive(int a) {
            return a > 0 ? a : 0;
        }
    }

    public static void main(String[] args) {
        Formula formula1 = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);  //默认方法sqrt将在子类上可以直接使用
            }
        };
        System.out.println(formula1.calculate(100));
        System.out.println(formula1.sqrt(-23));
        System.out.println(Formula.positive(-4));

        /**Lambda表达式不能使用默认方法，无法编译*/
        //Formula formula = (a) -> sqrt( a * 100);
    }
}


