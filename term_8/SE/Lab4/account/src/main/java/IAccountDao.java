import com.mongodb.rx.client.Success;
import rx.Observable;

public interface IAccountDao {
    Observable<Success> addUser(long id);

    Observable<Success> addMoney(long id, int count);

    Observable<Stocks> getUserStocksInfo(long id);

    Observable<Integer> getAllMoney(long id);

    Observable<Success> buyStocks(long id, String companyName, int count);

    Observable<Success> sellStocks(long id, String companyName, int count);
}
