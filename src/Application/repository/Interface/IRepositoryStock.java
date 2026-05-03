package repository.Interface;

import model.notaFiscal.Produto;

public interface IRepositoryStock {
    public Produto getProduto();
    public void setProduto(Produto produto);
}
