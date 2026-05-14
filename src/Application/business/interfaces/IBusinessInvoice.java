package business.interfaces;

import business.model.notaFiscal.NotaFiscal;
import java.util.List;

public interface IBusinessInvoice {
    NotaFiscal getById(int id);
    List<NotaFiscal> getAll();
    void patch(int id, NotaFiscal nf);
    void delete(int id);
    void post(NotaFiscal nf);
}
