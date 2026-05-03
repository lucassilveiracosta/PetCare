package repository;

import model.notaFiscal.Produto;
import repository.Interface.IRepositoryStockGeneralProducts;

public class RepositoryStockGeneralProducts implements IRepositoryStockGeneralProducts {
    private Produto produto;
    public RepositoryStockGeneralProducts(Produto produto){
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
