package business.interfaces;

import model.notaFiscal.Produto;
import java.util.List;

public interface IBusinessStockGeneralProducts {
    Produto getById(int id);
    List<Produto> getAll();
    void patch(int id, Produto novoProduto);
    void delete(int id);
    void post(Produto novoProduto);
}
