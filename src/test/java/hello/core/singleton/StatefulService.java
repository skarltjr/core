package hello.core.singleton;

public class StatefulService {
    /**    ★★★ 실무에서 정말 실수할 수 있는 부분
     *
     *      싱글톤은 결국 객체를 공유하기 때문에
     *      싱글톤 객체는 상태를 유지
     *      (stateful)하게 설계하면 안된다
     * */


  /*private int price; *//**      상태를 유지하는 필드  = 안좋은것 *//*

   public *//*void*//* int  order(String name, int price) {
        System.out.println(name + " " + price);
       this.price = price;        *//** 여기가 문제 !!!*//*
       return price;
    }           공유필드가 존재하고 변경될 수 있는 부분이 문제가되니까 해결하면*/


    //공유필드 없이
    public int order(String name,int price)
    {
        System.out.println(name + " " + price);
        return price;
    }





    /*public int getPrice() {
         return price;
    }*/





}
