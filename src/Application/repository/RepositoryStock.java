package repository;

import model.notaFiscal.Produto;
import repository.Interface.IRepositoryStock;

public class RepositoryStock implements IRepositoryStock {
    private Produto produto;
    public RepositoryStock(Produto produto){
        this.produto = produto;
    }

    @Override
    public Produto getProduto() {
        return null;
    }

    @Override
    public void setProduto(Produto produto) {

    }
}
