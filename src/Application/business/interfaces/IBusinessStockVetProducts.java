package business.interfaces;

import model.notaFiscal.Produto;
import java.util.List;

public interface IBusinessStockVetProducts {
    Produto getById(int id);
    List<Produto> getAll();
    void patch(int id, Produto nf);
    void delete(int id);
    void post(Produto nf);
}
