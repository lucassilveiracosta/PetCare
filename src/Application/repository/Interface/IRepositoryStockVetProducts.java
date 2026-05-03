package repository.Interface;

import model.notaFiscal.Produto;
import java.util.List;

public interface IRepositoryStockVetProducts {
    Produto findById(int id);
    void save(Produto produto);
    List<Produto> listAll();
    void delete(int id);

}