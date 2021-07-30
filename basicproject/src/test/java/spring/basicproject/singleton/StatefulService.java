package spring.basicproject.singleton;

public class StatefulService {

    //private int price; //상태를 유지하는 필드

    public int order(String name, int price){
        System.out.println("name = " + name + " price = " + price);
        //this.price = price; // 이곳이 문제 라는데,,
        // A. price 필드 (공유 필드)가 존재하며, Client가 이를 수정할 수 있기 때문에 문제
        //    따라서 price 필드를 유지할 필요가 없다.
        return price;

    }

    public void getPrice() {
        //return price;
    }
}
