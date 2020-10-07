package hello.core.singleton;

/**     스프링은 기본적으로 싱글톤으로 제공해준다 */

/**     임의로 만든 싱글톤서비스  test에서 사용*/
public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    public static SingletonService getInstance() {
        return instance;
    }
    /**     그럼 static이니까 자바 뜰 때 한 번 생성된 후 private이니까 사실상 아무곳에서도 새로 생성할 수 없고
     *      호출을하기위해선 getInstance말곤 할 수 없다 */
    private SingletonService() {
    }
    //하나만 만들려고하는데 누가 다른곳에서 더 만들면 안되니까 private 생성자

    public void logic()
    {
        System.out.println("싱글톤 객체 로직 호출");
    }


}
